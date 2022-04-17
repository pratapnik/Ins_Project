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
import com.odroid.inspro.entity.BaseMovie;
import com.odroid.inspro.entity.BookmarkedMovie;
import com.odroid.inspro.entity.MovieViewHolderType;

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
        moviesListAdapter = new MoviesListAdapter(getContext(), this, MovieViewHolderType.BOOKMARKED);

        binding.rvBookmarkedMovies.setAdapter(moviesListAdapter);
        binding.rvBookmarkedMovies.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));

        sharedViewModel.getBookmarkedMovies();
        observeMoviesData();
    }

    private void observeMoviesData() {
        final Observer<List<BaseMovie>> bookmarkedMoviesListObserver = bookmarkedMoviesList -> {
            moviesListAdapter.updateMovieList(bookmarkedMoviesList);
        };

        sharedViewModel.bookmarkedMovieMutableLiveData.observe(getViewLifecycleOwner(), bookmarkedMoviesListObserver);
    }

    private void launchMovieDetailsActivity(String movieDetails) {
        movieDetailsIntent.putExtra("movieDetails", movieDetails);
        startActivity(movieDetailsIntent);
    }

    @Override
    public void onMovieClicked(BaseMovie baseMovie) {
        String movieDetails = JsonUtils.getGson().toJson(baseMovie);
        launchMovieDetailsActivity(movieDetails);
    }

    @Override
    public void bookmarkMovie(long movieId, boolean bookmark) {
        sharedViewModel.bookmarkMovie(movieId, bookmark);
    }
}
