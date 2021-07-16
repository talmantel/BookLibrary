package com.openu.sadna.booklibrary.data;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.openu.sadna.booklibrary.network.pojo.User;

//Implementation of Shared Preferences storage
public class SharedPrefs {

    private final SharedPreferences sharedPrefs;
    private static SharedPrefs instance = null;

    private static final String SHARED_PREFS = "library_shared_prefs";
    private static final String SHARED_PREFS_USER_TOKEN_KEY = "user_token";
    private static final String SHARED_PREFS_USER_FIRST_NAME_KEY = "user_first_name";
    private static final String SHARED_PREFS_USER_LAST_NAME_KEY = "user_last_name";
    private static final String SHARED_PREFS_USER_IS_ADMIN_KEY = "user_is_admin";

    private SharedPrefs(Application application) {
        sharedPrefs = application.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
    }

    public synchronized static SharedPrefs getInstance(Application application){
        if(instance == null)
            instance = new SharedPrefs(application);
        return instance;
    }

    @SuppressLint("ApplySharedPref")
    public void setUser(User user){
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(SHARED_PREFS_USER_TOKEN_KEY, user.getToken());
        editor.putString(SHARED_PREFS_USER_FIRST_NAME_KEY, user.getFirstName());
        editor.putString(SHARED_PREFS_USER_LAST_NAME_KEY, user.getLastName());
        editor.putBoolean(SHARED_PREFS_USER_IS_ADMIN_KEY, user.isAdmin());
        editor.commit();
    }

    public User getUser(){
        String token = sharedPrefs.getString(SHARED_PREFS_USER_TOKEN_KEY, null);
        String fname = sharedPrefs.getString(SHARED_PREFS_USER_FIRST_NAME_KEY, null);
        String lname = sharedPrefs.getString(SHARED_PREFS_USER_LAST_NAME_KEY, null);
        boolean isAdmin = sharedPrefs.getBoolean(SHARED_PREFS_USER_IS_ADMIN_KEY, false);

        if(token != null)
            return new User(fname, lname, token, isAdmin);
        return null;
    }

    @SuppressLint("ApplySharedPref")
    public void clearUser(){
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear();
        editor.commit();
    }

}
