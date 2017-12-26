package com.rxmuhammadyoussef.twitterc.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 This class hold all the queries need to communicate with Twitter's API.
 see {@link com.rxmuhammadyoussef.twitterc.ui.home.TwitterStore}
 */

public interface CloudQueries {

    @GET("1.1/followers/list.json")
    Call<FollowersResponse> getFollowers(@Query("user_id") long id, @Query("cursor") long cursor);
}
