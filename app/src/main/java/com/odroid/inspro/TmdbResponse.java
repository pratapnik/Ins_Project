package com.odroid.inspro;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

class TmdbResponse {
   @SerializedName("page")
   public int pageNo;
   @SerializedName("results")
   public ArrayList<Movie> moviesList;
   @SerializedName("total_pages")
   public long totalNumberOfPages;
}
