package com.odroid.inspro.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.odroid.inspro.entity.BaseMovie;
import com.odroid.inspro.entity.NowPlayingMovie;
import com.odroid.inspro.entity.TrendingMovie;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

@Dao
public interface MovieDao {
    @Query("UPDATE trending_movie SET bookmarked = :isBookmarked WHERE id = :id")
    void updateTrendingMovieBookmark(long id, boolean isBookmarked);

    @Query("UPDATE now_playing_movie SET bookmarked = :isBookmarked WHERE id = :id")
    void updateNowPlayingMovieBookmark(long id, boolean isBookmarked);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertNowPlayingMovies(ArrayList<NowPlayingMovie> moviesList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTrendingMovies(ArrayList<TrendingMovie> moviesList);

    @Query("SELECT * FROM trending_movie ORDER BY time_stamp ASC")
    Observable<List<BaseMovie>> getTrendingMovies();

    @Query("SELECT * FROM now_playing_movie ORDER BY time_stamp ASC")
    Observable<List<BaseMovie>> getNowPlayingMovies();

    @Query("SELECT * FROM (SELECT * FROM trending_movie WHERE bookmarked = 1 UNION SELECT * FROM now_playing_movie WHERE bookmarked = 1) group by id ORDER BY time_stamp DESC")
    Observable<List<BaseMovie>> getBookmarkedMovies();

    @Query("SELECT * FROM (SELECT * FROM trending_movie WHERE movie_title LIKE '%' || :searchText || '%' UNION SELECT * FROM now_playing_movie WHERE movie_title like '%' || :searchText || '%')  GROUP BY id ORDER BY time_stamp")
    Observable<List<BaseMovie>> searchMovie(String searchText);
}
