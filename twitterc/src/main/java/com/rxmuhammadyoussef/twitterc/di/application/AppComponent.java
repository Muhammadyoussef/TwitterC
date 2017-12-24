package com.rxmuhammadyoussef.twitterc.di.application;

import com.rxmuhammadyoussef.twitterc.TwitterCApplication;
import com.rxmuhammadyoussef.twitterc.di.activity.ActivityComponent;
import com.rxmuhammadyoussef.twitterc.di.activity.ActivityModule;

import dagger.Component;

/**
 This interface is used by dagger to generate the code that defines the connection between the provider of objects
 (i.e. {@link AppModule}), and the object which expresses a dependency.
 */

@ApplicationScope
@Component(
        modules = {AppModule.class, SchedulersModule.class}
)
public interface AppComponent {

    ActivityComponent plus(ActivityModule activityModule);

    void inject(TwitterCApplication twitterCApplication);
}
