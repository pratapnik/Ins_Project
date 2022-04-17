package com.odroid.inspro.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.odroid.inspro.R;
import com.odroid.inspro.entity.BaseMovie;
import com.odroid.inspro.entity.Constants;
import com.odroid.inspro.databinding.MovieItemBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MoviesListAdapter extends RecyclerView.Adapter {

    private Context context;

    private List<BaseMovie> movieList = new ArrayList<>();

    private MovieClickListener movieClickListener;

    public MoviesListAdapter(Context context, MovieClickListener movieClickListener) {
        this.context = context;
        this.movieClickListener = movieClickListener;
    }

    public void updateMovieList(List<BaseMovie> newMoviesList) {
//        MoviesListDiffCallBack diffCallback = new MoviesListDiffCallBack(movieList, newMoviesList);
//        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
//        movieList.clear();
//        movieList.addAll(newMoviesList);
//        diffResult.dispatchUpdatesTo(this);
        this.movieList = newMoviesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        return new MovieViewHolder(
                MovieItemBinding.inflate(layoutInflater, parent, false), context
        );
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
        private Context context;

        public MovieViewHolder(MovieItemBinding viewBinding, Context context) {
            super(viewBinding.getRoot());
            this.movieItemBinding = viewBinding;
            this.context = context;
        }


        void bindView(BaseMovie movieItem) {

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

    public interface MovieClickListener {
        void onMovieClicked(BaseMovie baseMovie);

        void bookmarkMovie(long movieId, boolean bookmark);
    }
}
