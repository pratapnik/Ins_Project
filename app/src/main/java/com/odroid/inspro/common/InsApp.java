package com.odroid.inspro.common;

import android.app.Application;
import android.content.Context;

import com.odroid.inspro.di.AppComponent;
import com.odroid.inspro.di.AppModule;
import com.odroid.inspro.di.DaggerAppComponent;
import com.odroid.inspro.di.NetworkModule;

public class InsApp extends Application {

    private AppComponent appComponent;

    private static InsApp insApp;

    @Override
    public void onCreate() {
        super.onCreate();

        insApp = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).networkModule(new NetworkModule()).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public static Application getApplication() {
        return insApp;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }
}
