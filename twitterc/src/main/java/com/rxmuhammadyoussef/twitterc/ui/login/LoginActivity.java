package com.rxmuhammadyoussef.twitterc.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.rxmuhammadyoussef.twitterc.R;
import com.rxmuhammadyoussef.twitterc.TwitterCApplication;
import com.rxmuhammadyoussef.twitterc.di.activity.ActivityModule;
import com.rxmuhammadyoussef.twitterc.di.activity.ActivityScope;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 This class represents the view layer of the loginSuccess process which handles all the UI interactions
 */

@ActivityScope
public class LoginActivity extends Activity implements LoginScreen {

    private TwitterAuthClient authClient;

    @Inject
    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != TwitterCore.getInstance().getSessionManager().getActiveSession()) {
            //TODO: start home intent
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

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onUserReady() {
        //TODO: start home intent
    }

    @OnClick(R.id.btn_login)
    void login() {
        authClient = new TwitterAuthClient();
        authClient.authorize(this, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                presenter.loginSuccess(result.data.getUserId(), result.data.getUserName());
            }

            @Override
            public void failure(TwitterException exception) {
                presenter.loginFailure(exception);
            }
        });
    }
}
