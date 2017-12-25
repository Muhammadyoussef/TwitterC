package com.rxmuhammadyoussef.twitterc.ui.login;

import android.app.Activity;
import android.os.Bundle;

import com.rxmuhammadyoussef.twitterc.R;
import com.rxmuhammadyoussef.twitterc.TwitterCApplication;
import com.rxmuhammadyoussef.twitterc.di.activity.ActivityModule;
import com.rxmuhammadyoussef.twitterc.di.activity.ActivityScope;

import butterknife.ButterKnife;
import butterknife.OnClick;

@ActivityScope
public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TwitterCApplication.getComponent(this)
                .plus(new ActivityModule(this))
                .inject(this);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    public void login() {

    }
}
