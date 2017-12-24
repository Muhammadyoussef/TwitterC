package com.rxmuhammadyoussef.twitterc.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Input/Output-specific Schedulers
 */

public class IOThreadSchedulers implements ThreadSchedulers {

    @Override
    public Scheduler observeOn() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler subscribeOn() {
        return Schedulers.io();
    }
}
