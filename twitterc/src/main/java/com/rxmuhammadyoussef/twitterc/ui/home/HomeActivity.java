package com.rxmuhammadyoussef.twitterc.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.rxmuhammadyoussef.twitterc.R;
import com.rxmuhammadyoussef.twitterc.TwitterCApplication;
import com.rxmuhammadyoussef.twitterc.di.activity.ActivityModule;
import com.twitter.sdk.android.core.TwitterCore;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 This class represents the view layer of the Home page which handles all the UI interactions
 */

public class HomeActivity extends Activity implements HomeScreen {

    @BindView(R.id.rv_followers)
    RecyclerView followersRecyclerView;

    @Inject
    HomePresenter presenter;
    @Inject
    RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        TwitterCApplication.getComponent(this)
                .plus(new ActivityModule(this))
                .inject(this);
        ButterKnife.bind(this);
        presenter.onCreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
        return true;
    }

    @Override
    public void setupRecyclerView() {
        followersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        followersRecyclerView.setAdapter(adapter);
    }

    public void onLogoutClick(MenuItem item) {
        TwitterCore.getInstance().getSessionManager().clearActiveSession();
        finish();
    }
}
