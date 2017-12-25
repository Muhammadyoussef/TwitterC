package com.rxmuhammadyoussef.twitterc.ui.login;

import com.rxmuhammadyoussef.twitterc.di.activity.ActivityScope;
import com.rxmuhammadyoussef.twitterc.models.user.UserMapper;
import com.rxmuhammadyoussef.twitterc.schedulers.ThreadSchedulers;
import com.rxmuhammadyoussef.twitterc.schedulers.qualifier.IOThread;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

/**
 This class represents the presenter layer of the login process which handles all the logic
 */

@ActivityScope
class LoginPresenter {

    private final ThreadSchedulers threadSchedulers;
    private final CompositeDisposable disposable;
    private final LoginScreen loginScreen;
    private final LoginLocalStore store;
    private final UserMapper mapper;

    @Inject
    LoginPresenter(@IOThread ThreadSchedulers threadSchedulers, LoginScreen loginScreen, LoginLocalStore store, UserMapper mapper) {
        this.threadSchedulers = threadSchedulers;
        this.loginScreen = loginScreen;
        this.store = store;
        this.mapper = mapper;
        this.disposable = new CompositeDisposable();
    }

    void loginSuccess(long userId, String userName) {
        disposable.add(
                Single.zip(
                        Single.just(userId),
                        Single.just(userName),
                        mapper::toUser)
                        .map(mapper::toUserEntity)
                        .flatMapCompletable(store::saveUser)
                        .subscribeOn(threadSchedulers.subscribeOn())
                        .observeOn(threadSchedulers.observeOn())
                        .subscribe(loginScreen::onUserReady, Timber::e));
    }

    void loginFailure(Exception exception) {
        Timber.e(exception);
    }

    void onDestroy() {
        disposable.clear();
    }
}
