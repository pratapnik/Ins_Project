package com.odroid.inspro;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Observable;

@Singleton
class MoviesService {

    private MovieApi movieApi;

    private String apiKey = "49048a3db1a5f4417cf4992305aba62c";

    @Inject
    public MoviesService(MovieApi movieApi) {
        this.movieApi = movieApi;
    }

    public Observable<TmdbResponse> getTrendingMovies() {
        return movieApi.getTrendingMovies(apiKey);
    }

    public Observable<TmdbResponse> getNowPlayingMovies() {
        return movieApi.getNowPlayingMovies(apiKey);
    }
}
