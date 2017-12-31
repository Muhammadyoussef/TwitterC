package com.rxmuhammadyoussef.twitterc.models.profile;

import com.rxmuhammadyoussef.twitterc.models.tweet.TweetViewModel;
import com.rxmuhammadyoussef.twitterc.models.user.UserViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 This class represents the profile view model object, a view-layer-ready version of the users profile details
 */

public class ProfileViewModel {

    private final UserViewModel user;
    private final List<TweetViewModel> tweets;

    public ProfileViewModel(UserViewModel user, List<TweetViewModel> tweets) {
        this.user = user;
        this.tweets = tweets;
    }

    public static ProfileViewModel createEmpty() {
        return new ProfileViewModel(UserViewModel.createEmpty(), new ArrayList<>());
    }

    public UserViewModel getUser() {
        return user;
    }

    public List<TweetViewModel> getTweets() {
        return tweets;
    }
}
