package com.rxmuhammadyoussef.twitterc.di.application;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 This class is responsible for providing the requested objects for {@link ApplicationScope} objects
 */

@Module(
        includes = {SchedulersModule.class}
)
public class AppModule {

    private final Application application;

    public AppModule(Application application) {this.application = application;}

    @ApplicationScope
    @Provides
    public Application providesApplication() {
        return application;
    }

    @ApplicationScope
    @Provides
    @ForApplication
    public Context providesApplicationContext() {
        return application;
    }
}
