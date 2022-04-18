package com.odroid.inspro.common;

import com.odroid.inspro.database.MovieRepositoryImpl;
import com.odroid.inspro.entity.Movie;
import com.odroid.inspro.entity.NowPlayingMovie;
import com.odroid.inspro.entity.TmdbResponse;
import com.odroid.inspro.entity.TrendingMovie;
import com.odroid.inspro.network.MoviesService;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Singleton
public class MoviesManager {

    private MoviesService moviesService;
    private CompositeDisposable compositeDisposable;
    private MovieRepositoryImpl movieRepository;

    @Inject
    public MoviesManager(MoviesService moviesService, MovieRepositoryImpl movieRepository) {
        this.moviesService = moviesService;
        this.compositeDisposable = new CompositeDisposable();
        this.movieRepository = movieRepository;
    }

    public void fetchTrendingMovies(int pageNo) {
        DisposableObserver<TmdbResponse> trendingMoviesObserver = getTrendingMoviesObserver();
        addDisposable(trendingMoviesObserver);
        moviesService.getTrendingMovies(pageNo)
                .subscribeOn(Schedulers.io())
                .subscribe(trendingMoviesObserver);
    }

    public void fetchNowPlayingMovies(int pageNo) {
        DisposableObserver<TmdbResponse> nowPlayingMoviesObserver = getNowPlayingMoviesObserver();
        addDisposable(nowPlayingMoviesObserver);
        moviesService.getNowPlayingMovies(pageNo)
                .subscribeOn(Schedulers.io())
                .subscribe(nowPlayingMoviesObserver);
    }

    private void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    private DisposableObserver<TmdbResponse> getTrendingMoviesObserver() {
        return new DisposableObserver<TmdbResponse>() {
            @Override
            public void onNext(@NonNull TmdbResponse tmdbResponse) {
                movieRepository.saveTotalTrendingPages(tmdbResponse.totalNumberOfPages);
                movieRepository.saveCurrentTrendingPage(tmdbResponse.pageNo);
                ArrayList<TrendingMovie> trendingMovies = new ArrayList<>();
                for (Movie movie :
                        tmdbResponse.moviesList) {
                    trendingMovies.add(new TrendingMovie(movie.id, movie.title, movie.movieDescription, movie.releaseDate,
                            movie.posterUrl, movie.rating, movie.ratingCount, false));
                }
                movieRepository.insertTrendingMoviesToDB(trendingMovies);
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
                movieRepository.saveTotalNowPlayingPages(tmdbResponse.totalNumberOfPages);
                movieRepository.saveCurrentNowPlayingPage(tmdbResponse.pageNo);
                ArrayList<NowPlayingMovie> nowPlayingMovies = new ArrayList<>();
                for (Movie movie :
                        tmdbResponse.moviesList) {
                    nowPlayingMovies.add(new NowPlayingMovie(movie.id, movie.title, movie.movieDescription, movie.releaseDate,
                            movie.posterUrl, movie.rating, movie.ratingCount, false));
                }
                movieRepository.insertNowPlayingMoviesToDB(nowPlayingMovies);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    public void stop() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
