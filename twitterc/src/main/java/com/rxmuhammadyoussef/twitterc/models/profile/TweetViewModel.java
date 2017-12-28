package com.rxmuhammadyoussef.twitterc.models.profile;

import com.rxmuhammadyoussef.twitterc.models.user.UserViewModel;

/**
 This class represents the Tweet view model object, a view-layer-ready version of the tweet details
 */

public class TweetViewModel {

    private final String text;
    private final UserViewModel user;

    public TweetViewModel(String text, UserViewModel user) {
        this.text = text;
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public UserViewModel getUser() {
        return user;
    }
}
