package com.odroid.inspro.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.odroid.inspro.common.InsApp;
import com.odroid.inspro.common.JsonUtils;
import com.odroid.inspro.databinding.ActivityMovieDetailsBinding;
import com.odroid.inspro.entity.BaseMovie;
import com.odroid.inspro.entity.Constants;

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

            BaseMovie baseMovie = JsonUtils.getGson().fromJson(
                    movieDetails,
                    BaseMovie.class);
            setMovieDetailsOnView(baseMovie.posterUrl, baseMovie.title,
                    baseMovie.releaseDate, baseMovie.movieDescription);
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
