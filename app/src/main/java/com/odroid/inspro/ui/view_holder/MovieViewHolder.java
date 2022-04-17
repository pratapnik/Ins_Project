package com.odroid.inspro.ui.view_holder;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.odroid.inspro.R;
import com.odroid.inspro.databinding.MovieItemBinding;
import com.odroid.inspro.entity.BaseMovie;
import com.odroid.inspro.entity.Constants;
import com.odroid.inspro.ui.adapter.MoviesListAdapter;

public class MovieViewHolder extends BaseViewHolder {

   private MovieItemBinding movieItemBinding;
   private MoviesListAdapter.MovieClickListener movieClickListener;
   private Context context;

   public MovieViewHolder(MovieItemBinding viewBinding, Context context, MoviesListAdapter.MovieClickListener movieClickListener) {
      super(viewBinding.getRoot());
      this.movieItemBinding = viewBinding;
      this.movieClickListener = movieClickListener;
      this.context = context;
   }

   public void bindView(BaseMovie movieItem) {
      String posterUrl = Constants.POSTER_BASE_URL + movieItem.posterUrl;
      Glide.with(context).load(posterUrl).into(movieItemBinding.ivMovieIcon);
      movieItemBinding.tvMovieTitle.setText(movieItem.title);
      String rating = movieItem.rating + "/10";
      movieItemBinding.tvMovieRating.setText(rating);
      if (movieItem.isBookmarked) {
         movieItemBinding.ivBookmark.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_bookmark_filled));
      } else {
         movieItemBinding.ivBookmark.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_bookmark_unfilled));
      }
      movieItemBinding.getRoot().setOnClickListener(view -> movieClickListener.onMovieClicked(movieItem));
      movieItemBinding.ivBookmark.setOnClickListener(view -> movieClickListener.bookmarkMovie(movieItem.id, !movieItem.isBookmarked));
   }
}
