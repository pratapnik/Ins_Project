package com.odroid.inspro;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Singleton
public class MoviesManager {

    private MoviesService moviesService;
    private CompositeDisposable compositeDisposable;
    private MovieRepository movieRepository;


    @Inject
    public MoviesManager(MoviesService moviesService, MovieRepository movieRepository) {
        this.moviesService = moviesService;
        this.compositeDisposable = new CompositeDisposable();
        this.movieRepository = movieRepository;
    }

    public void fetchTrendingMoviesFromRemote() {
        Observable<TmdbResponse> tmdbResponseObservable = moviesService.getTrendingMovies().subscribeOn(Schedulers.io());
        DisposableObserver<TmdbResponse> trendingMoviesObserver = getTrendingMoviesObserver();
        addDisposable(trendingMoviesObserver);
        tmdbResponseObservable.subscribe(trendingMoviesObserver);
    }

    public void fetchNowPlayingMoviesFromRemote() {
        Observable<TmdbResponse> tmdbResponseObservable = moviesService.getNowPlayingMovies().subscribeOn(Schedulers.io());
        DisposableObserver<TmdbResponse> nowPlayingMoviesObserver = getNowPlayingMoviesObserver();
        addDisposable(nowPlayingMoviesObserver);
        tmdbResponseObservable.subscribe(nowPlayingMoviesObserver);
    }

    private void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    private DisposableObserver<TmdbResponse> getTrendingMoviesObserver() {
        return new DisposableObserver<TmdbResponse>() {
            @Override
            public void onNext(@NonNull TmdbResponse tmdbResponse) {
                saveTrendingMovies(tmdbResponse);
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
                saveNowPlayingMovies(tmdbResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    private void saveTrendingMovies(TmdbResponse tmdbResponse) {
        movieRepository.insertTrendingMoviesToDB(tmdbResponse.moviesList);
        movieRepository.saveTotalTrendingPages(tmdbResponse.totalNumberOfPages);
        movieRepository.saveCurrentTrendingPage(tmdbResponse.pageNo);
    }

    private void saveNowPlayingMovies(TmdbResponse tmdbResponse) {
        movieRepository.insertNowPlayingMoviesToDB(tmdbResponse.moviesList);
        movieRepository.saveTotalNowPlayingPages(tmdbResponse.totalNumberOfPages);
        movieRepository.saveCurrentNowPlayingPage(tmdbResponse.pageNo);
    }

    public void stop() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
