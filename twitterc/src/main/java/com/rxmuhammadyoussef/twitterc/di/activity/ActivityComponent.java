package com.rxmuhammadyoussef.twitterc.di.activity;

/**
 This interface is used by dagger to generate the code that defines the connection between the provider of objects
 (i.e. {@link com.rxmuhammadyoussef.twitterc.di.activity.ActivityModule}), and the object which expresses a dependency.
 */

import com.rxmuhammadyoussef.twitterc.ui.home.HomeActivity;
import com.rxmuhammadyoussef.twitterc.ui.login.LoginActivity;
import com.rxmuhammadyoussef.twitterc.ui.userdetails.UserDetailsActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {

    void inject(LoginActivity loginActivity);

    void inject(HomeActivity homeActivity);

    void inject(UserDetailsActivity userDetailsActivity);
}
