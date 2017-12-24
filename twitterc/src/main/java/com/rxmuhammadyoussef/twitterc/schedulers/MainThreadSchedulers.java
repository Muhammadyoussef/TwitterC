package com.rxmuhammadyoussef.twitterc.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 MainThread-specific schedulers
 */

public class MainThreadSchedulers implements ThreadSchedulers {

    @Override
    public Scheduler observeOn() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler subscribeOn() {
        return AndroidSchedulers.mainThread();
    }
}
