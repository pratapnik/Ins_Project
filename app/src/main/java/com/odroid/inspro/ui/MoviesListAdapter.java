package com.odroid.inspro.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.bumptech.glide.Glide;
import com.odroid.inspro.R;
import com.odroid.inspro.databinding.BookmarkedMovieItemBinding;
import com.odroid.inspro.entity.BookmarkedMovie;
import com.odroid.inspro.entity.Constants;
import com.odroid.inspro.entity.MovieListType;
import com.odroid.inspro.entity.NowPlayingMovie;
import com.odroid.inspro.databinding.MovieItemBinding;
import com.odroid.inspro.entity.TrendingMovie;

import java.util.Collections;
import java.util.List;

public class MoviesListAdapter<T> extends RecyclerView.Adapter {

    private Context context;

    private List<T> movieList = Collections.emptyList();

    private MovieListType movieListType;
    private MovieClickListener movieClickListener;

    public MoviesListAdapter(Context context, MovieListType movieListType, MovieClickListener movieClickListener) {
        this.context = context;
        this.movieListType = movieListType;
        this.movieClickListener = movieClickListener;
    }

    public void updateMovieList(List<T> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        if (movieListType == MovieListType.BOOKMARKED) {
            return new MovieViewHolder(
                    BookmarkedMovieItemBinding.inflate(layoutInflater, parent, false), context
            );
        } else {
            return new MovieViewHolder(
                    MovieItemBinding.inflate(layoutInflater, parent, false), context
            );
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
        movieViewHolder.bindView(movieList.get(position));
    }


    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        private MovieItemBinding movieItemBinding;
        private BookmarkedMovieItemBinding bookmarkedMovieItemBinding;
        private Context context;

        public MovieViewHolder(MovieItemBinding viewBinding, Context context) {
            super(viewBinding.getRoot());
            this.movieItemBinding = viewBinding;
            this.context = context;
        }

        public MovieViewHolder(BookmarkedMovieItemBinding viewBinding, Context context) {
            super(viewBinding.getRoot());
            this.bookmarkedMovieItemBinding = viewBinding;
            this.context = context;
        }


        void bindView(T movieItem) {
            if (movieItem instanceof TrendingMovie) {
                TrendingMovie trendingMovie = (TrendingMovie) movieItem;
                setTrendingMovie(trendingMovie);
                if (trendingMovie.isBookmarked) {
                    movieItemBinding.ivBookmark.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_bookmark_filled));
                } else {
                    movieItemBinding.ivBookmark.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_bookmark_unfilled));
                }
                movieItemBinding.getRoot().setOnClickListener(view -> movieClickListener.onTrendingMovieClicked(trendingMovie));
                movieItemBinding.ivBookmark.setOnClickListener(view -> movieClickListener.bookmarkMovie(trendingMovie.id, !trendingMovie.isBookmarked, MovieListType.TRENDING));
            } else if (movieItem instanceof NowPlayingMovie) {
                NowPlayingMovie nowPlayingMovie = (NowPlayingMovie) movieItem;
                setNowPlayingMovie(nowPlayingMovie);
                if (nowPlayingMovie.isBookmarked) {
                    movieItemBinding.ivBookmark.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_bookmark_filled));
                } else {
                    movieItemBinding.ivBookmark.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_bookmark_unfilled));
                }
                movieItemBinding.getRoot().setOnClickListener(view -> movieClickListener.onNowPlayingMovieClicked(nowPlayingMovie));
                movieItemBinding.ivBookmark.setOnClickListener(view -> movieClickListener.bookmarkMovie(nowPlayingMovie.id, !nowPlayingMovie.isBookmarked, MovieListType.NOW_PLAYING));
            } else if (movieItem instanceof BookmarkedMovie) {
                BookmarkedMovie bookmarkedMovie = (BookmarkedMovie) movieItem;
                setBookmarkedMovie(bookmarkedMovie);
                if (bookmarkedMovie.isBookmarked) {
                    bookmarkedMovieItemBinding.ivBookmark.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_bookmark_filled));
                } else {
                    bookmarkedMovieItemBinding.ivBookmark.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_bookmark_unfilled));
                }
                bookmarkedMovieItemBinding.getRoot().setOnClickListener(view -> movieClickListener.onBookmarkedMovieClicked(bookmarkedMovie));
                bookmarkedMovieItemBinding.ivBookmark.setOnClickListener(view -> movieClickListener.bookmarkMovie(bookmarkedMovie.id, !bookmarkedMovie.isBookmarked, MovieListType.BOOKMARKED));
            }
        }

        private void setTrendingMovie(TrendingMovie trendingMovie) {
            String posterUrl = Constants.POSTER_BASE_URL + trendingMovie.posterUrl;
            Glide.with(context).load(posterUrl).into(movieItemBinding.ivMovieIcon);
            movieItemBinding.tvMovieTitle.setText(trendingMovie.title);
            String rating = trendingMovie.rating + "/10";
            movieItemBinding.tvMovieRating.setText(rating);
        }

        private void setNowPlayingMovie(NowPlayingMovie nowPlayingMovie) {
            String posterUrl = Constants.POSTER_BASE_URL + nowPlayingMovie.posterUrl;
            Glide.with(context).load(posterUrl).into(movieItemBinding.ivMovieIcon);
            movieItemBinding.tvMovieTitle.setText(nowPlayingMovie.title);

            String rating = nowPlayingMovie.rating + "/10";
            movieItemBinding.tvMovieRating.setText(rating);
        }

        private void setBookmarkedMovie(BookmarkedMovie bookmarkedMovie) {
            String posterUrl = Constants.POSTER_BASE_URL + bookmarkedMovie.posterUrl;
            Glide.with(context).load(posterUrl).into(bookmarkedMovieItemBinding.ivMovieIcon);
            bookmarkedMovieItemBinding.tvMovieTitle.setText(bookmarkedMovie.title);

            String rating = bookmarkedMovie.rating + "/10";
            bookmarkedMovieItemBinding.tvMovieRating.setText(rating);
        }
    }

    public interface MovieClickListener {
        void onTrendingMovieClicked(TrendingMovie trendingMovie);

        void onNowPlayingMovieClicked(NowPlayingMovie nowPlayingMovie);

        void onBookmarkedMovieClicked(BookmarkedMovie bookmarkedMovie);

        void bookmarkMovie(long movieId, boolean bookmark, MovieListType movieListType);
    }
}
