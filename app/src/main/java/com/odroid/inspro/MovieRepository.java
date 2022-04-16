package com.odroid.inspro;

import java.util.ArrayList;

import javax.inject.Inject;

class MovieRepository {

    private MovieDao movieDao;

    @Inject
    public MovieRepository(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public void saveTotalTrendingPages(int pages) {
        PreferenceUtils.setTotalTrendingPages(pages);
    }

    public void saveTotalNowPlayingPages(int pages) {
        PreferenceUtils.setTotalNowPlayingPages(pages);
    }

    public void saveCurrentTrendingPage(int page) {
        PreferenceUtils.setCurrentTrendingPage(page);
    }

    public void saveCurrentNowPlayingPage(int page) {
        PreferenceUtils.setCurrentNowPlayingPage(page);
    }

    public int getTotalTrendingPages() {
        return PreferenceUtils.getTotalTrendingPages();
    }

    public int getTotalNowPlayingPages() {
        return PreferenceUtils.getTotalNowPlayingPages();
    }

    public int getCurrentTrendingPage() {
        return PreferenceUtils.getCurrentTrendingPage();
    }

    public int getCurrentNowPlayingPage() {
        return PreferenceUtils.getCurrentNowPlayingPage();
    }



    public void insertTrendingMoviesToDB(ArrayList<Movie> trendingMovies) {
        movieDao.insertTrendingMovies(getTrendingMovies(trendingMovies));
    }

    public void insertNowPlayingMoviesToDB(ArrayList<Movie> nowPlayingMovies) {
        movieDao.insertNowPlayingMovies(getNowPlayingMovies(nowPlayingMovies));
    }

    public void updateTrendingMovie(long movieId, boolean isBookmarked) {
        movieDao.updateTrendingMovieBookmark(movieId, isBookmarked);
    }

    public void updateNowPlayingMovie(long movieId, boolean isBookmarked) {
        movieDao.updateNowPlayingMovieBookmark(movieId, isBookmarked);
    }

    private ArrayList<TrendingMovie> getTrendingMovies(ArrayList<Movie> movies) {
        ArrayList<TrendingMovie> trendingMovies = new ArrayList<>();
        for (Movie movie : movies) {
            trendingMovies.add(new TrendingMovie(movie.id, movie.title,
                    movie.movieDescription, movie.releaseDate, movie.posterUrl));
        }
        return trendingMovies;
    }

    private ArrayList<NowPlayingMovie> getNowPlayingMovies(ArrayList<Movie> movies) {
        ArrayList<NowPlayingMovie> nowPlayingMovies = new ArrayList<>();
        for (Movie movie : movies) {
            nowPlayingMovies.add(new NowPlayingMovie(movie.id, movie.title,
                    movie.movieDescription, movie.releaseDate, movie.posterUrl));
        }
        return nowPlayingMovies;
    }
}
