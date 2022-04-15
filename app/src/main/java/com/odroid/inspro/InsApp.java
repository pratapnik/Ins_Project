package com.odroid.inspro;

import android.app.Application;

public class InsApp extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).networkModule(new NetworkModule()).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
