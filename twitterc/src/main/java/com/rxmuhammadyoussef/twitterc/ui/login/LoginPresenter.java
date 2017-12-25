package com.rxmuhammadyoussef.twitterc.ui.login;

import android.util.Pair;

import com.rxmuhammadyoussef.twitterc.di.activity.ActivityScope;
import com.rxmuhammadyoussef.twitterc.models.user.UserMapper;
import com.rxmuhammadyoussef.twitterc.schedulers.ThreadSchedulers;
import com.rxmuhammadyoussef.twitterc.schedulers.qualifier.IOThread;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

/**
 This class represents the presenter layer of the loginSuccess process which handles all the logic
 */

@ActivityScope
class LoginPresenter {

    private final ThreadSchedulers threadSchedulers;
    private final CompositeDisposable disposable;
    private final LoginScreen loginScreen;
    private final UserMapper mapper;
    private final LoginRepo repo;

    @Inject
    LoginPresenter(@IOThread ThreadSchedulers threadSchedulers, LoginScreen loginScreen, LoginRepo repo,
                   UserMapper mapper) {
        this.threadSchedulers = threadSchedulers;
        this.loginScreen = loginScreen;
        this.repo = repo;
        this.mapper = mapper;
        disposable = new CompositeDisposable();
    }

    void loginSuccess(long userId, String userName) {
        disposable.add(
                Single.just(new Pair<>(userId, userName))
                        .map(mapper::toUser)
                        .flatMapCompletable(repo::save)
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
