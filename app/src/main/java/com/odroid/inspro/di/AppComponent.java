package com.odroid.inspro.di;

import com.odroid.inspro.common.MoviesManager;
import com.odroid.inspro.ui.BookmarkedMoviesFragment;
import com.odroid.inspro.ui.MainActivity;
import com.odroid.inspro.ui.MovieDetailsActivity;
import com.odroid.inspro.ui.MoviesFragment;
import com.odroid.inspro.ui.SearchMoviesFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, ViewModelModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);
    void inject(MoviesFragment moviesFragment);
    void inject(BookmarkedMoviesFragment bookmarkedMoviesFragment);
    void inject(SearchMoviesFragment searchMoviesFragment);
    void inject(MovieDetailsActivity movieDetailsActivity);
    MoviesManager provideMovieManager();
}
