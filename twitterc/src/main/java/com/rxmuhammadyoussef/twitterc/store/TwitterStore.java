package com.rxmuhammadyoussef.twitterc.store;

import com.rxmuhammadyoussef.twitterc.api.CloudQueries;
import com.rxmuhammadyoussef.twitterc.api.FollowersResponse;
import com.rxmuhammadyoussef.twitterc.di.application.ApplicationScope;
import com.rxmuhammadyoussef.twitterc.event.FetchFollowersNetworkFailureEvent;
import com.rxmuhammadyoussef.twitterc.event.FetchFollowersSuccessfulEvent;
import com.rxmuhammadyoussef.twitterc.event.FetchTweetsNetworkFailureEvent;
import com.rxmuhammadyoussef.twitterc.event.FetchTweetsSuccessfulEvent;
import com.rxmuhammadyoussef.twitterc.store.model.tweet.Tweet;
import com.rxmuhammadyoussef.twitterc.store.model.user.User;
import com.rxmuhammadyoussef.twitterc.util.PreferencesUtil;
import com.rxmuhammadyoussef.twitterc.util.RxEventBus;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 This class hold all the cloud-storage-specific operations for the Twitter API (i.e. save, get, delete, etc..)
 */

@ApplicationScope
class TwitterStore extends TwitterApiClient {

    private static final String CURSOR = "cursor";

    private final PreferencesUtil preferencesUtil;
    private final RxEventBus eventBus;

    @Inject
    TwitterStore(PreferencesUtil preferencesUtil, RxEventBus eventBus) {
        this.preferencesUtil = preferencesUtil;
        this.eventBus = eventBus;
    }

    /**
     This method returns a collection of the most recent Tweets (max: 10 tweets) posted by the user indicated by the user_id

     @param userId the ID of the user for whom to return results.

     @return {@link Single}, will contain a list of {@link Tweet} if the operation was successful or will throw an exception
     */
    Single<List<Tweet>> fetchTweets(long userId) {
        return Single.create(emitter -> getService(CloudQueries.class)
                .getTweets(userId, 10)
                .enqueue(new Callback<List<Tweet>>() {
                    @Override
                    public void success(Result<List<Tweet>> result) {
                        eventBus.send(new FetchTweetsSuccessfulEvent());
                        emitter.onSuccess(result.data);
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        eventBus.send(new FetchTweetsNetworkFailureEvent());
                        emitter.onError(exception);
                    }
                }));
    }

    /**
     This method returns a collection of followers of the currently logged in user

     @return {@link Single}, will contain a list of {@link User} if the operation was successful or will throw an exception
     */
    Single<List<User>> fetchFollowers() {
        return Single.create(emitter -> getService(CloudQueries.class)
                .getFollowers(TwitterCore.getInstance().getSessionManager().getActiveSession().getUserId(), preferencesUtil.getLong(CURSOR))
                .enqueue(new Callback<FollowersResponse>() {
                    @Override
                    public void success(Result<FollowersResponse> result) {
                        eventBus.send(new FetchFollowersSuccessfulEvent());
                        preferencesUtil.saveOrUpdateLong(CURSOR, result.data.getNextCursor());
                        emitter.onSuccess(result.data.getUsers());
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        eventBus.send(new FetchFollowersNetworkFailureEvent());
                        emitter.onError(exception);
                    }
                }));
    }
}
