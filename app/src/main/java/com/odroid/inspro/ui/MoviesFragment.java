package com.odroid.inspro.ui;

import static com.odroid.inspro.common.InsApp.getApplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.odroid.inspro.common.InsApp;
import com.odroid.inspro.common.JsonUtils;
import com.odroid.inspro.databinding.FragmentMoviesBinding;
import com.odroid.inspro.entity.BookmarkedMovie;
import com.odroid.inspro.entity.MovieListType;
import com.odroid.inspro.entity.NowPlayingMovie;
import com.odroid.inspro.entity.TrendingMovie;

import java.util.List;

import javax.inject.Inject;

public class MoviesFragment extends Fragment implements MoviesListAdapter.MovieClickListener {

    private FragmentMoviesBinding fragmentMoviesBinding;

    private SharedViewModel sharedViewModel;

    private MoviesListAdapter trendingMoviesListAdapter;
    private MoviesListAdapter nowPlayingMoviesListAdapter;
    private Intent movieDetailsIntent;

    @Inject
    MovieViewModelFactory movieViewModelFactory;

    public MoviesFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentMoviesBinding = FragmentMoviesBinding.inflate(inflater, container, false);
        return fragmentMoviesBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((InsApp) getApplication()).getAppComponent().inject(this);
        sharedViewModel = new ViewModelProvider(this, movieViewModelFactory).get(SharedViewModel.class);
        movieDetailsIntent = new Intent(getActivity(), MovieDetailsActivity.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        trendingMoviesListAdapter = new MoviesListAdapter(getContext(), MovieListType.TRENDING, this);
        nowPlayingMoviesListAdapter = new MoviesListAdapter(getContext(), MovieListType.NOW_PLAYING, this);

        fragmentMoviesBinding.rvTrendingMovies.setAdapter(trendingMoviesListAdapter);
        fragmentMoviesBinding.rvTrendingMovies.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        fragmentMoviesBinding.rvNowPlayingMovies.setAdapter(nowPlayingMoviesListAdapter);
        fragmentMoviesBinding.rvNowPlayingMovies.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        sharedViewModel.getTrendingMovies();
        sharedViewModel.getNowPlayingMovies();

        observeMoviesData();
    }

    private void observeMoviesData() {
        final Observer<List<TrendingMovie>> trendingMoviesListObserver = trendingMovieList -> {
            trendingMoviesListAdapter.updateMovieList(trendingMovieList);
        };
        final Observer<List<NowPlayingMovie>> nowPlayingMoviesListObserver = nowPlayingMovieList -> {
            nowPlayingMoviesListAdapter.updateMovieList(nowPlayingMovieList);
        };

        sharedViewModel.trendingMovieMutableLiveData.observe(getViewLifecycleOwner(), trendingMoviesListObserver);
        sharedViewModel.nowPlayingMovieMutableLiveData.observe(getViewLifecycleOwner(), nowPlayingMoviesListObserver);
    }

    @Override
    public void onTrendingMovieClicked(TrendingMovie trendingMovie) {
        String movieDetails = JsonUtils.getGson().toJson(trendingMovie);
        launchMovieDetailsActivity(movieDetails, "trending");
    }

    @Override
    public void onNowPlayingMovieClicked(NowPlayingMovie nowPlayingMovie) {
        String movieDetails = JsonUtils.getGson().toJson(nowPlayingMovie);
        launchMovieDetailsActivity(movieDetails, "now_playing");
    }

    @Override
    public void onBookmarkedMovieClicked(BookmarkedMovie bookmarkedMovie) {

    }

    @Override
    public void bookmarkMovie(long movieId, boolean bookmark, MovieListType movieListType) {
        sharedViewModel.bookmarkMovie(movieId, bookmark, movieListType);
    }

    private void launchMovieDetailsActivity(String movieDetails, String movieType) {
        movieDetailsIntent.putExtra("movieDetails", movieDetails);
        movieDetailsIntent.putExtra("movieType", movieType);
        startActivity(movieDetailsIntent);
    }
}
