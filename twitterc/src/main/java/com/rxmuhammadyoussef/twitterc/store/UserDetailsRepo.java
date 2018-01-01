package com.rxmuhammadyoussef.twitterc.store;

import com.rxmuhammadyoussef.twitterc.di.activity.ActivityScope;
import com.rxmuhammadyoussef.twitterc.store.model.tweet.TweetEntity;
import com.rxmuhammadyoussef.twitterc.store.model.tweet.TweetMapper;
import com.rxmuhammadyoussef.twitterc.store.model.user.UserEntity;
import com.rxmuhammadyoussef.twitterc.util.PreferencesUtil;
import com.twitter.sdk.android.core.TwitterCore;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 This class represents the {@link com.rxmuhammadyoussef.twitterc.ui.userdetails.UserDetailsActivity} gate to all the data resources
 (i.e {@link LocalStore}, {@link TwitterStore}, etc..)
 */

@ActivityScope
public class UserDetailsRepo {

    private final PreferencesUtil preferencesUtil;
    private final TwitterStore twitterTweetStore;
    private final LocalStore localTweetStore;
    private final TweetMapper tweetMapper;

    @Inject
    public UserDetailsRepo(TwitterStore twitterTweetStore,
                           PreferencesUtil preferencesUtil,
                           LocalStore localTweetStore,
                           TweetMapper tweetMapper) {
        this.preferencesUtil = preferencesUtil;
        this.twitterTweetStore = twitterTweetStore;
        this.localTweetStore = localTweetStore;
        this.tweetMapper = tweetMapper;
    }

    public Single<List<TweetEntity>> fetchTweets(long userId) {
        return twitterTweetStore.fetchTweets(userId)
                .map(tweetMapper::toEntities);
    }

    public Single<UserEntity> fetchUserDetails(long userId) {
        return localTweetStore.getFollowerDetails(userId);
    }

    public Completable clearDatabase() {
        return localTweetStore.clearDatabase()
                .doOnComplete(() -> {
                    preferencesUtil.deleteAll();
                    TwitterCore.getInstance().getSessionManager().clearActiveSession();
                });
    }
}
