package com.rxmuhammadyoussef.twitterc.models.tweet;

/**
 TODO: Add class header
 */

public class TweetViewModel {

    private final String text;

    TweetViewModel(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
