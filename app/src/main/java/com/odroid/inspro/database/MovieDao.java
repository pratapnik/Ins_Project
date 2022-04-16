package com.odroid.inspro.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.odroid.inspro.entity.BaseMovie;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

@Dao
public interface MovieDao {
    @Query("UPDATE all_movies SET bookmarked = :isBookmarked WHERE id = :id")
    void updateMovieBookmark(long id, boolean isBookmarked);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(ArrayList<BaseMovie> moviesList);

    @Query("SELECT * FROM all_movies WHERE trending = 1")
    Observable<List<BaseMovie>> getTrendingMovies();

    @Query("SELECT * FROM all_movies WHERE now_playing = 1")
    Observable<List<BaseMovie>> getNowPlayingMovies();

    @Query("SELECT * FROM all_movies WHERE bookmarked = 1")
    Observable<List<BaseMovie>> getBookmarkedMovies();
}
