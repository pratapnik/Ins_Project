package com.odroid.inspro.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.odroid.inspro.common.InsApp;
import com.odroid.inspro.common.JsonUtils;
import com.odroid.inspro.databinding.ActivityMainBinding;
import com.odroid.inspro.databinding.ActivityMovieDetailsBinding;
import com.odroid.inspro.entity.Constants;
import com.odroid.inspro.entity.NowPlayingMovie;
import com.odroid.inspro.entity.TrendingMovie;

public class MovieDetailsActivity extends AppCompatActivity {

    private ActivityMovieDetailsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ((InsApp) getApplication()).getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if (intent != null) {
            String movieDetails = intent.getStringExtra("movieDetails");
            String movieType = intent.getStringExtra("movieType");

            if (movieType.equals("trending")) {
                TrendingMovie trendingMovie = JsonUtils.getGson().fromJson(
                        movieDetails,
                        TrendingMovie.class);
                setMovieDetailsOnView(trendingMovie.posterUrl, trendingMovie.title,
                        trendingMovie.releaseDate, trendingMovie.movieDescription);
            } else if (movieType.equals("now_playing")) {
                NowPlayingMovie nowPlayingMovie = JsonUtils.getGson().fromJson(
                        movieDetails,
                        NowPlayingMovie.class);
                setMovieDetailsOnView(nowPlayingMovie.posterUrl, nowPlayingMovie.title,
                        nowPlayingMovie.releaseDate, nowPlayingMovie.movieDescription);
            }
        }
    }

    private void setMovieDetailsOnView(String movieIcon, String movieTitle, String releaseDate, String movieDesc) {
        String posterUrl = Constants.POSTER_BASE_URL + movieIcon;
        Glide.with(this).load(posterUrl).into(binding.ivIcon);
        binding.tvTitle.setText(movieTitle);
        binding.tvRelease.setText(releaseDate);
        binding.tvDetails.setText(movieDesc);
    }

}
