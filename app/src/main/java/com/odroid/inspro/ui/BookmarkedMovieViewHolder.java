package com.odroid.inspro.ui;

import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.odroid.inspro.R;
import com.odroid.inspro.databinding.BookmarkedMovieItemBinding;
import com.odroid.inspro.entity.BaseMovie;
import com.odroid.inspro.entity.Constants;

public class BookmarkedMovieViewHolder extends BaseViewHolder {

   private BookmarkedMovieItemBinding bookmarkedMovieItemBinding;
   private MoviesListAdapter.MovieClickListener movieClickListener;
   private Context context;

   public BookmarkedMovieViewHolder(BookmarkedMovieItemBinding viewBinding, Context context,
                                    MoviesListAdapter.MovieClickListener movieClickListener) {
      super(viewBinding.getRoot());
      this.bookmarkedMovieItemBinding = viewBinding;
      this.movieClickListener = movieClickListener;
      this.context = context;
   }

   void bindView(BaseMovie movieItem) {
      String posterUrl = Constants.POSTER_BASE_URL + movieItem.posterUrl;
      Glide.with(context).load(posterUrl).into(bookmarkedMovieItemBinding.ivMovieIcon);
      bookmarkedMovieItemBinding.tvMovieTitle.setText(movieItem.title);
      String rating = movieItem.rating + "/10";
      bookmarkedMovieItemBinding.tvMovieRating.setText(rating);
      if (movieItem.isBookmarked) {
         bookmarkedMovieItemBinding.ivBookmark.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_bookmark_filled));
      } else {
         bookmarkedMovieItemBinding.ivBookmark.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_bookmark_unfilled));
      }
      bookmarkedMovieItemBinding.getRoot().setOnClickListener(view -> movieClickListener.onMovieClicked(movieItem));
      bookmarkedMovieItemBinding.ivBookmark.setOnClickListener(view -> movieClickListener.bookmarkMovie(movieItem.id, !movieItem.isBookmarked));
   }
}
