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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.odroid.inspro.common.InsApp;
import com.odroid.inspro.common.JsonUtils;
import com.odroid.inspro.databinding.FragmentBookmarkedMoviesBinding;
import com.odroid.inspro.entity.BookmarkedMovie;
import com.odroid.inspro.entity.MovieListType;
import com.odroid.inspro.entity.NowPlayingMovie;
import com.odroid.inspro.entity.TrendingMovie;

import java.util.List;

import javax.inject.Inject;

public class BookmarkedMoviesFragment extends Fragment implements MoviesListAdapter.MovieClickListener {

    private FragmentBookmarkedMoviesBinding binding;

    private SharedViewModel sharedViewModel;

    private MoviesListAdapter moviesListAdapter;
    private Intent movieDetailsIntent;

    @Inject
    MovieViewModelFactory movieViewModelFactory;


    public BookmarkedMoviesFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBookmarkedMoviesBinding.inflate(inflater, container, false);
        return binding.getRoot();
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
        moviesListAdapter = new MoviesListAdapter(getContext(), MovieListType.BOOKMARKED, this);

        binding.rvBookmarkedMovies.setAdapter(moviesListAdapter);
        binding.rvBookmarkedMovies.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));

        sharedViewModel.getBookmarkedMovies();
        observeMoviesData();
    }

    private void observeMoviesData() {
        final Observer<List<BookmarkedMovie>> bookmarkedMoviesListObserver = bookmarkedMoviesList -> {
            moviesListAdapter.updateMovieList(bookmarkedMoviesList);
        };

        sharedViewModel.bookmarkedMovieMutableLiveData.observe(getViewLifecycleOwner(), bookmarkedMoviesListObserver);
    }

    @Override
    public void onTrendingMovieClicked(TrendingMovie trendingMovie) {

    }

    @Override
    public void onNowPlayingMovieClicked(NowPlayingMovie nowPlayingMovie) {

    }

    @Override
    public void onBookmarkedMovieClicked(BookmarkedMovie bookmarkedMovie) {
        String movieDetails = JsonUtils.getGson().toJson(bookmarkedMovie);
        launchMovieDetailsActivity(movieDetails, "bookmarked_movie");
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
