package com.odroid.inspro;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, ViewModelModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);

    MoviesManager provideMovieManager();
}
