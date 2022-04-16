package com.odroid.inspro.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "all_movies")
public class BaseMovie {

   @PrimaryKey
   @ColumnInfo(name = "id")
   public long id;
   @ColumnInfo(name = "movie_title")
   public String title;
   @ColumnInfo(name = "movie_description")
   public String movieDescription;
   @ColumnInfo(name = "release_date")
   public String releaseDate;
   @ColumnInfo(name = "image")
   public String posterUrl;
   @ColumnInfo(name = "rating")
   public float rating;
   @ColumnInfo(name = "rating_count")
   public long ratingCount;
   @ColumnInfo(name = "bookmarked")
   public boolean isBookmarked;
   @ColumnInfo(name = "trending")
   public boolean isTrending;
   @ColumnInfo(name = "now_playing")
   public boolean isNowPlaying;

   public BaseMovie(long id, String title, String movieDescription, String releaseDate,
                        String posterUrl, float rating, long ratingCount, boolean isTrending, boolean isNowPlaying,
                    boolean isBookmarked) {
      this.id = id;
      this.title = title;
      this.movieDescription = movieDescription;
      this.releaseDate = releaseDate;
      this.posterUrl = posterUrl;
      this.rating = rating;
      this.ratingCount = ratingCount;
      this.isTrending = isTrending;
      this.isNowPlaying = isNowPlaying;
      this.isBookmarked = isBookmarked;
   }
}