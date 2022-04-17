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
import androidx.recyclerview.widget.RecyclerView;

import com.odroid.inspro.common.InsApp;
import com.odroid.inspro.common.JsonUtils;
import com.odroid.inspro.databinding.FragmentMoviesBinding;
import com.odroid.inspro.entity.BaseMovie;
import com.odroid.inspro.entity.BookmarkedMovie;

import java.util.List;

import javax.inject.Inject;

public class MoviesFragment extends Fragment implements MoviesListAdapter.MovieClickListener {

    private FragmentMoviesBinding fragmentMoviesBinding;

    private SharedViewModel sharedViewModel;

    private MoviesListAdapter trendingMoviesListAdapter;
    private MoviesListAdapter nowPlayingMoviesListAdapter;
    private Intent movieDetailsIntent;

    private boolean isTrendingMoviesLastPage = false;
    private boolean isTrendingMoviesLoading = false;
    private boolean isNowPlayingMoviesLastPage = false;
    private boolean isNowPlayingMoviesLoading = false;

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
        trendingMoviesListAdapter = new MoviesListAdapter(getContext(), this);
        nowPlayingMoviesListAdapter = new MoviesListAdapter(getContext(), this);

        LinearLayoutManager trendingMoviesLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        fragmentMoviesBinding.rvTrendingMovies.setAdapter(trendingMoviesListAdapter);
        fragmentMoviesBinding.rvTrendingMovies.setLayoutManager(trendingMoviesLayoutManager);

        LinearLayoutManager nowPlayingMoviesLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        fragmentMoviesBinding.rvNowPlayingMovies.setAdapter(nowPlayingMoviesListAdapter);
        fragmentMoviesBinding.rvNowPlayingMovies.setLayoutManager(nowPlayingMoviesLayoutManager);

        fragmentMoviesBinding.rvTrendingMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = trendingMoviesLayoutManager.getChildCount();
                int totalItemCount = trendingMoviesLayoutManager.getItemCount();
                int firstVisibleItemPosition = trendingMoviesLayoutManager.findFirstVisibleItemPosition();
                if (!isTrendingMoviesLoading && !isTrendingMoviesLastPage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 &&
                            totalItemCount >= trendingMoviesListAdapter.getItemCount() && sharedViewModel.isNextTrendingPageAvailable()
                    ) {
                        sharedViewModel.fetchTrendingMovies();
                    }
                }
            }
        });

        fragmentMoviesBinding.rvNowPlayingMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = nowPlayingMoviesLayoutManager.getChildCount();
                int totalItemCount = nowPlayingMoviesLayoutManager.getItemCount();
                int firstVisibleItemPosition = nowPlayingMoviesLayoutManager.findFirstVisibleItemPosition();
                if (!isNowPlayingMoviesLoading && !isNowPlayingMoviesLastPage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 &&
                            totalItemCount >= nowPlayingMoviesListAdapter.getItemCount() && sharedViewModel.isNextNowPlayingPageAvailable()
                    ) {
                        sharedViewModel.fetchNowPlayingMovies();
                    }
                }
            }
        });
        sharedViewModel.getTrendingMovies();
        sharedViewModel.getNowPlayingMovies();

        observeMoviesData();
    }

    private void observeMoviesData() {
        final Observer<List<BaseMovie>> trendingMoviesListObserver = trendingMovieList -> {
            trendingMoviesListAdapter.updateMovieList(trendingMovieList);
        };
        final Observer<List<BaseMovie>> nowPlayingMoviesListObserver = nowPlayingMovieList -> {
            nowPlayingMoviesListAdapter.updateMovieList(nowPlayingMovieList);
        };

        sharedViewModel.trendingMovieMutableLiveData.observe(getViewLifecycleOwner(), trendingMoviesListObserver);
        sharedViewModel.nowPlayingMovieMutableLiveData.observe(getViewLifecycleOwner(), nowPlayingMoviesListObserver);
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

//    private var recyclerViewOnScrollListener: RecyclerView.OnScrollListener? =
//    object : RecyclerView.OnScrollListener() {
//
//        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//            super.onScrolled(recyclerView, dx, dy)

//        }
//    }
}
