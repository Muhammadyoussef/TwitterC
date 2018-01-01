package com.rxmuhammadyoussef.twitterc.ui.userdetails;

import com.rxmuhammadyoussef.twitterc.di.activity.ActivityScope;
import com.rxmuhammadyoussef.twitterc.event.FetchTweetsFinishedEvent;
import com.rxmuhammadyoussef.twitterc.event.FetchTweetsNetworkFailureEvent;
import com.rxmuhammadyoussef.twitterc.schedulers.ThreadSchedulers;
import com.rxmuhammadyoussef.twitterc.schedulers.qualifier.ComputationalThread;
import com.rxmuhammadyoussef.twitterc.store.UserDetailsRepo;
import com.rxmuhammadyoussef.twitterc.store.model.profile.ProfileViewModel;
import com.rxmuhammadyoussef.twitterc.store.model.tweet.TweetMapper;
import com.rxmuhammadyoussef.twitterc.store.model.user.UserMapper;
import com.rxmuhammadyoussef.twitterc.util.RxEventBus;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 This class represents the presenter layer of the user details page which handles all the logic
 */

@ActivityScope
class UserDetailsPresenter {

    private final UserDetailsScreen userDetailsScreen;
    private final ThreadSchedulers threadSchedulers;
    private final UserDetailsRepo userDetailsRepo;
    private final CompositeDisposable disposable;
    private final TweetMapper tweetMapper;
    private final UserMapper userMapper;
    private final RxEventBus eventBus;

    @Inject
    UserDetailsPresenter(@ComputationalThread ThreadSchedulers threadSchedulers, UserDetailsScreen userDetailsScreen,
                         UserDetailsRepo userDetailsRepo, CompositeDisposable disposable,
                         TweetMapper tweetMapper, UserMapper userMapper, RxEventBus eventBus) {
        this.threadSchedulers = threadSchedulers;
        this.userDetailsScreen = userDetailsScreen;
        this.userDetailsRepo = userDetailsRepo;
        this.disposable = disposable;
        this.tweetMapper = tweetMapper;
        this.userMapper = userMapper;
        this.eventBus = eventBus;
    }

    void onCreate(long userId) {
        userDetailsScreen.setupToolbar();
        userDetailsScreen.setupRefreshListener();
        userDetailsScreen.setupRecyclerView();
        disposable.add(initializeEventsObservable());
        fetchProfile(userId);
    }

    private Disposable initializeEventsObservable() {
        return eventBus.toObservable()
                .subscribeOn(threadSchedulers.subscribeOn())
                .observeOn(threadSchedulers.observeOn())
                .subscribe(event -> {
                    if (event instanceof FetchTweetsFinishedEvent) {
                        userDetailsScreen.hideLoadingAnimation();
                        if (event instanceof FetchTweetsNetworkFailureEvent) {
                            userDetailsScreen.showNetworkError();
                        }
                    }
                }, Timber::e);
    }

    void fetchProfile(long userId) {
        userDetailsScreen.showLoadingAnimation();
        Observable.combineLatest(
                userDetailsRepo.fetchUserDetails(userId)
                        .map(userMapper::toViewModel)
                        .toObservable(),
                userDetailsRepo.fetchTweets(userId)
                        .map(tweetMapper::toModels)
                        .map(tweetMapper::toViewModels)
                        .toObservable(),
                ProfileViewModel::new)
                .subscribeOn(threadSchedulers.subscribeOn())
                .observeOn(threadSchedulers.observeOn())
                .subscribe(userDetailsScreen::updateProfile, Timber::e);
    }

    void onLogoutClick() {
        disposable.add(
                userDetailsRepo.clearDatabase()
                        .subscribeOn(threadSchedulers.subscribeOn())
                        .observeOn(threadSchedulers.observeOn())
                        .subscribe(userDetailsScreen::logout, Timber::e));
    }

    void onDestroy() {
        disposable.clear();
    }
}
