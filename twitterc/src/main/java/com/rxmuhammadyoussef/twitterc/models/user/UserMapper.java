package com.rxmuhammadyoussef.twitterc.models.user;

import com.rxmuhammadyoussef.twitterc.di.application.ApplicationScope;

import javax.inject.Inject;

/**
 This class is responsible for mapping the user object according to the corresponding layer (i.e. View, presenter or model)
 */

@ApplicationScope
public class UserMapper {

    @Inject
    UserMapper() {

    }

    public User toUser(long userId, String userName) {
        return new User(userId, userName);
    }

    public UserEntity toUserEntity(User user) {
        return new UserEntity(user.getId(), user.getUserName());
    }
}
