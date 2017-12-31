package com.rxmuhammadyoussef.twitterc.models.tweet;

import com.google.gson.annotations.SerializedName;

/**
 TODO: Add class header
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
