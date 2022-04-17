package com.odroid.inspro.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.odroid.inspro.common.MoviesManager;
import com.odroid.inspro.database.MovieRepository;
import com.odroid.inspro.entity.BaseMovie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SharedViewModel extends ViewModel {

    private MoviesManager moviesManager;
    private MovieRepository movieRepository;
    private CompositeDisposable compositeDisposable;

    public MutableLiveData<List<BaseMovie>> trendingMovieMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<BaseMovie>> nowPlayingMovieMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<BaseMovie>> bookmarkedMovieMutableLiveData = new MutableLiveData<>();

    @Inject
    public SharedViewModel(MoviesManager moviesManager,
                           MovieRepository movieRepository) {
        this.moviesManager = moviesManager;
        this.movieRepository = movieRepository;
        this.compositeDisposable = new CompositeDisposable();
    }

    void fetchMovies() {
        moviesManager.fetchTrendingMovies(1);
        moviesManager.fetchNowPlayingMovies(1);
    }

    public void getTrendingMovies() {
        Observable<List<BaseMovie>> trendingMoviesListObservable = movieRepository.getTrendingMoviesFromDB().subscribeOn(Schedulers.io());
        DisposableObserver<List<BaseMovie>> trendingMoviesObserver = getTrendingMoviesObserver();
        addDisposable(trendingMoviesObserver);
        trendingMoviesListObservable.subscribe(trendingMoviesObserver);
    }


    public void getNowPlayingMovies() {
        Observable<List<BaseMovie>> nowPlayingMoviesListObservable = movieRepository.getNowPlayingMoviesFromDB().subscribeOn(Schedulers.io());
        DisposableObserver<List<BaseMovie>> nowPlayingMoviesObserver = getNowPlayingMoviesObserver();
        addDisposable(nowPlayingMoviesObserver);
        nowPlayingMoviesListObservable.subscribe(nowPlayingMoviesObserver);
    }

    public void getBookmarkedMovies() {
        Observable<List<BaseMovie>> bookmarkedMoviesObservable = movieRepository.getBookmarkedMoviesFromDB().subscribeOn(Schedulers.io());
        DisposableObserver<List<BaseMovie>> bookmarkedMoviesObserver = getBookmarkedMoviesObserver();
        addDisposable(bookmarkedMoviesObserver);
        bookmarkedMoviesObservable.subscribe(bookmarkedMoviesObserver);
    }

    public void bookmarkMovie(long movieId, boolean bookmark) {
        Observable.just(1).subscribeOn(Schedulers.io()).subscribe(new DisposableObserver<Integer>() {
            @Override
            public void onNext(@NonNull Integer integer) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                movieRepository.updateTrendingMovie(movieId, bookmark);
                movieRepository.updateNowPlayingMovie(movieId, bookmark);
            }
        });
    }

    private void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public boolean isNextTrendingPageAvailable() {
        int currentPage = movieRepository.getCurrentTrendingPage();
        long totalPages = movieRepository.getTotalTrendingPages();
        return currentPage < totalPages;
    }

    public boolean isNextNowPlayingPageAvailable() {
        int currentPage = movieRepository.getCurrentNowPlayingPage();
        long totalPages = movieRepository.getTotalNowPlayingPages();
        return currentPage < totalPages;
    }

    public void fetchTrendingMovies() {
        int currentTrendingPage = movieRepository.getCurrentTrendingPage();
        int nextPage = currentTrendingPage + 1;
        moviesManager.fetchTrendingMovies(nextPage);
    }

    public void fetchNowPlayingMovies() {
        int currentNowPlayingPage = movieRepository.getCurrentNowPlayingPage();
        int nextPage = currentNowPlayingPage + 1;
        moviesManager.fetchNowPlayingMovies(nextPage);
    }

    private DisposableObserver<List<BaseMovie>> getTrendingMoviesObserver() {
        return new DisposableObserver<List<BaseMovie>>() {
            @Override
            public void onNext(@NonNull List<BaseMovie> trendingMovies) {
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

    private DisposableObserver<List<BaseMovie>> getNowPlayingMoviesObserver() {
        return new DisposableObserver<List<BaseMovie>>() {
            @Override
            public void onNext(@NonNull List<BaseMovie> nowPlayingMovies) {
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

    private DisposableObserver<List<BaseMovie>> getBookmarkedMoviesObserver() {
        return new DisposableObserver<List<BaseMovie>>() {
            @Override
            public void onNext(@NonNull List<BaseMovie> bookmarkedMovies) {
                bookmarkedMovieMutableLiveData.postValue(bookmarkedMovies);
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
