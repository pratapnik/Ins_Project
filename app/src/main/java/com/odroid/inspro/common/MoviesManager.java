package com.odroid.inspro.common;

import com.odroid.inspro.database.MovieRepository;
import com.odroid.inspro.entity.BaseMovie;
import com.odroid.inspro.entity.Movie;
import com.odroid.inspro.entity.TmdbResponse;
import com.odroid.inspro.network.MoviesService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
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

    public void fetchMoviesFromRemote() {
        Observable.zip(
                moviesService.getTrendingMovies().subscribeOn(Schedulers.io()),
                moviesService.getNowPlayingMovies().subscribeOn(Schedulers.io()),
                (trendingMovies, nowPlayingMovies) -> {
                    movieRepository.saveTotalTrendingPages(trendingMovies.totalNumberOfPages);
                    movieRepository.saveCurrentTrendingPage(trendingMovies.pageNo);
                    movieRepository.saveTotalNowPlayingPages(nowPlayingMovies.totalNumberOfPages);
                    movieRepository.saveCurrentNowPlayingPage(nowPlayingMovies.pageNo);
                    ArrayList<BaseMovie> baseMovies = new ArrayList<>();
                    ArrayList<Long> trendingMoviesIds = new ArrayList<>();
                    ArrayList<Long> nowPlayingMoviesIds = new ArrayList<>();
                    for (Movie movie :
                            trendingMovies.moviesList) {
                        trendingMoviesIds.add(movie.id);
                    }
                    for (Movie movie :
                            nowPlayingMovies.moviesList) {
                        nowPlayingMoviesIds.add(movie.id);
                    }
                    for (Movie movie :
                            trendingMovies.moviesList) {
                        if (nowPlayingMoviesIds.contains(movie.id)) {
                            baseMovies.add(new BaseMovie(movie.id, movie.title, movie.movieDescription, movie.releaseDate,
                                    movie.posterUrl, movie.rating, movie.ratingCount, true, true, false));
                        } else {
                            baseMovies.add(new BaseMovie(movie.id, movie.title, movie.movieDescription, movie.releaseDate,
                                    movie.posterUrl, movie.rating, movie.ratingCount, true, false, false));
                        }
                    }

                    for (Movie movie :
                            nowPlayingMovies.moviesList) {
                        if (!trendingMoviesIds.contains(movie.id)) {
                            baseMovies.add(new BaseMovie(movie.id, movie.title, movie.movieDescription, movie.releaseDate,
                                    movie.posterUrl, movie.rating, movie.ratingCount, false, true, false));
                        }
                    }
                    return baseMovies;
                }
        ).subscribeOn(Schedulers.io()).subscribe(getBaseMoviesObserver());
    }

    private void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    private DisposableObserver<ArrayList<BaseMovie>> getBaseMoviesObserver() {
        return new DisposableObserver<ArrayList<BaseMovie>>() {
            @Override
            public void onNext(@NonNull ArrayList<BaseMovie> baseMovies) {
                movieRepository.insertMoviesToDB(baseMovies);
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
