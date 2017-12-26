package com.rxmuhammadyoussef.twitterc.util;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import com.rxmuhammadyoussef.twitterc.di.application.ApplicationScope;
import com.rxmuhammadyoussef.twitterc.event.Event;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 This is a universal event bus used to fire events across the entire app and between the layers
 */

@ApplicationScope
public class RxEventBus {

    private final Relay<Object> bus = PublishRelay.create().toSerialized();

    @Inject
    RxEventBus() {

    }

    public void send(Event event) {
        bus.accept(event);
    }

    public Observable<Object> toObservable() {
        return bus.hide();
    }
}
