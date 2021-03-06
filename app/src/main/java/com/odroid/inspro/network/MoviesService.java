package com.odroid.inspro.network;

import com.odroid.inspro.entity.TmdbResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Observable;

@Singleton
public class MoviesService {

    private MovieApi movieApi;

    private String apiKey = "49048a3db1a5f4417cf4992305aba62c";

    @Inject
    public MoviesService(MovieApi movieApi) {
        this.movieApi = movieApi;
    }

    public Observable<TmdbResponse> getTrendingMovies(int pageNo) {
        return movieApi.getTrendingMovies(apiKey, pageNo);
    }

    public Observable<TmdbResponse> getNowPlayingMovies(int pageNo) {
        return movieApi.getNowPlayingMovies(apiKey, pageNo);
    }
}
