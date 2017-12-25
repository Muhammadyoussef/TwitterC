package com.rxmuhammadyoussef.twitterc.ui.login;

import com.rxmuhammadyoussef.twitterc.di.activity.ActivityScope;
import com.rxmuhammadyoussef.twitterc.models.user.UserEntity;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.realm.Realm;

/**
 This class is responsible for cashing the user login data
 */

@ActivityScope
class LoginLocalStore {

    @Inject
    LoginLocalStore() {

    }

    Completable saveUser(UserEntity userEntity) {
        return Completable.fromCallable(() -> {
            Realm instance = Realm.getDefaultInstance();
            instance.executeTransaction(realm -> {
                realm.where(UserEntity.class)
                        .findAll()
                        .deleteAllFromRealm();
                instance.copyToRealm(userEntity);
            });
            instance.close();
            return Completable.complete();
        });
    }
}
