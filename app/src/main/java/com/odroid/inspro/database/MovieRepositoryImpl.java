package com.odroid.inspro.database;

import com.odroid.inspro.entity.BaseMovie;
import com.odroid.inspro.entity.NowPlayingMovie;
import com.odroid.inspro.entity.TrendingMovie;
import com.odroid.inspro.util.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class MovieRepositoryImpl implements MovieRepository {

    private MovieDao movieDao;

    @Inject
    public MovieRepositoryImpl(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public void saveTotalTrendingPages(int pages) {
        PreferenceUtils.setTotalTrendingPages(pages);
    }

    @Override
    public void saveTotalNowPlayingPages(int pages) {
        PreferenceUtils.setTotalNowPlayingPages(pages);
    }

    @Override
    public void saveCurrentTrendingPage(int page) {
        PreferenceUtils.setCurrentTrendingPage(page);
    }

    @Override
    public void saveCurrentNowPlayingPage(int page) {
        PreferenceUtils.setCurrentNowPlayingPage(page);
    }

    @Override
    public int getTotalTrendingPages() {
        return PreferenceUtils.getTotalTrendingPages();
    }

    @Override
    public int getTotalNowPlayingPages() {
        return PreferenceUtils.getTotalNowPlayingPages();
    }

    @Override
    public int getCurrentTrendingPage() {
        return PreferenceUtils.getCurrentTrendingPage();
    }

    @Override
    public int getCurrentNowPlayingPage() {
        return PreferenceUtils.getCurrentNowPlayingPage();
    }

    @Override
    public void updateTrendingMovie(long movieId, boolean isBookmarked) {
        movieDao.updateTrendingMovieBookmark(movieId, isBookmarked);
    }

    @Override
    public void updateNowPlayingMovie(long movieId, boolean isBookmarked) {
        movieDao.updateNowPlayingMovieBookmark(movieId, isBookmarked);
    }

    @Override
    public void insertTrendingMoviesToDB(ArrayList<TrendingMovie> movies) {
        movieDao.insertTrendingMovies(movies);
    }

    @Override
    public void insertNowPlayingMoviesToDB(ArrayList<NowPlayingMovie> movies) {
        movieDao.insertNowPlayingMovies(movies);
    }

    @Override
    public Observable<List<BaseMovie>> getTrendingMoviesFromDB() {
        return movieDao.getTrendingMovies();
    }

    @Override
    public Observable<List<BaseMovie>> getNowPlayingMoviesFromDB() {
        return movieDao.getNowPlayingMovies();
    }

    @Override
    public Observable<List<BaseMovie>> getBookmarkedMoviesFromDB() {
        return movieDao.getBookmarkedMovies();
    }

    @Override
    public Observable<List<BaseMovie>> searchMoviesFromDB(String searchText) {
        return movieDao.searchMovie(searchText);
    }
}
