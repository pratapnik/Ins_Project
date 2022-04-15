package com.odroid.inspro;

import android.app.Application;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Application mApplication;

    public AppModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    MoviesManager provideMoviesManager(MoviesService moviesService, MovieRepository movieRepository) {
        return new MoviesManager(moviesService, movieRepository);
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase() {
        return Room.databaseBuilder(provideApplication().getApplicationContext(),
                AppDatabase.class, "movie_db").build();
    }

    @Provides
    @Singleton
    MovieDao provideTrendingMovieDao() {
        return provideAppDatabase().movieDao();
    }

    @Provides
    @Singleton
    MovieRepository provideMovieRepository(MovieDao movieDao) {
        return new MovieRepository(movieDao);
    }
}
