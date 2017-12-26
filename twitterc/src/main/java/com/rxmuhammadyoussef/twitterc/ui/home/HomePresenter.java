package com.rxmuhammadyoussef.twitterc.ui.home;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.rxmuhammadyoussef.twitterc.di.activity.ActivityScope;
import com.rxmuhammadyoussef.twitterc.event.FetchFollowersEvent;
import com.rxmuhammadyoussef.twitterc.event.FetchFollowersFinishedEvent;
import com.rxmuhammadyoussef.twitterc.event.FetchFollowersStartedEvent;
import com.rxmuhammadyoussef.twitterc.event.FollowersFetchNetworkFailureEvent;
import com.rxmuhammadyoussef.twitterc.event.LogoutEvent;
import com.rxmuhammadyoussef.twitterc.models.user.UserMapper;
import com.rxmuhammadyoussef.twitterc.models.user.UserViewModel;
import com.rxmuhammadyoussef.twitterc.schedulers.ThreadSchedulers;
import com.rxmuhammadyoussef.twitterc.schedulers.qualifier.ComputationalThread;
import com.rxmuhammadyoussef.twitterc.util.RxEventBus;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 This class represents the presenter layer of the home page which handles all the logic
 */

@ActivityScope
class HomePresenter {

    private final BehaviorRelay<List<UserViewModel>> userViewModelsRelay;
    private final ThreadSchedulers threadSchedulers;
    private final CompositeDisposable disposable;
    private final HomeScreen homeScreen;
    private final RxEventBus eventBus;
    private final HomeRepo homeRepo;
    private final UserMapper mapper;

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
        this.mapper = mapper;
        this.eventBus = eventBus;
        this.userViewModelsRelay = BehaviorRelay.createDefault(Collections.emptyList());
    }

    void onCreate() {
        homeScreen.setupRecyclerView();
        homeScreen.setupRefreshListener();
        homeRepo.onCreate();
        disposable.add(initializeEventsChangesObservable());
        disposable.add(initializeFollowersChangesObservable());
        disposable.add(initializeShouldUpdateUiObservable());
        fetchFollowers();
    }

    private Disposable initializeEventsChangesObservable() {
        return eventBus.toObservable()
                .subscribeOn(threadSchedulers.subscribeOn())
                .observeOn(threadSchedulers.observeOn())
                .subscribe(event -> {
                    if (event instanceof FetchFollowersStartedEvent) {
                        homeScreen.showLoadingAnimation();
                    } else if (event instanceof FetchFollowersFinishedEvent) {
                        homeScreen.hideLoadingAnimation();
                        if (event instanceof FollowersFetchNetworkFailureEvent) {
                            homeScreen.showNetworkError();
                        }
                    } else if (event instanceof LogoutEvent) {
                        homeScreen.logout();
                    }
                }, Timber::e);
    }

    private Disposable initializeFollowersChangesObservable() {
        return homeRepo.observeFollowers()
                .map(mapper::toViewModels)
                .subscribeOn(threadSchedulers.subscribeOn())
                .observeOn(threadSchedulers.observeOn())
                .subscribe(userViewModelsRelay::accept, Timber::e);
    }

    private Disposable initializeShouldUpdateUiObservable() {
        return userViewModelsRelay
                .debounce(200, TimeUnit.MICROSECONDS)
                .distinctUntilChanged()
                .subscribeOn(threadSchedulers.subscribeOn())
                .observeOn(threadSchedulers.observeOn())
                .subscribe(homeScreen::updateFollowers, Timber::e);
    }

    void fetchFollowers() {
        eventBus.send(new FetchFollowersEvent());
    }

    void onLogoutClick() {
        homeRepo.clearDatabase();
    }

    void onDestroy() {
        disposable.clear();
    }
}
