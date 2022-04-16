package com.odroid.inspro;

import android.app.Application;
import android.content.Context;

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
