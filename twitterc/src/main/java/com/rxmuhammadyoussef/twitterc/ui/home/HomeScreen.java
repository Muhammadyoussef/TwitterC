package com.rxmuhammadyoussef.twitterc.ui.home;

import com.rxmuhammadyoussef.twitterc.models.user.UserViewModel;

import java.util.List;

/**
 this class represents how the home page view layer should behave
 */

public interface HomeScreen {

    void setupRecyclerView();

    void setupRefreshListener();

    void showLoadingAnimationTop();

    void showLoadingAnimationBottom();

    void hideLoadingAnimation();

    void updateFollowers(List<UserViewModel> userViewModels);

    void showNetworkError();

    void logout();
}
