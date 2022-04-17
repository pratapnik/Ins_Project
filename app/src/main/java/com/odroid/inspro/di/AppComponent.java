package com.odroid.inspro.di;

import com.odroid.inspro.common.MoviesManager;
import com.odroid.inspro.ui.view.BookmarkedMoviesFragment;
import com.odroid.inspro.ui.view.MainActivity;
import com.odroid.inspro.ui.view.MovieDetailsActivity;
import com.odroid.inspro.ui.view.MoviesFragment;
import com.odroid.inspro.ui.view.SearchMoviesFragment;

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
