package com.rxmuhammadyoussef.twitterc.models.tweet;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 TODO: Add class header
 */

public class TweetEntity extends RealmObject {

    @PrimaryKey
    private String createdAt;
    private String text;

    public TweetEntity() {
    }

    TweetEntity(String createdAt, String text) {
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
