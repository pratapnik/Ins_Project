package com.odroid.inspro.entity;

import androidx.room.Entity;

@Entity(tableName = "now_playing_movie")
public class NowPlayingMovie extends BaseMovie {

   public NowPlayingMovie(long id, String title, String movieDescription, String releaseDate,
                        String posterUrl, float rating, long ratingCount, boolean isBookmarked) {
      super(id, title, movieDescription, releaseDate, posterUrl, rating, ratingCount, isBookmarked);
   }
}
