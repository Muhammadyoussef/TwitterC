package com.rxmuhammadyoussef.twitterc.models.user;

/**
 This class represents the User model which holds all the meta-data about the user
 */

public class User {

    private long id;
    private String userName;

    public User(long id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }
}
