package com.odroid.inspro;

import static com.odroid.inspro.InsApp.getApplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.odroid.inspro.databinding.FragmentMoviesBinding;

import java.util.List;

import javax.inject.Inject;

public class MoviesFragment extends Fragment {

    private FragmentMoviesBinding fragmentMoviesBinding;

    private SharedViewModel sharedViewModel;

    private TrendingMoviesListAdapter trendingMoviesListAdapter;
    private NowPlayingMoviesListAdapter nowPlayingMoviesListAdapter;

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
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        trendingMoviesListAdapter = new TrendingMoviesListAdapter(getContext());
        nowPlayingMoviesListAdapter = new NowPlayingMoviesListAdapter(getContext());

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
            trendingMoviesListAdapter.updateTrendingMovieList(trendingMovieList);
        };
        final Observer<List<NowPlayingMovie>> nowPlayingMoviesListObserver = nowPlayingMovieList -> {
            nowPlayingMoviesListAdapter.updateNowPlayingMovieList(nowPlayingMovieList);
        };

        sharedViewModel.trendingMovieMutableLiveData.observe(getViewLifecycleOwner(), trendingMoviesListObserver);
        sharedViewModel.nowPlayingMovieMutableLiveData.observe(getViewLifecycleOwner(), nowPlayingMoviesListObserver);
    }
}
