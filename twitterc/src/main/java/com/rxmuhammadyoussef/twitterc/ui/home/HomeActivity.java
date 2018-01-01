package com.rxmuhammadyoussef.twitterc.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rxmuhammadyoussef.twitterc.R;
import com.rxmuhammadyoussef.twitterc.TwitterCApplication;
import com.rxmuhammadyoussef.twitterc.di.activity.ActivityModule;
import com.rxmuhammadyoussef.twitterc.di.activity.ActivityScope;
import com.rxmuhammadyoussef.twitterc.store.model.user.User;
import com.rxmuhammadyoussef.twitterc.store.model.user.UserViewModel;
import com.rxmuhammadyoussef.twitterc.ui.home.HomePresenter.LoadingPosition;
import com.rxmuhammadyoussef.twitterc.ui.userdetails.UserDetailsActivity;
import com.rxmuhammadyoussef.twitterc.widget.SimpleDividerItemDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 This class represents the view layer of the Home page which handles all the UI interactions
 */

@ActivityScope
public class HomeActivity extends Activity implements HomeScreen {

    @BindView(R.id.refresh_layout_home)
    SwipeRefreshLayout homeLayout;
    @BindView(R.id.rv_followers)
    RecyclerView followersRecyclerView;
    @BindView(R.id.pb_fetching)
    ProgressBar fetchingProgressBar;

    @Inject
    RecyclerAdapter adapter;
    @Inject
    HomePresenter presenter;

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
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
        return true;
    }

    @Override
    public void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        SimpleDividerItemDecoration divider = new SimpleDividerItemDecoration(this);
        followersRecyclerView.setLayoutManager(layoutManager);
        followersRecyclerView.addItemDecoration(divider);
        followersRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setupRefreshListener() {
        homeLayout.setOnRefreshListener(() -> presenter.fetchFollowers(LoadingPosition.TOP));
    }

    @Override
    public void showLoadingAnimationTop() {
        homeLayout.setRefreshing(true);
    }

    @Override
    public void showLoadingAnimationBottom() {
        fetchingProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingAnimation() {
        fetchingProgressBar.setVisibility(View.GONE);
        homeLayout.setRefreshing(false);
    }

    @Override
    public void updateFollowers(List<UserViewModel> userViewModels) {
        adapter.update(userViewModels);
    }

    @Override
    public void showFollowerProfile(long userId) {
        Intent intent = new Intent(this, UserDetailsActivity.class);
        intent.putExtra(User.USER_ID, userId);
        startActivity(intent);
    }

    @Override
    public void showNetworkError() {
        Toast.makeText(this, R.string.network_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void logout() {
        finishAffinity();
    }

    public void onLogoutClick(MenuItem item) {
        presenter.onLogoutClick();
    }
}
