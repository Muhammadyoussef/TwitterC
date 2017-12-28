package com.rxmuhammadyoussef.twitterc.event;

/**
 this event will be fired when fetch-followers-action is needed
 */

public class FetchFollowersEvent implements Event {

    public static final boolean BOTTOM = true;
    public static final boolean TOP = false;
    private final boolean isFromBottom;

    public FetchFollowersEvent(boolean isFromBottom) {this.isFromBottom = isFromBottom;}

    public boolean isFromBottom() {
        return isFromBottom;
    }
}
