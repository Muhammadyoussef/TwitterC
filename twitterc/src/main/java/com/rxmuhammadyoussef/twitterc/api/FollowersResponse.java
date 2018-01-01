package com.rxmuhammadyoussef.twitterc.api;

import com.google.gson.annotations.SerializedName;
import com.rxmuhammadyoussef.twitterc.store.model.user.User;

import java.util.List;

/**
 This class represents the Twitter GET followers list API response
 */

public class FollowersResponse {

    @SerializedName("users")
    private final List<User> users;

    @SerializedName("next_cursor")
    private final long nextCursor;

    FollowersResponse(List<User> users, long nextCursor) {
        this.users = users;
        this.nextCursor = nextCursor;
    }

    public List<User> getUsers() {
        return users;
    }

    public long getNextCursor() {
        return nextCursor;
    }
}
