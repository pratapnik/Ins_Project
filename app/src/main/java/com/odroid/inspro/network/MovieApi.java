package com.odroid.inspro.network;


import com.odroid.inspro.entity.TmdbResponse;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("trending/movie/day")
    Observable<TmdbResponse> getTrendingMovies(@Query("api_key") String apiKey,
                                             @Query("page") int pageNo);

    @GET("movie/now_playing")
    Observable<TmdbResponse> getNowPlayingMovies(@Query("api_key") String apiKey,
                                                 @Query("page") int pageNo);
}
