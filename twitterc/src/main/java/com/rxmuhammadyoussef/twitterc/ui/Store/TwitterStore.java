package com.rxmuhammadyoussef.twitterc.ui.Store;

import android.util.Log;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.rxmuhammadyoussef.twitterc.api.CloudQueries;
import com.rxmuhammadyoussef.twitterc.api.FollowersResponse;
import com.rxmuhammadyoussef.twitterc.di.application.ApplicationScope;
import com.rxmuhammadyoussef.twitterc.event.FetchFollowersEvent;
import com.rxmuhammadyoussef.twitterc.event.FollowersFetchNetworkFailureEvent;
import com.rxmuhammadyoussef.twitterc.event.FollowersFetchSuccessfulEvent;
import com.rxmuhammadyoussef.twitterc.event.TweetsFetchNetworkFailureEvent;
import com.rxmuhammadyoussef.twitterc.event.TweetsFetchSuccessfulEvent;
import com.rxmuhammadyoussef.twitterc.models.tweet.Tweet;
import com.rxmuhammadyoussef.twitterc.models.user.User;
import com.rxmuhammadyoussef.twitterc.schedulers.ThreadSchedulers;
import com.rxmuhammadyoussef.twitterc.schedulers.qualifier.ComputationalThread;
import com.rxmuhammadyoussef.twitterc.util.PreferencesUtil;
import com.rxmuhammadyoussef.twitterc.util.RxEventBus;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

/**
 This class hold all the cloud-storage-specific operations for the Twitter API (i.e. save, get, delete, etc..)
 */

@ApplicationScope
class TwitterStore extends TwitterApiClient {

    private static final String CURSOR = "cursor";
    private final BehaviorRelay<List<User>> usersRelay;
    private final ThreadSchedulers threadSchedulers;
    private final PreferencesUtil preferencesUtil;
    private final CompositeDisposable disposable;
    private final RxEventBus eventBus;

    @Inject
    TwitterStore(@ComputationalThread ThreadSchedulers threadSchedulers, PreferencesUtil preferencesUtil,
                 CompositeDisposable disposable, RxEventBus eventBus) {
        this.threadSchedulers = threadSchedulers;
        this.preferencesUtil = preferencesUtil;
        this.disposable = disposable;
        this.eventBus = eventBus;
        this.usersRelay = BehaviorRelay.createDefault(Collections.emptyList());
        init();
    }

    private void init() {
        disposable.add(
                eventBus.toObservable()
                        .debounce(500, TimeUnit.MILLISECONDS)
                        .doOnNext(event -> {
                            if (event instanceof FetchFollowersEvent) {
                                fetchUsers();
                            }
                        })
                        .subscribeOn(threadSchedulers.subscribeOn())
                        .observeOn(threadSchedulers.observeOn())
                        .subscribe(ignored -> {}, Timber::e));
    }

    Observable<List<User>> observeFollowers() {
        return usersRelay.hide();
    }

    Single<List<Tweet>> fetchTweets(long userId) {
        return Single.create(e -> getService(CloudQueries.class)
                .getTweets(userId, 10)
                .enqueue(new Callback<List<Tweet>>() {
                    @Override
                    public void success(Result<List<Tweet>> result) {
                        eventBus.send(new TweetsFetchSuccessfulEvent());
                        Log.d("Muhammad:cloud:success", "" + result.data.size());
                        e.onSuccess(result.data);
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        eventBus.send(new TweetsFetchNetworkFailureEvent());
                        e.onError(exception);
                    }
                }));
    }

    private void fetchUsers() {
        getService(CloudQueries.class)
                .getFollowers(TwitterCore.getInstance().getSessionManager().getActiveSession().getUserId(), preferencesUtil.getLong(CURSOR))
                .enqueue(new Callback<FollowersResponse>() {
                    @Override
                    public void success(Result<FollowersResponse> result) {
                        eventBus.send(new FollowersFetchSuccessfulEvent());
                        preferencesUtil.saveOrUpdateLong(CURSOR, result.data.getNextCursor());
                        usersRelay.accept(result.data.getUsers());
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        eventBus.send(new FollowersFetchNetworkFailureEvent());
                        Timber.e(exception);
                    }
                });
    }
}
