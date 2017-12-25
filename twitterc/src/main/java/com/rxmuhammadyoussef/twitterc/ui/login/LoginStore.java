package com.rxmuhammadyoussef.twitterc.ui.login;

import com.rxmuhammadyoussef.twitterc.di.activity.ActivityScope;
import com.rxmuhammadyoussef.twitterc.models.user.UserEntity;

import javax.inject.Inject;

import io.reactivex.Single;
import io.realm.Realm;

/**
 This class is responsible for cashing the user login data
 */

@ActivityScope
class LoginStore {

    @Inject
    LoginStore() {

    }

    Single<UserEntity> saveUser(UserEntity userEntity) {
        return Single.just(userEntity)
                .map(entity -> {
                    Realm instance = Realm.getDefaultInstance();
                    instance.where(UserEntity.class)
                            .findAll()
                            .deleteAllFromRealm();
                    instance.copyToRealm(entity);
                    instance.close();
                    return entity;
                });
    }
}
