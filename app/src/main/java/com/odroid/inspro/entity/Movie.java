package com.odroid.inspro.entity;

import com.google.gson.annotations.SerializedName;

public class Movie {

   @SerializedName("id")
   public long id;
   @SerializedName("title")
   public String title;
   @SerializedName("overview")
   public String movieDescription;
   @SerializedName("release_date")
   public String releaseDate;
   @SerializedName("poster_path")
   public String posterUrl;
   @SerializedName("vote_average")
   public float rating;
   @SerializedName("vote_count")
   public long ratingCount;
}

