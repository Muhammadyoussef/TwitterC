package com.rxmuhammadyoussef.twitterc.ui.Store;

import com.rxmuhammadyoussef.twitterc.di.activity.ActivityScope;
import com.rxmuhammadyoussef.twitterc.models.tweet.TweetEntity;
import com.rxmuhammadyoussef.twitterc.models.tweet.TweetMapper;
import com.rxmuhammadyoussef.twitterc.models.user.UserEntity;
import com.rxmuhammadyoussef.twitterc.util.PreferencesUtil;
import com.twitter.sdk.android.core.TwitterCore;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 TODO: Add class header
 */

@ActivityScope
public class UserDetailsRepo {

    private final TwitterStore twitterTweetStore;
    private final PreferencesUtil preferencesUtil;
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
        return localTweetStore.getUser(userId);
    }

    public Completable clearDatabase() {
        return localTweetStore.clearDatabase()
                .doOnComplete(() -> {
                    preferencesUtil.deleteAll();
                    TwitterCore.getInstance().getSessionManager().clearActiveSession();
                });
    }
}
