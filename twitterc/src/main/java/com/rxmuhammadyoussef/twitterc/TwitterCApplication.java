package com.rxmuhammadyoussef.twitterc;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.rxmuhammadyoussef.twitterc.di.application.AppComponent;
import com.rxmuhammadyoussef.twitterc.di.application.AppModule;
import com.rxmuhammadyoussef.twitterc.di.application.ApplicationScope;
import com.rxmuhammadyoussef.twitterc.di.application.DaggerAppComponent;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import io.realm.Realm;
import timber.log.Timber;

/**
 This class represents TwitterC Application
 */

@ApplicationScope
public class TwitterCApplication extends Application {

    private final AppComponent appComponent = createComponent();

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        Twitter.initialize(new TwitterConfig.Builder(this)
                .twitterAuthConfig(new TwitterAuthConfig(
                        getString(R.string.CONSUMER_KEY),
                        getString(R.string.CONSUMER_SECRET)))
                .build());
        appComponent.inject(this);
        setStrictModeForDebugEnabled(true);
        setTimberDebugTreeEnabled(true);
    }

    private AppComponent createComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    /*
    * When enabled it detects things you might be doing by accident and brings them to your attention so you can fix them.
    * Thread policy is used to catch accidental disk or network operations on the application's MAIN thread.
    * VmPolicy is used to detect when a closeable or other object with an explicit termination method is finalized without having been closed.
    * @See https://developer.android.com/reference/android/os/StrictMode.html
    */
    private void setStrictModeForDebugEnabled(boolean enabled) {
        if (enabled && BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
        }
    }

    /*
    * When enabled we should start logging using Timber class.
    * @See https://medium.com/@caueferreira/timber-enhancing-your-logging-experience-330e8af97341
    */
    private void setTimberDebugTreeEnabled(boolean enabled) {
        if (enabled && BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    /*
    * @param context
    * @return appComponent
    */
    public static AppComponent getComponent(Context context) {
        return getApp(context).appComponent;
    }

    //This is a hack to get a non-static field from a static method (i.e. appComponent)
    private static TwitterCApplication getApp(Context context) {
        return (TwitterCApplication) context.getApplicationContext();
    }
}
