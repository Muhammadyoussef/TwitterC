package com.rxmuhammadyoussef.twitterc.models.user;

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
     this method should be used when passing objects from the model layer to the presenter layer
     it maps list of {@link UserEntity} to list of {@link User}

     @param userEntities that is passed from the model layer and needs to be mapped

     @return the mapped list of Users, ready to be used in the presenter layer
     */
    public List<User> toModels(List<UserEntity> userEntities) {
        List<User> users = new ArrayList<>();
        for (UserEntity entity : userEntities) {
            users.add(
                    new User(entity.getUserId(),
                            entity.getFullName(),
                            entity.getUserName(),
                            entity.getUserBio(),
                            entity.getBackgroundUrl(),
                            entity.getAvatarUrl()));
        }
        return users;
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
    public List<UserViewModel> toViewModels(List<User> users) {
        List<UserViewModel> userViewModels = new ArrayList<>();
        for (User user : users) {
            userViewModels.add(
                    new UserViewModel(user.getUserId(),
                            user.getFullName(),
                            user.getUserName(),
                            user.getUserBio(),
                            user.getAvatarUrl()));
        }
        return userViewModels;
    }
}
