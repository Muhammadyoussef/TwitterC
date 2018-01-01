package com.rxmuhammadyoussef.twitterc.store.model.tweet;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 This class represents the Tweet database entity object, a database ready-to-store version of {@link Tweet}
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
