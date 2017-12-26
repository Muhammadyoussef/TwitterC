package com.rxmuhammadyoussef.twitterc.models.user;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 This class represents the User database entity object, a database ready-to-store version of {@link User}
 */

public class UserEntity extends RealmObject {

    @PrimaryKey
    private long userId;
    private String fullName;
    private String userName;
    private String userBio;
    private String backgroundUrl;
    private String avatarUrl;

    public UserEntity() {

    }

    UserEntity(long userId, String fullName, String userName, String userBio, String backgroundUrl, String avatarUrl) {
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
