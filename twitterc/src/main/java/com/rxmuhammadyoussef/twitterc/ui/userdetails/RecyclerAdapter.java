package com.rxmuhammadyoussef.twitterc.ui.userdetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rxmuhammadyoussef.twitterc.R;
import com.rxmuhammadyoussef.twitterc.di.activity.ActivityScope;
import com.rxmuhammadyoussef.twitterc.di.activity.ForActivity;
import com.rxmuhammadyoussef.twitterc.models.profile.ProfileViewModel;
import com.rxmuhammadyoussef.twitterc.models.profile.TweetViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

@ActivityScope
class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER = 0;
    private static final int TWEET = 1;
    private final Context context;
    private final LayoutInflater layoutInflater;
    private ProfileViewModel currentProfileState;
    private final List<TweetViewModel> currentTweetsList = Collections.emptyList();

    @Inject
    RecyclerAdapter(@ForActivity Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER;
        } else {
            return TWEET;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TWEET) {
            return new TweetViewHolder(layoutInflater.inflate(R.layout.item_tweet, parent, false));
        } else {
            return new HeaderViewHolder(layoutInflater.inflate(R.layout.item_header, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TweetViewHolder) {
            if (currentProfileState != null) {
                ((TweetViewHolder) holder).bind(currentTweetsList.get(position));
            }
        } else if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).bind(currentProfileState);
        }
    }

    @Override
    public int getItemCount() {
        return currentTweetsList.size();
    }

    void update(ProfileViewModel newProfileState) {
        this.currentProfileState = newProfileState;
        currentTweetsList.clear();
        currentTweetsList.addAll(newProfileState.getTweets());
        notifyDataSetChanged();
    }

    class TweetViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_avatar)
        CircleImageView avatarImageView;
        @BindView(R.id.tv_full_name)
        TextView fullNameTextView;
        @BindView(R.id.tv_user_name)
        TextView userNameTextView;
        @BindView(R.id.tv_tweet)
        TextView tweetTextView;

        TweetViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(TweetViewModel tweet) {
            Glide.with(context)
                    .load(tweet.getUser().getImageUrl())
                    .into(avatarImageView);
            fullNameTextView.setText(tweet.getUser().getFulName());
            userNameTextView.setText(tweet.getUser().getUserName());
            tweetTextView.setText(tweet.getText());
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_avatar)
        CircleImageView avatarImageView;
        @BindView(R.id.iv_background)
        ImageView backgroundImageView;

        HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(ProfileViewModel profile) {
            Glide.with(context)
                    .load(profile.getAvatarUrl())
                    .into(avatarImageView);
            Glide.with(context)
                    .load(profile.getBackgroundUrl())
                    .into(backgroundImageView);
        }

        @OnClick(R.id.iv_avatar)
        void openAvatar() {
            //TODO: open avatar in browser
        }

        @OnClick(R.id.iv_background)
        void openBackground() {
            //TODO: open background in browser
        }
    }
}
