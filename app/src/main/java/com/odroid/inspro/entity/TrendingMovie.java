package com.odroid.inspro.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "trending_movie")
public class TrendingMovie extends BaseMovie {

    public TrendingMovie(long id, String title, String movieDescription, String releaseDate,
                         String posterUrl, float rating, long ratingCount, boolean isBookmarked) {
        super(id, title, movieDescription, releaseDate, posterUrl, rating, ratingCount, isBookmarked);
    }
}
