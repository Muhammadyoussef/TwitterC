package com.rxmuhammadyoussef.twitterc.store.model.user;

import com.rxmuhammadyoussef.twitterc.di.application.ApplicationScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 This class is responsible for mapping the user object according to the corresponding layer (i.e. View, presenter or model)
 */

@ApplicationScope
public class UserMapper {

    @Inject
    UserMapper() {

    }

    /**
     this method should be used when passing objects from the presenter layer to the model layer
     it maps list of {@link User} to list {@link UserEntity}

     @param users that is passed from the presenter layer and needs to be mapped

     @return the mapped list of UserEntities, ready to be used in the model layer
     */
    public List<UserEntity> toEntities(List<User> users) {
        List<UserEntity> userEntities = new ArrayList<>();
        for (User user : users) {
            userEntities.add(
                    new UserEntity(user.getUserId(),
                            user.getFullName(),
                            user.getUserName(),
                            user.getUserBio(),
                            user.getBackgroundUrl(),
                            user.getAvatarUrl()));
        }
        return userEntities;
    }

    /**
     this method should be used when passing objects from the presenter layer to the view layer
     it maps list of {@link User} to list of {@link UserViewModel}

     @param users that is passed from the presenter layer and needs to be mapped

     @return the mapped list of userViewModel, ready to be used in the view layer
     */
    public List<UserViewModel> toViewModels(List<UserEntity> users) {
        List<UserViewModel> userViewModels = new ArrayList<>();
        for (UserEntity user : users) {
            userViewModels.add(
                    new UserViewModel(user.getUserId(),
                            user.getFullName(),
                            user.getUserName(),
                            user.getUserBio(),
                            user.getAvatarUrl(),
                            user.getBackgroundUrl()));
        }
        return userViewModels;
    }

    /**
     this method should be used when passing objects from the presenter layer to the view layer
     it maps list of {@link User} to list of {@link UserViewModel}

     @param user that is passed from the presenter layer and needs to be mapped

     @return the mapped list of userViewModel, ready to be used in the view layer
     */
    public UserViewModel toViewModel(UserEntity user) {
        return new UserViewModel(
                user.getUserId(),
                user.getFullName(),
                user.getUserName(),
                user.getUserBio(),
                user.getAvatarUrl(),
                user.getBackgroundUrl());
    }
}
