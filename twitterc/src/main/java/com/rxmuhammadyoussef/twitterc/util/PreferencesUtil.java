package com.rxmuhammadyoussef.twitterc.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.rxmuhammadyoussef.twitterc.di.application.ApplicationScope;
import com.rxmuhammadyoussef.twitterc.di.application.ForApplication;

import javax.inject.Inject;

/**
 This util class hold all the required method to save, update or delete items in {@link SharedPreferences}
 */

@ApplicationScope
public class PreferencesUtil {

    private final static String PREFERENCES = "myPreferences";
    private final Context context;

    @Inject
    public PreferencesUtil(@ForApplication Context context) {
        this.context = context;
    }

    /**
     this method used to save new long or update and existing one

     @param key   that will identifies the passed element
     @param value that needs to be persisted
     */
    public void saveOrUpdateLong(String key, long value) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     this method used to retrieve a long value

     @param key that identifies the element

     @return the value or -1 if not found
     */
    public long getLong(String key) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES, Activity.MODE_PRIVATE);
        return sp.getLong(key, -1);
    }

    /**
     this method used to delete specific element from the sharedPreferences

     @param key that identifies the element
     */
    public void delete(String key) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     this method used to delete all the elements from the sharedPreferences
     */
    public void deleteAll() {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }
}
