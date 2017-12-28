package com.rxmuhammadyoussef.twitterc.models.profile;

import java.util.List;

/**
 This class represents the profile view model object, a view-layer-ready version of the users profile details
 */

public class ProfileViewModel {

    private final long userId;
    private final String fulName;
    private final String userName;
    private final String backgroundUrl;
    private final String avatarUrl;
    private final List<TweetViewModel> tweets;

    public ProfileViewModel(long userId, String fulName, String userName, String backgroundUrl, String avatarUrl, List<TweetViewModel> tweets) {
        this.userId = userId;
        this.fulName = fulName;
        this.userName = userName;
        this.backgroundUrl = backgroundUrl;
        this.avatarUrl = avatarUrl;
        this.tweets = tweets;
    }

    public long getUserId() {
        return userId;
    }

    public String getFulName() {
        return fulName;
    }

    public String getUserName() {
        return "@".concat(userName);
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public List<TweetViewModel> getTweets() {
        return tweets;
    }
}
