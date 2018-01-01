package com.rxmuhammadyoussef.twitterc.ui.home;

import com.rxmuhammadyoussef.twitterc.di.activity.ActivityScope;
import com.rxmuhammadyoussef.twitterc.event.FetchFollowersFinishedEvent;
import com.rxmuhammadyoussef.twitterc.event.FetchFollowersNetworkFailureEvent;
import com.rxmuhammadyoussef.twitterc.schedulers.ThreadSchedulers;
import com.rxmuhammadyoussef.twitterc.schedulers.qualifier.ComputationalThread;
import com.rxmuhammadyoussef.twitterc.store.HomeRepo;
import com.rxmuhammadyoussef.twitterc.store.model.user.UserMapper;
import com.rxmuhammadyoussef.twitterc.util.RxEventBus;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 This class represents the presenter layer of the home page which handles all the logic
 */

@ActivityScope
class HomePresenter {

    enum LoadingPosition {
        TOP, BOTTOM
    }

    private final ThreadSchedulers threadSchedulers;

    private final CompositeDisposable disposable;
    private final HomeScreen homeScreen;
    private final RxEventBus eventBus;
    private final HomeRepo homeRepo;
    private final UserMapper userMapper;

    @Inject
    HomePresenter(@ComputationalThread ThreadSchedulers threadSchedulers,
                  CompositeDisposable disposable,
                  HomeScreen homeScreen,
                  RxEventBus eventBus,
                  HomeRepo homeRepo,
                  UserMapper mapper) {
        this.threadSchedulers = threadSchedulers;
        this.disposable = disposable;
        this.homeScreen = homeScreen;
        this.homeRepo = homeRepo;
        this.userMapper = mapper;
        this.eventBus = eventBus;
    }

    void onCreate() {
        homeScreen.setupRecyclerView();
        homeScreen.setupRefreshListener();
        homeRepo.onCreate();
        disposable.add(initializeEventsChangesObservable());
        disposable.add(initializeFollowersChangesObservable());
        fetchFollowers(LoadingPosition.TOP);
    }

    private Disposable initializeEventsChangesObservable() {
        return eventBus.toObservable()
                .subscribeOn(threadSchedulers.subscribeOn())
                .observeOn(threadSchedulers.observeOn())
                .subscribe(event -> {
                    if (event instanceof FetchFollowersFinishedEvent) {
                        homeScreen.hideLoadingAnimation();
                        if (event instanceof FetchFollowersNetworkFailureEvent) {
                            homeScreen.showNetworkError();
                        }
                    }
                }, Timber::e);
    }

    private Disposable initializeFollowersChangesObservable() {
        return homeRepo.observeFollowers()
                .debounce(200, TimeUnit.MICROSECONDS)
                .distinctUntilChanged()
                .map(userMapper::toViewModels)
                .subscribeOn(threadSchedulers.subscribeOn())
                .observeOn(threadSchedulers.observeOn())
                .subscribe(homeScreen::updateFollowers, Timber::e);
    }

    void fetchFollowers(LoadingPosition direction) {
        if (direction == LoadingPosition.BOTTOM) {
            homeScreen.showLoadingAnimationBottom();
        } else {
            homeScreen.showLoadingAnimationTop();
        }
        homeRepo.fetchFollowers();
    }

    void showFollowerProfile(long userId) {
        disposable.add(
                Single.just(userId)
                        .subscribeOn(threadSchedulers.subscribeOn())
                        .observeOn(threadSchedulers.observeOn())
                        .subscribe(homeScreen::showFollowerProfile, Timber::e));
    }

    void onLogoutClick() {
        disposable.add(
                homeRepo.clearDatabase()
                        .subscribeOn(threadSchedulers.subscribeOn())
                        .observeOn(threadSchedulers.observeOn())
                        .subscribe(homeScreen::logout, Timber::e));
    }

    void onDestroy() {
        disposable.clear();
    }
}
