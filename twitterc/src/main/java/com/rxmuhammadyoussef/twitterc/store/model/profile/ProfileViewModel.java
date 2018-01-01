package com.rxmuhammadyoussef.twitterc.store.model.profile;

import com.rxmuhammadyoussef.twitterc.store.model.tweet.TweetViewModel;
import com.rxmuhammadyoussef.twitterc.store.model.user.UserViewModel;

import java.util.Collections;
import java.util.List;

/**
 This class represents the profile view model object, a combined view-layer-ready object of
 {@link com.rxmuhammadyoussef.twitterc.store.model.user.UserViewModel}
 and {@link com.rxmuhammadyoussef.twitterc.store.model.tweet.TweetViewModel}
 */

public class ProfileViewModel {

    private final UserViewModel user;
    private final List<TweetViewModel> tweets;

    public ProfileViewModel(UserViewModel user, List<TweetViewModel> tweets) {
        this.user = user;
        this.tweets = tweets;
    }

    /**
     This method creates an empty-state user profile object.

     @return ProfileViewModel with empty stings, empty lists and -1 userId
     */
    public static ProfileViewModel emptyProfile() {
        return new ProfileViewModel(UserViewModel.createEmpty(), Collections.emptyList());
    }

    public UserViewModel getUser() {
        return user;
    }

    public List<TweetViewModel> getTweets() {
        return tweets;
    }
}
