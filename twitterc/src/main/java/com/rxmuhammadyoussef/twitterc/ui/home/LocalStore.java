package com.rxmuhammadyoussef.twitterc.ui.home;

import com.rxmuhammadyoussef.twitterc.di.activity.ActivityScope;
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

@ActivityScope
public class LocalStore {

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

    Completable clearDatabase() {
        return Completable.create(emitter -> {
            Realm instance = Realm.getDefaultInstance();
            instance.executeTransaction(realm -> instance.deleteAll());
            instance.close();
            emitter.onComplete();
        });
    }

    Flowable<List<UserEntity>> observeFollowers() {
        Realm realm = Realm.getDefaultInstance();
        return realm
                .where(UserEntity.class)
                .findAll()
                .asFlowable()
                .map(realm::copyFromRealm)
                .doOnTerminate(realm::close);
    }
}