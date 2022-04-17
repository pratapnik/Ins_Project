package com.odroid.inspro.ui.view;

import static com.odroid.inspro.common.InsApp.getApplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.odroid.inspro.databinding.FragmentSearchMovieBinding;
import com.odroid.inspro.entity.BaseMovie;
import com.odroid.inspro.entity.MovieViewHolderType;
import com.odroid.inspro.ui.view_model.MovieViewModelFactory;
import com.odroid.inspro.ui.view_model.SharedViewModel;
import com.odroid.inspro.ui.adapter.MoviesListAdapter;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class SearchMoviesFragment extends Fragment implements MoviesListAdapter.MovieClickListener {

    private FragmentSearchMovieBinding binding;

    private SharedViewModel sharedViewModel;

    private MoviesListAdapter moviesListAdapter;
    private Intent movieDetailsIntent;

    @Inject
    MovieViewModelFactory movieViewModelFactory;


    public SearchMoviesFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchMovieBinding.inflate(inflater, container, false);
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

        binding.rvSearchedMovies.setAdapter(moviesListAdapter);
        binding.rvSearchedMovies.setLayoutManager(new GridLayoutManager(getContext(), 2,
                LinearLayoutManager.VERTICAL, false));

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() <= 2) {
                    binding.rvSearchedMovies.setVisibility(View.GONE);
                    moviesListAdapter.updateMovieList(Collections.EMPTY_LIST);
                } else
                    sharedViewModel.searchMovie(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        observeMoviesData();
    }

    private void observeMoviesData() {
        final Observer<List<BaseMovie>> searchedMoviesObserver = searchedMoviesList -> {
            if (searchedMoviesList.isEmpty()) {
                binding.rvSearchedMovies.setVisibility(View.GONE);
            } else {
                moviesListAdapter.updateMovieList(searchedMoviesList);
                binding.rvSearchedMovies.setVisibility(View.VISIBLE);
            }
        };

        sharedViewModel.searchMoviesLiveData.observe(getViewLifecycleOwner(), searchedMoviesObserver);
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