package com.rxmuhammadyoussef.twitterc.di.activity;

import android.app.Activity;
import android.content.Context;

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
}
