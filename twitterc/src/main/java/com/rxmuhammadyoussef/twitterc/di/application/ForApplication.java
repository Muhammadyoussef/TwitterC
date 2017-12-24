package com.rxmuhammadyoussef.twitterc.di.application;

import javax.inject.Qualifier;

/**
 This qualifier is used for distinguishing between similar objects for dependency injection.
 (i.e. Activity context and App context), acts like {@link javax.inject.Named}
 */

@Qualifier
public @interface ForApplication {
}
