package com.rxmuhammadyoussef.twitterc.ui.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rxmuhammadyoussef.twitterc.R;
import com.rxmuhammadyoussef.twitterc.di.activity.ActivityScope;
import com.rxmuhammadyoussef.twitterc.di.activity.ForActivity;
import com.rxmuhammadyoussef.twitterc.event.FetchFollowersEvent;
import com.rxmuhammadyoussef.twitterc.models.user.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

@ActivityScope
class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.FollowerViewHolder> {

    private final Context context;
    private final HomePresenter presenter;
    private final List<UserViewModel> currentFollowersList = new ArrayList<>();

    @Inject
    RecyclerAdapter(@ForActivity Context context, HomePresenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public FollowerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FollowerViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_follower,
                        parent,
                        false)
        );
    }

    @Override
    public void onBindViewHolder(FollowerViewHolder holder, int position) {
        holder.bind(currentFollowersList.get(position));
        if (position == currentFollowersList.size() - 1) {
            presenter.fetchFollowers(FetchFollowersEvent.BOTTOM);
        }
    }

    @Override
    public int getItemCount() {
        return currentFollowersList.size();
    }

    void update(List<UserViewModel> newFollowersList) {
        currentFollowersList.clear();
        currentFollowersList.addAll(newFollowersList);
        notifyDataSetChanged();
    }

    class FollowerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_avatar)
        CircleImageView avatarImageView;
        @BindView(R.id.tv_full_name)
        TextView fullNameTextView;
        @BindView(R.id.tv_user_name)
        TextView userNameTextView;
        @BindView(R.id.tv_user_bio)
        TextView userBioTextView;

        FollowerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(UserViewModel viewModel) {
            Glide.with(context)
                    .load(viewModel.getImageUrl())
                    .into(avatarImageView);
            fullNameTextView.setText(viewModel.getFulName());
            userNameTextView.setText(viewModel.getUserName());
            userBioTextView.setText(viewModel.getBio());
        }

        @OnClick(R.id.item_follower_container)
        void onItemClick() {
            //TODO: open user details activity
        }
    }
}
