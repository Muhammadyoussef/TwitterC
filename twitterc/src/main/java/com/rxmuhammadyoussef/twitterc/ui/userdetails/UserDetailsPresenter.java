package com.rxmuhammadyoussef.twitterc.ui.userdetails;

import com.rxmuhammadyoussef.twitterc.di.activity.ActivityScope;
import com.rxmuhammadyoussef.twitterc.models.profile.ProfileViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 This class represents the presenter layer of the user details page which handles all the logic
 */

@ActivityScope
class UserDetailsPresenter {

    private final UserDetailsScreen userDetailsScreen;

    @Inject
    UserDetailsPresenter(UserDetailsScreen userDetailsScreen) {
        this.userDetailsScreen = userDetailsScreen;
    }

    void onCreate() {
        userDetailsScreen.setupToolbar();
        userDetailsScreen.setupRefreshListener();
        userDetailsScreen.setupRecyclerView();
    }

    void refreshProfile() {

    }

    void onLogoutClick() {

    }

    void onDestroy() {

    }
}
