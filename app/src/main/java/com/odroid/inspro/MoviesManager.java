package com.odroid.inspro;

import java.util.ArrayList;

import javax.inject.Singleton;

import dagger.Provides;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Singleton
public class MoviesManager {

    private MoviesService moviesService;
    private CompositeDisposable compositeDisposable;


    public MoviesManager(MoviesService moviesService) {
        this.moviesService = moviesService;
        this.compositeDisposable = new CompositeDisposable();
    }

    public void fetchTrendingMoviesFromRemote() {
        Observable<TmdbResponse> tmdbResponseObservable = moviesService.getTrendingMovies().subscribeOn(Schedulers.io());
        tmdbResponseObservable.subscribe(getTrendingMoviesObserver());
    }

    public void fetchNowPlayingMoviesFromRemote() {
        Observable<TmdbResponse> tmdbResponseObservable = moviesService.getNowPlayingMovies().subscribeOn(Schedulers.io());
        tmdbResponseObservable.subscribe(getNowPlayingMoviesObserver());
    }

    private void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    private DisposableObserver<TmdbResponse> getTrendingMoviesObserver() {
        return new DisposableObserver<TmdbResponse>() {
            @Override
            public void onNext(@NonNull TmdbResponse tmdbResponse) {
                saveTrendingMovies(tmdbResponse.moviesList);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    private DisposableObserver<TmdbResponse> getNowPlayingMoviesObserver() {
        return new DisposableObserver<TmdbResponse>() {
            @Override
            public void onNext(@NonNull TmdbResponse tmdbResponse) {
                saveNowPlayingMovies(tmdbResponse.moviesList);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    private void saveTrendingMovies(ArrayList<Movie> movies) {

    }

    private void saveNowPlayingMovies(ArrayList<Movie> movies) {

    }

    public void stop() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
