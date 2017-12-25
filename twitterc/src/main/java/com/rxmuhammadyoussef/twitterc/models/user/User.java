package com.rxmuhammadyoussef.twitterc.models.user;

/**
 This class represents the User model which holds all the meta-data about the user
 */

public class User {

    private long id;
    private String userName;

    User(long id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    long getId() {
        return id;
    }

    String getUserName() {
        return userName;
    }
}
