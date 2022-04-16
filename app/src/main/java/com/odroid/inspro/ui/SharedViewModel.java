package com.odroid.inspro.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.odroid.inspro.common.MoviesManager;
import com.odroid.inspro.database.MovieRepository;
import com.odroid.inspro.entity.NowPlayingMovie;
import com.odroid.inspro.entity.TrendingMovie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SharedViewModel extends ViewModel {

    private MoviesManager moviesManager;
    private MovieRepository movieRepository;
    private CompositeDisposable compositeDisposable;

    public MutableLiveData<List<TrendingMovie>> trendingMovieMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<NowPlayingMovie>> nowPlayingMovieMutableLiveData = new MutableLiveData<>();

    @Inject
    public SharedViewModel(MoviesManager moviesManager,
                           MovieRepository movieRepository) {
        this.moviesManager = moviesManager;
        this.movieRepository = movieRepository;
        this.compositeDisposable = new CompositeDisposable();
    }

    void fetchTrendingMovies() {
        moviesManager.fetchTrendingMoviesFromRemote();
    }

    void fetchNowPlayingMovies() {
        moviesManager.fetchNowPlayingMoviesFromRemote();
    }

    public void getTrendingMovies() {
        Observable<List<TrendingMovie>> trendingMoviesListObservable = movieRepository.getTrendingMovies().subscribeOn(Schedulers.io());
        DisposableObserver<List<TrendingMovie>> trendingMoviesObserver = getTrendingMoviesObserver();
        addDisposable(trendingMoviesObserver);
        trendingMoviesListObservable.subscribe(trendingMoviesObserver);
    }


    public void getNowPlayingMovies() {
        Observable<List<NowPlayingMovie>> nowPlayingMoviesListObservable = movieRepository.getNowPlayingMovies().subscribeOn(Schedulers.io());
        DisposableObserver<List<NowPlayingMovie>> nowPlayingMoviesObserver = getNowPlayingMoviesObserver();
        addDisposable(nowPlayingMoviesObserver);
        nowPlayingMoviesListObservable.subscribe(nowPlayingMoviesObserver);
    }


    private void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    private DisposableObserver<List<TrendingMovie>> getTrendingMoviesObserver() {
        return new DisposableObserver<List<TrendingMovie>>() {
            @Override
            public void onNext(@NonNull List<TrendingMovie> trendingMovies) {
                trendingMovieMutableLiveData.postValue(trendingMovies);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    private DisposableObserver<List<NowPlayingMovie>> getNowPlayingMoviesObserver() {
        return new DisposableObserver<List<NowPlayingMovie>>() {
            @Override
            public void onNext(@NonNull List<NowPlayingMovie> nowPlayingMovies) {
                nowPlayingMovieMutableLiveData.postValue(nowPlayingMovies);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    protected void onCleared() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        super.onCleared();
    }
}
