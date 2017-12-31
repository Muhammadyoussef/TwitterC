package com.rxmuhammadyoussef.twitterc.ui.Store;

import android.util.Log;

import com.rxmuhammadyoussef.twitterc.di.application.ApplicationScope;
import com.rxmuhammadyoussef.twitterc.models.user.UserEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.realm.Realm;

/**
 This class hold all the local-storage-specific operations (i.e. save, get, delete, etc..)
 */

@ApplicationScope
class LocalStore {

    @Inject
    LocalStore() {
    }

    Completable saveUsers(List<UserEntity> entitiesToAdd) {
        return Completable.fromSingle(
                Single.just(entitiesToAdd)
                        .map(entities -> {
                            Realm instance = Realm.getDefaultInstance();
                            instance.executeTransaction(realm -> {
                                for (UserEntity entity : entities) {
                                    realm.copyToRealm(entity);
                                }
                            });
                            instance.close();
                            return Completable.complete();
                        }));
    }

    Single<UserEntity> getUser(long userId) {
        return Single.just(userId)
                .map(entities -> {
                    Realm instance = Realm.getDefaultInstance();
                    UserEntity userEntity = instance.copyFromRealm(
                            instance.where(UserEntity.class)
                                    .equalTo("userId", userId)
                                    .findFirst());

                    Log.d("Muhammad:local:user", "" + userEntity);
                    instance.close();
                    return userEntity;
                });
    }

    Flowable<List<UserEntity>> observeUsers() {
        Realm realm = Realm.getDefaultInstance();
        return realm
                .where(UserEntity.class)
                .findAll()
                .asFlowable()
                .map(realm::copyFromRealm)
                .doOnTerminate(realm::close);
    }

    Completable clearDatabase() {
        return Completable.create(emitter -> {
            Realm instance = Realm.getDefaultInstance();
            instance.executeTransaction(realm -> instance.deleteAll());
            instance.close();
            emitter.onComplete();
        });
    }
}