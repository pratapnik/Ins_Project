package com.odroid.inspro.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.odroid.inspro.R;
import com.odroid.inspro.common.InsApp;
import com.odroid.inspro.common.JsonUtils;
import com.odroid.inspro.databinding.ActivityMovieDetailsBinding;
import com.odroid.inspro.entity.BaseMovie;
import com.odroid.inspro.entity.Constants;
import com.odroid.inspro.ui.view_model.MovieViewModelFactory;
import com.odroid.inspro.ui.view_model.SharedViewModel;

import javax.inject.Inject;

public class MovieDetailsActivity extends AppCompatActivity {

    private ActivityMovieDetailsBinding binding;
    private SharedViewModel sharedViewModel;
    BaseMovie baseMovie;

    @Inject
    MovieViewModelFactory movieViewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ((InsApp) getApplication()).getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedViewModel = new ViewModelProvider(this, movieViewModelFactory)
                .get(SharedViewModel.class);

        Intent intent = getIntent();
        binding.ivBookmark.setVisibility(View.GONE);
        if (intent != null) {
            String movieDetails = intent.getStringExtra("movieDetails");

            baseMovie = JsonUtils.getGson().fromJson(
                    movieDetails,
                    BaseMovie.class);
            setMovieDetailsOnView(baseMovie);
        }

        binding.ivBookmark.setOnClickListener(view -> {
            if (baseMovie != null) {
                sharedViewModel.bookmarkMovie(baseMovie.id, !baseMovie.isBookmarked);
                showBookmarkIcon(!baseMovie.isBookmarked);
            }
        });

        binding.ivShare.setOnClickListener(view -> {
            shareMovie();
        });
    }

    private void shareMovie() {
        Intent intent1 = new Intent(Intent.ACTION_SEND);
        String shareBody = Constants.SHARE_MOVIE_URL;
        intent1.setType("text/plain");
        intent1.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(intent1, "Share Using"));
    }

    private void setMovieDetailsOnView(BaseMovie baseMovie) {
        String posterUrl = Constants.POSTER_BASE_URL + baseMovie.posterUrl;
        Glide.with(this).load(posterUrl).into(binding.ivIcon);
        binding.tvTitle.setText(baseMovie.title);
        String releaseDateText = "Released on: " + baseMovie.releaseDate;
        binding.tvRelease.setText(releaseDateText);
        binding.tvDetails.setText(baseMovie.movieDescription);
        binding.tvMovieRating.setText(baseMovie.rating + "/10");
        binding.tvRatedUsers.setText("(" + baseMovie.ratingCount + ")");
        showBookmarkIcon(baseMovie.isBookmarked);
    }


    private void showBookmarkIcon(boolean isBookmarked) {
        binding.ivBookmark.setVisibility(View.VISIBLE);
        if (isBookmarked) {
            binding.ivBookmark.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_white_bookmark_filled));
        } else {
            binding.ivBookmark.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_white_bookmark_unfilled));
        }
    }

}
