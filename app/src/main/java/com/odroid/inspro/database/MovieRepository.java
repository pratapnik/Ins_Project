package com.odroid.inspro.database;

import com.odroid.inspro.entity.BaseMovie;
import com.odroid.inspro.entity.NowPlayingMovie;
import com.odroid.inspro.entity.TrendingMovie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class MovieRepository {

    private MovieDao movieDao;

    @Inject
    public MovieRepository(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public void saveTotalTrendingPages(int pages) {
        PreferenceUtils.setTotalTrendingPages(pages);
    }

    public void saveTotalNowPlayingPages(int pages) {
        PreferenceUtils.setTotalNowPlayingPages(pages);
    }

    public void saveCurrentTrendingPage(int page) {
        PreferenceUtils.setCurrentTrendingPage(page);
    }

    public void saveCurrentNowPlayingPage(int page) {
        PreferenceUtils.setCurrentNowPlayingPage(page);
    }

    public int getTotalTrendingPages() {
        return PreferenceUtils.getTotalTrendingPages();
    }

    public int getTotalNowPlayingPages() {
        return PreferenceUtils.getTotalNowPlayingPages();
    }

    public int getCurrentTrendingPage() {
        return PreferenceUtils.getCurrentTrendingPage();
    }

    public int getCurrentNowPlayingPage() {
        return PreferenceUtils.getCurrentNowPlayingPage();
    }


    public void updateTrendingMovie(long movieId, boolean isBookmarked) {
        movieDao.updateTrendingMovieBookmark(movieId, isBookmarked);
    }

    public void updateNowPlayingMovie(long movieId, boolean isBookmarked) {
        movieDao.updateNowPlayingMovieBookmark(movieId, isBookmarked);
    }

    public void insertTrendingMoviesToDB(ArrayList<TrendingMovie> movies) {
        movieDao.insertTrendingMovies(movies);
    }

    public void insertNowPlayingMoviesToDB(ArrayList<NowPlayingMovie> movies) {
        movieDao.insertNowPlayingMovies(movies);
    }


    public Observable<List<BaseMovie>> getTrendingMoviesFromDB() {
        return movieDao.getTrendingMovies();
    }

    public Observable<List<BaseMovie>> getNowPlayingMoviesFromDB() {
        return movieDao.getNowPlayingMovies();
    }

    public Observable<List<BaseMovie>> getBookmarkedMoviesFromDB() {
        return movieDao.getBookmarkedMovies();
    }
}
