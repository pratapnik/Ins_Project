package com.odroid.inspro.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.odroid.inspro.entity.BookmarkedMovie;
import com.odroid.inspro.entity.NowPlayingMovie;
import com.odroid.inspro.entity.TrendingMovie;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTrendingMovies(ArrayList<TrendingMovie> trendingMovies);

    @Query("UPDATE trending_movies SET bookmarked = :isBookmarked WHERE id = :id")
    Completable updateTrendingMovieBookmark(long id, boolean isBookmarked);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertNowPlayingMovies(ArrayList<NowPlayingMovie> nowPlayingMovies);

    @Query("UPDATE now_playing_movies SET bookmarked = :isBookmarked WHERE id = :id")
    void updateNowPlayingMovieBookmark(long id, boolean isBookmarked);

    @Query("SELECT * FROM trending_movies")
    Observable<List<TrendingMovie>> getAllTrendingMovies();

    @Query("SELECT * FROM now_playing_movies")
    Observable<List<NowPlayingMovie>> getAllNowPlayingMovies();

    @Query("SELECT * FROM now_playing_movies WHERE bookmarked = 1 UNION SELECT * FROM trending_movies WHERE bookmarked = 1")
    Observable<List<BookmarkedMovie>> getBookmarkedMovies();
}
