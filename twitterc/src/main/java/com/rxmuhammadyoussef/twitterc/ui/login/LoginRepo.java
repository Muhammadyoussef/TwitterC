package com.rxmuhammadyoussef.twitterc.ui.login;

import com.rxmuhammadyoussef.twitterc.di.activity.ActivityScope;
import com.rxmuhammadyoussef.twitterc.models.user.User;
import com.rxmuhammadyoussef.twitterc.models.user.UserMapper;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 This class represents the {@link LoginPresenter} gate to all the data (cloud & local storage)
 see <a href= "https://image.slidesharecdn.com/cleanarchmeetmobile-150531221516-lva1-app6891/95/clean-architecture-android-32-638.jpg?cb=1433110630"/>
 */

@ActivityScope
class LoginRepo {

    private final LoginStore store;
    private final UserMapper mapper;

    @Inject
    LoginRepo(LoginStore store, UserMapper mapper) {
        this.store = store;
        this.mapper = mapper;
    }

    Completable save(User user) {
        return Single.just(user)
                .map(mapper::toUserEntity)
                .map(store::saveUser)
                .toCompletable();
    }
}
