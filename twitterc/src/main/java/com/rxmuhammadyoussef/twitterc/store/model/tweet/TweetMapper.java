package com.rxmuhammadyoussef.twitterc.store.model.tweet;

import com.rxmuhammadyoussef.twitterc.di.application.ApplicationScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 This class is responsible for mapping the tweet object according to the corresponding layer (i.e. View, presenter or model)
 */

@ApplicationScope
public class TweetMapper {

    @Inject
    TweetMapper() {
    }

    /**
     this method should be used when passing objects from the model layer to the presenter layer
     it maps list of {@link TweetEntity} to list of {@link Tweet}

     @param tweetEntities that is passed from the model layer and needs to be mapped

     @return the mapped list of Tweets, ready to be used in the presenter layer
     */
    public List<Tweet> toModels(List<TweetEntity> tweetEntities) {
        List<Tweet> tweets = new ArrayList<>();
        for (TweetEntity entity : tweetEntities) {
            tweets.add(
                    new Tweet(entity.getCreatedAt(),
                            entity.getText()));
        }
        return tweets;
    }

    /**
     this method should be used when passing objects from the presenter layer to the model layer
     it maps list of {@link Tweet} to list {@link TweetEntity}

     @param tweets that is passed from the presenter layer and needs to be mapped

     @return the mapped list of TweetEntities, ready to be used in the model layer
     */
    public List<TweetEntity> toEntities(List<Tweet> tweets) {
        List<TweetEntity> tweetEntities = new ArrayList<>();
        for (Tweet entity : tweets) {
            tweetEntities.add(
                    new TweetEntity(
                            entity.getCreatedAt(),
                            entity.getText()));
        }
        return tweetEntities;
    }

    /**
     this method should be used when passing objects from the presenter layer to the view layer
     it maps list of {@link Tweet} to list of {@link TweetViewModel}

     @param tweets that is passed from the presenter layer and needs to be mapped

     @return the mapped list of TweetViewModels, ready to be used in the view layer
     */
    public List<TweetViewModel> toViewModels(List<Tweet> tweets) {
        List<TweetViewModel> tweetViewModels = new ArrayList<>();
        for (Tweet tweet : tweets) {
            tweetViewModels.add(
                    new TweetViewModel(
                            tweet.getText()));
        }
        return tweetViewModels;
    }
}
