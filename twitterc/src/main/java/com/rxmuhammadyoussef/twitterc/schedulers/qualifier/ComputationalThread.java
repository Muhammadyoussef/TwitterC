package com.rxmuhammadyoussef.twitterc.schedulers.qualifier;

import javax.inject.Qualifier;

/**
 * This qualifier is used for distinguishing between different {@link com.rxmuhammadyoussef.twitterc.schedulers.ThreadSchedulers} for dependency injection.
 */

@Qualifier
public @interface ComputationalThread {
}
