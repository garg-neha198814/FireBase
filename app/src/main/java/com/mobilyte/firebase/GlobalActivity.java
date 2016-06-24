package com.mobilyte.firebase;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by root on 26/4/16.
 */
public class GlobalActivity extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
