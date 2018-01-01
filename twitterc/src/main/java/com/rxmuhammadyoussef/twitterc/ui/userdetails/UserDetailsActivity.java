package com.rxmuhammadyoussef.twitterc.ui.userdetails;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.rxmuhammadyoussef.twitterc.R;
import com.rxmuhammadyoussef.twitterc.TwitterCApplication;
import com.rxmuhammadyoussef.twitterc.di.activity.ActivityModule;
import com.rxmuhammadyoussef.twitterc.di.activity.ActivityScope;
import com.rxmuhammadyoussef.twitterc.store.model.profile.ProfileViewModel;
import com.rxmuhammadyoussef.twitterc.store.model.user.User;
import com.rxmuhammadyoussef.twitterc.widget.SimpleDividerItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 This class represents the view layer of the user details page which handles all the UI interactions
 */

@ActivityScope
public class UserDetailsActivity extends Activity implements UserDetailsScreen {

    private long userId;

    @BindView(R.id.rv_profile)
    RecyclerView tweetsRecyclerView;
    @BindView(R.id.refresh_layout_user_details)
    SwipeRefreshLayout profileLayout;

    @Inject
    UserDetailsPresenter presenter;
    @Inject
    RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        ButterKnife.bind(this);
        TwitterCApplication.getComponent(this)
                .plus(new ActivityModule(this))
                .inject(this);
        userId = getIntent().getLongExtra(User.USER_ID, -1);
        presenter.onCreate(userId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_details, menu);
        return true;
    }

    @Override
    public void setupToolbar() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        SimpleDividerItemDecoration divider = new SimpleDividerItemDecoration(this);
        tweetsRecyclerView.setLayoutManager(layoutManager);
        tweetsRecyclerView.addItemDecoration(divider);
        tweetsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setupRefreshListener() {
        profileLayout.setOnRefreshListener(() -> presenter.fetchProfile(userId));
    }

    @Override
    public void showLoadingAnimation() {
        profileLayout.setRefreshing(true);
    }

    @Override
    public void hideLoadingAnimation() {
        profileLayout.setRefreshing(false);
    }

    @Override
    public void updateProfile(ProfileViewModel profileViewModel) {
        adapter.update(profileViewModel);
    }

    @Override
    public void showNetworkError() {
        Toast.makeText(this, R.string.network_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void logout() {
        finishAffinity();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    public void onLogoutClick(MenuItem item) {
        presenter.onLogoutClick();
    }
}
