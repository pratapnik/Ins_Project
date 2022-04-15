package com.odroid.inspro;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface MovieApi {

    @GET("trending/movie/day")
    Observable<TmdbResponse> getTrendingMovies(@Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Observable<TmdbResponse> getNowPlayingMovies(@Query("api_key") String apiKey);
}
