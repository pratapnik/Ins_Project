package com.odroid.inspro.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BookmarkedMovie {

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
   public boolean isBookmarked = false;


   public BookmarkedMovie(long id, String title, String movieDescription,
                          String releaseDate, String posterUrl, float rating, long ratingCount) {
      this.id = id;
      this.title = title;
      this.movieDescription = movieDescription;
      this.releaseDate = releaseDate;
      this.posterUrl = posterUrl;
      this.rating = rating;
      this.ratingCount = ratingCount;
   }
}
