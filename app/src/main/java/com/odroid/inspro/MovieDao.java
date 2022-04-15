package com.odroid.inspro;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;

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
}
