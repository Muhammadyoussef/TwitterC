package com.rxmuhammadyoussef.twitterc.ui.userdetails;

import com.rxmuhammadyoussef.twitterc.models.profile.ProfileViewModel;

/**
 this class represents how the user details page view layer should behave
 */

public interface UserDetailsScreen {

    void setupToolbar();

    void setupRecyclerView();

    void setupRefreshListener();

    void showLoadingAnimation();

    void hideLoadingAnimation();

    void updateProfile(ProfileViewModel profileViewModel);

    void showNetworkError();

    void logout();
}
