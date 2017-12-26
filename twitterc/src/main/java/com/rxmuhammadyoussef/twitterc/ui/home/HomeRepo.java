package com.rxmuhammadyoussef.twitterc.ui.home;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.rxmuhammadyoussef.twitterc.di.activity.ActivityScope;
import com.rxmuhammadyoussef.twitterc.event.LogoutEvent;
import com.rxmuhammadyoussef.twitterc.models.user.User;
import com.rxmuhammadyoussef.twitterc.models.user.UserMapper;
import com.rxmuhammadyoussef.twitterc.schedulers.ThreadSchedulers;
import com.rxmuhammadyoussef.twitterc.schedulers.qualifier.IOThread;
import com.rxmuhammadyoussef.twitterc.util.PreferencesUtil;
import com.rxmuhammadyoussef.twitterc.util.RxEventBus;
import com.twitter.sdk.android.core.TwitterCore;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.internal.functions.Functions;
import timber.log.Timber;

/**
 This class represents the {@link HomePresenter} gate to all the data resources (i.e {@link LocalStore}, {@link TwitterStore}, etc..)
 */

@ActivityScope
class HomeRepo {

    private final BehaviorRelay<List<User>> usersRelay;
    private final ThreadSchedulers threadSchedulers;
    private final PreferencesUtil preferencesUtil;
    private final CompositeDisposable disposable;
    private final TwitterStore twitterStore;
    private final LocalStore localStore;
    private final RxEventBus eventBus;
    private final UserMapper mapper;

    @Inject
    HomeRepo(@IOThread ThreadSchedulers threadSchedulers,
             PreferencesUtil preferencesUtil,
             CompositeDisposable disposable,
             TwitterStore twitterStore,
             LocalStore localStore,
             RxEventBus eventBus,
             UserMapper mapper) {
        this.threadSchedulers = threadSchedulers;
        this.preferencesUtil = preferencesUtil;
        this.disposable = disposable;
        this.twitterStore = twitterStore;
        this.localStore = localStore;
        this.eventBus = eventBus;
        this.mapper = mapper;
        this.usersRelay = BehaviorRelay.createDefault(Collections.emptyList());
    }

    void onCreate() {
        disposable.add(
                localStore.observeFollowers()
                        .map(mapper::toModels)
                        /*subscribeOn and subscribeOn
                         were intentionally left out due some limitations in Realm's multi-threading.
                         Realm objects can only be accessed on the thread they were created*/
                        .subscribe(usersRelay::accept, Timber::e));

        disposable.add(
                twitterStore.observeFollowers()
                        .map(mapper::toEntities)
                        .flatMapCompletable(localStore::saveUsers)
                        .subscribeOn(threadSchedulers.subscribeOn())
                        .observeOn(threadSchedulers.observeOn())
                        .subscribe(Functions.EMPTY_ACTION, Timber::e));
    }

    void clearDatabase() {
        localStore.clearDatabase()
                .subscribeOn(threadSchedulers.subscribeOn())
                .observeOn(threadSchedulers.observeOn())
                .subscribe(() -> {
                    preferencesUtil.deleteAll();
                    TwitterCore.getInstance().getSessionManager().clearActiveSession();
                    eventBus.send(new LogoutEvent());
                }, Timber::e);
    }

    Observable<List<User>> observeFollowers() {
        return usersRelay.hide();
    }
}
