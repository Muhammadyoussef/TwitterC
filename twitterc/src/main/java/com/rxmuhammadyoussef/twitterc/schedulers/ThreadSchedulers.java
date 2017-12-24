package com.rxmuhammadyoussef.twitterc.schedulers;

import io.reactivex.Scheduler;

/**
 This interface is implemented by different classes (i.e. {@link IOThreadSchedulers}) to define which ThreadSchedulers
 should be used for RxJava's {@link io.reactivex.Observable#observeOn} and {@link io.reactivex.Observable#subscribeOn}
 */

public interface ThreadSchedulers {

    Scheduler observeOn();

    Scheduler subscribeOn();
}
