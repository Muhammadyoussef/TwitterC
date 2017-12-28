package com.rxmuhammadyoussef.twitterc.models.user;

import com.google.gson.annotations.SerializedName;

/**
 This class represents the User model which holds all the meta-data about the user
 */

public class User {

    public static final String USER_ID = "userId";

    @SerializedName("id")
    private final long userId;

    @SerializedName("name")
    private final String fullName;

    @SerializedName("screen_name")
    private final String userName;

    @SerializedName("description")
    private final String userBio;

    @SerializedName("profile_background_image_url")
    private final String backgroundUrl;

    @SerializedName("profile_image_url")
    private final String avatarUrl;

    User(long userId, String fullName, String userName, String userBio, String backgroundUrl, String avatarUrl) {
        this.userId = userId;
        this.fullName = fullName;
        this.userName = userName;
        this.userBio = userBio;
        this.backgroundUrl = backgroundUrl;
        this.avatarUrl = avatarUrl;
    }

    long getUserId() {
        return userId;
    }

    String getFullName() {
        return fullName;
    }

    String getUserName() {
        return userName;
    }

    String getUserBio() {
        return userBio;
    }

    String getBackgroundUrl() {
        return backgroundUrl;
    }

    String getAvatarUrl() {
        return avatarUrl;
    }
}
