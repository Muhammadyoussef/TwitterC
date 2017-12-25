package com.rxmuhammadyoussef.twitterc.models.user;

import android.util.Pair;

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

    public User toUser(Pair<Long, String> idUserNamePair) {
        return new User(idUserNamePair.first, idUserNamePair.second);
    }

    public UserEntity toUserEntity(User user) {
        return new UserEntity(user.getId(), user.getUserName());
    }
}
