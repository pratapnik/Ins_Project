package com.odroid.inspro;

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
}

