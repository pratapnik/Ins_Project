package com.odroid.inspro.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TmdbResponse {
   @SerializedName("page")
   public int pageNo;
   @SerializedName("results")
   public ArrayList<Movie> moviesList;
   @SerializedName("total_pages")
   public int totalNumberOfPages;
}
