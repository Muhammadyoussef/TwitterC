package com.rxmuhammadyoussef.twitterc.store.model.tweet;

/**
 This class represents the user view model object, a view-layer-ready version of {@link Tweet}
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
