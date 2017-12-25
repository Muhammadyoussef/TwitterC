package com.rxmuhammadyoussef.twitterc.models.user;

/**
 This class represents the User view model object, a view-layer-ready version of {@link User
 */

public class UserViewModel {

    private final String fulName;
    private final String userName;
    private final String bio;
    private final String imageUrl;

    UserViewModel(String fulName, String userName, String bio, String imageUrl) {
        this.fulName = fulName;
        this.userName = userName;
        this.bio = bio;
        this.imageUrl = imageUrl;
    }

    public String getFulName() {
        return fulName;
    }

    public String getUserName() {
        return "@".concat(userName);
    }

    public String getBio() {
        return bio;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
