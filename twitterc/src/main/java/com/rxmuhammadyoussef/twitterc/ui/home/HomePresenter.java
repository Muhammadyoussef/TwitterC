package com.rxmuhammadyoussef.twitterc.ui.home;

import com.rxmuhammadyoussef.twitterc.di.activity.ActivityScope;
import com.rxmuhammadyoussef.twitterc.models.user.UserViewModel;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 This class represents the presenter layer of the home page which handles all the logic
 */

@ActivityScope
class HomePresenter {

    private final HomeScreen homeScreen;

    @Inject
    HomePresenter(HomeScreen homeScreen) {
        this.homeScreen = homeScreen;
    }

    void onCreate() {
        homeScreen.setupRecyclerView();
    }

    List<UserViewModel> getCurrentFollowersViewModels() {
        return Collections.emptyList();
    }
}
