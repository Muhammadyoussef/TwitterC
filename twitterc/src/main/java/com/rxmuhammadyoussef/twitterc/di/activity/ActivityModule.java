package com.rxmuhammadyoussef.twitterc.di.activity;

import android.app.Activity;
import android.content.Context;

import com.rxmuhammadyoussef.twitterc.ui.home.HomeScreen;
import com.rxmuhammadyoussef.twitterc.ui.userdetails.UserDetailsScreen;

import dagger.Module;
import dagger.Provides;

/**
 This class is responsible for providing the requested objects for {@link ActivityScope} objects
 */

@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {this.activity = activity;}

    @ActivityScope
    @Provides
    public final Activity provideActivity() {
        return activity;
    }

    @ActivityScope
    @Provides
    @ForActivity
    public final Context provideActivityContext() {
        return activity;
    }

    @ActivityScope
    @Provides
    public final HomeScreen provideHomeScreen() {
        return (HomeScreen) activity;
    }

    @ActivityScope
    @Provides
    public final UserDetailsScreen provideUserDetailsScreen() {
        return (UserDetailsScreen) activity;
    }
}
