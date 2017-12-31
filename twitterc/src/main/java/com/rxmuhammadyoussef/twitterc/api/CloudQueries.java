package com.rxmuhammadyoussef.twitterc.api;

import com.rxmuhammadyoussef.twitterc.models.tweet.Tweet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 This class hold all the queries need to communicate with Twitter's API.
 */

public interface CloudQueries {

    @GET("1.1/followers/list.json")
    Call<FollowersResponse> getFollowers(@Query("user_id") long id, @Query("cursor") long cursor);

    @GET("1.1/statuses/user_timeline.json")
    Call<List<Tweet>> getTweets(@Query("user_id") long id, @Query("count") int count);
}
