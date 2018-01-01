package com.rxmuhammadyoussef.twitterc.store;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.rxmuhammadyoussef.twitterc.di.activity.ActivityScope;
import com.rxmuhammadyoussef.twitterc.schedulers.ThreadSchedulers;
import com.rxmuhammadyoussef.twitterc.schedulers.qualifier.IOThread;
import com.rxmuhammadyoussef.twitterc.store.model.user.UserEntity;
import com.rxmuhammadyoussef.twitterc.store.model.user.UserMapper;
import com.rxmuhammadyoussef.twitterc.util.PreferencesUtil;
import com.twitter.sdk.android.core.TwitterCore;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.internal.functions.Functions;
import timber.log.Timber;

/**
 This class represents the {@link com.rxmuhammadyoussef.twitterc.ui.home.HomeActivity} gate to all the data resources
 (i.e {@link LocalStore}, {@link TwitterStore}, etc..)
 */

@ActivityScope
public class HomeRepo {

    private final BehaviorRelay<List<UserEntity>> usersRelay;
    private final ThreadSchedulers threadSchedulers;
    private final PreferencesUtil preferencesUtil;
    private final CompositeDisposable disposable;
    private final TwitterStore twitterStore;
    private final LocalStore localStore;
    private final UserMapper mapper;

    @Inject
    public HomeRepo(@IOThread ThreadSchedulers threadSchedulers,
                    PreferencesUtil preferencesUtil,
                    CompositeDisposable disposable,
                    TwitterStore twitterStore,
                    LocalStore localStore,
                    UserMapper mapper) {
        this.threadSchedulers = threadSchedulers;
        this.preferencesUtil = preferencesUtil;
        this.disposable = disposable;
        this.twitterStore = twitterStore;
        this.localStore = localStore;
        this.mapper = mapper;
        this.usersRelay = BehaviorRelay.createDefault(Collections.emptyList());
    }

    public void onCreate() {
        disposable.add(
                localStore.observeFollowers()
                        /*subscribeOn and subscribeOn
                         were intentionally left out due some limitations in Realm's multi-threading.
                         Realm objects can only be accessed on the thread they were created*/
                        .subscribe(usersRelay::accept, Timber::e));
    }

    public void fetchFollowers() {
        disposable.add(
                twitterStore.fetchFollowers()
                        .map(mapper::toEntities)
                        .flatMapCompletable(localStore::saveFollowers)
                        .subscribeOn(threadSchedulers.subscribeOn())
                        .observeOn(threadSchedulers.observeOn())
                        .subscribe(Functions.EMPTY_ACTION, Timber::e));
    }

    public Completable clearDatabase() {
        return localStore.clearDatabase()
                .doOnComplete(() -> {
                    preferencesUtil.deleteAll();
                    TwitterCore.getInstance().getSessionManager().clearActiveSession();
                });
    }

    public Observable<List<UserEntity>> observeFollowers() {
        return usersRelay.hide();
    }
}
