package com.odroid.inspro.database;

import com.odroid.inspro.entity.BaseMovie;
import com.odroid.inspro.entity.NowPlayingMovie;
import com.odroid.inspro.entity.TrendingMovie;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface MovieRepository {
    void saveTotalTrendingPages(int pages);

    void saveTotalNowPlayingPages(int pages);

    void saveCurrentTrendingPage(int page);

    void saveCurrentNowPlayingPage(int page);

    int getTotalTrendingPages();

    int getTotalNowPlayingPages();

    int getCurrentTrendingPage();

    int getCurrentNowPlayingPage();

    void updateTrendingMovie(long movieId, boolean isBookmarked);

    void updateNowPlayingMovie(long movieId, boolean isBookmarked);

    void insertTrendingMoviesToDB(ArrayList<TrendingMovie> movies);

    void insertNowPlayingMoviesToDB(ArrayList<NowPlayingMovie> movies);

    Observable<List<BaseMovie>> getTrendingMoviesFromDB();

    Observable<List<BaseMovie>> getNowPlayingMoviesFromDB();

    Observable<List<BaseMovie>> getBookmarkedMoviesFromDB();

    Observable<List<BaseMovie>> searchMoviesFromDB(String searchText) ;
}
