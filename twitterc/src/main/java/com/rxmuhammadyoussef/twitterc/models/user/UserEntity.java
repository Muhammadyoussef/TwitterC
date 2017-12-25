package com.rxmuhammadyoussef.twitterc.models.user;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 This class represents the User database entity object, a database-ready version of {@link User}
 */

public class UserEntity extends RealmObject {

    @PrimaryKey
    private long id;
    private String userName;

    public UserEntity() {

    }

    public UserEntity(long id, String userName) {
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
