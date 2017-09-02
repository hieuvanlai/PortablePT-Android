package com.example.hihihahahehe.portablept;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by hieuit on 8/31/17.
 */

public class RealmApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
