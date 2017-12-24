package com.rxmuhammadyoussef.twitterc.di.application;

import com.rxmuhammadyoussef.twitterc.schedulers.ComputationalThreadSchedulers;
import com.rxmuhammadyoussef.twitterc.schedulers.IOThreadSchedulers;
import com.rxmuhammadyoussef.twitterc.schedulers.MainThreadSchedulers;
import com.rxmuhammadyoussef.twitterc.schedulers.ThreadSchedulers;
import com.rxmuhammadyoussef.twitterc.schedulers.qualifier.ComputationalThread;
import com.rxmuhammadyoussef.twitterc.schedulers.qualifier.IOThread;
import com.rxmuhammadyoussef.twitterc.schedulers.qualifier.MainThread;

import dagger.Module;
import dagger.Provides;

/**
 This class is responsible for providing the requested objects for {@link ThreadSchedulers} objects
 */

@Module
public class SchedulersModule {

    @ApplicationScope
    @Provides
    @MainThread
    public final ThreadSchedulers providesMainThreadSchedulers() {
        return new MainThreadSchedulers();
    }

    @ApplicationScope
    @Provides
    @IOThread
    public final ThreadSchedulers providesIOThreadSchedulers() {
        return new IOThreadSchedulers();
    }

    @ApplicationScope
    @Provides
    @ComputationalThread
    public final ThreadSchedulers providesComputationalThreadSchedulers() {
        return new ComputationalThreadSchedulers();
    }
}
