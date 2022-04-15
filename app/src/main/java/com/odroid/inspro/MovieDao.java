package com.odroid.inspro;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTrendingMovies(ArrayList<TrendingMovie> trendingMovies);

    @Query("UPDATE trending_movies SET bookmarked = :isBookmarked WHERE id = :id")
    void updateTrendingMovieBookmark(long id, boolean isBookmarked);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertNowPlayingMovies(ArrayList<NowPlayingMovie> nowPlayingMovies);

    @Query("UPDATE now_playing_movies SET bookmarked = :isBookmarked WHERE id = :id")
    void updateNowPlayingMovieBookmark(long id, boolean isBookmarked);

    @Query("SELECT * FROM trending_movies")
    Observable<List<TrendingMovie>> getAllTrendingMovies();

    @Query("SELECT * FROM now_playing_movies")
    Observable<List<NowPlayingMovie>> getAllNowPlayingMovies();
}
