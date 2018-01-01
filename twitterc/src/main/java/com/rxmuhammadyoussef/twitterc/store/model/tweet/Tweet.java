package com.rxmuhammadyoussef.twitterc.store.model.tweet;

import com.google.gson.annotations.SerializedName;

/**
 This class represents the Tweet model which holds all the meta-data about a user's tweets
 */

public class Tweet {

    @SerializedName("created_at")
    private final String createdAt;
    @SerializedName("text")
    private final String text;

    Tweet(String createdAt, String text) {
        this.createdAt = createdAt;
        this.text = text;
    }

    String getCreatedAt() {
        return createdAt;
    }

    String getText() {
        return text;
    }
}
