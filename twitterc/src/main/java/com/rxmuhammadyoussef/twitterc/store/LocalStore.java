package com.rxmuhammadyoussef.twitterc.store;

import com.rxmuhammadyoussef.twitterc.di.application.ApplicationScope;
import com.rxmuhammadyoussef.twitterc.store.model.user.UserEntity;

import java.util.List;

import javax.annotation.Nullable;
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

    /**
     This method takes a list of {@link UserEntity} and perform an insert operation to the local database.

     @param entitiesToAdd list of user entities to be saved in the database.

     @return {@link Completable}, void if successful or will throw an exception if the process was not successful.
     */
    Completable saveFollowers(List<UserEntity> entitiesToAdd) {
        return Completable.create(emitter -> {
            Realm instance = Realm.getDefaultInstance();
            instance.executeTransaction(realm -> {
                for (UserEntity entity : entitiesToAdd) {
                    realm.insert(entity);
                }
            });
            instance.close();
            emitter.onComplete();
        });
    }

    /**
     This method takes a userId and performs a search operation on all {@link UserEntity} instances in the database and return the first match.

     @param userId the search key, also happens to be the primary key of {@link UserEntity}.

     @return {@link Single}, will contain a user entity if the query was matched, null otherwise.
     */
    @Nullable
    Single<UserEntity> getFollowerDetails(long userId) {
        return Single.just(userId)
                .map(entities -> {
                    Realm instance = Realm.getDefaultInstance();
                    UserEntity userEntity = instance.copyFromRealm(
                            instance.where(UserEntity.class)
                                    .equalTo("userId", userId)
                                    .findFirst());
                    instance.close();
                    return userEntity;
                });
    }

    /**
     This method observes all the changes in the database regarding all the {@link UserEntity} instances.

     @return {@link Flowable} of all the {@link UserEntity} instances stored in the database at that point of time
     */
    Flowable<List<UserEntity>> observeFollowers() {
        Realm realm = Realm.getDefaultInstance();
        return realm
                .where(UserEntity.class)
                .findAll()
                .asFlowable()
                .map(realm::copyFromRealm)
                .doOnTerminate(realm::close);
    }

    /**
     This method performs a delete operation on the entire database.

     @return {@link Completable}, void if successful or will throw an exception if the process was not successful.
     */
    Completable clearDatabase() {
        return Completable.create(emitter -> {
            Realm instance = Realm.getDefaultInstance();
            instance.executeTransaction(realm -> instance.deleteAll());
            instance.close();
            emitter.onComplete();
        });
    }
}