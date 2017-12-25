package com.rxmuhammadyoussef.twitterc.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.rxmuhammadyoussef.twitterc.R;
import com.rxmuhammadyoussef.twitterc.TwitterCApplication;
import com.rxmuhammadyoussef.twitterc.di.activity.ActivityModule;
import com.rxmuhammadyoussef.twitterc.di.activity.ActivityScope;
import com.rxmuhammadyoussef.twitterc.ui.home.HomeActivity;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 This class represents the view layer of the login process which handles all the UI interactions
 */

@ActivityScope
public class LoginActivity extends Activity {

    private TwitterAuthClient authClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != TwitterCore.getInstance().getSessionManager().getActiveSession()) {
            navigateToHomePage();
        }
        setContentView(R.layout.activity_login);
        TwitterCApplication.getComponent(this)
                .plus(new ActivityModule(this))
                .inject(this);
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        authClient.onActivityResult(requestCode, resultCode, data);
    }

    private void navigateToHomePage() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @OnClick(R.id.btn_login)
    void login() {
        authClient = new TwitterAuthClient();
        authClient.authorize(this, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                navigateToHomePage();
            }

            @Override
            public void failure(TwitterException exception) {
                Timber.e(exception);
            }
        });
    }
}
