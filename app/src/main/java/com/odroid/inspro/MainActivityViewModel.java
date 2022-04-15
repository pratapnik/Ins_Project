package com.odroid.inspro;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

public class MainActivityViewModel extends ViewModel {

   private MoviesManager moviesManager;

   @Inject
   public MainActivityViewModel(MoviesManager moviesManager) {
      this.moviesManager = moviesManager;
   }

   void fetchTrendingMovies() {
      moviesManager.fetchTrendingMoviesFromRemote();
   }

   void fetchNowPlayingMovies() {
      moviesManager.fetchNowPlayingMoviesFromRemote();
   }
}
