package com.odroid.inspro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.odroid.inspro.databinding.MovieItemBinding;

import java.util.Collections;
import java.util.List;

public class NowPlayingMoviesListAdapter extends RecyclerView.Adapter<NowPlayingMoviesListAdapter.NowPlayingMovieViewHolder> {

    private Context context;

    private List<NowPlayingMovie> movieList = Collections.emptyList();

    public NowPlayingMoviesListAdapter(Context context) {
        this.context = context;
    }

    public void updateNowPlayingMovieList(List<NowPlayingMovie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NowPlayingMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new NowPlayingMovieViewHolder(
                MovieItemBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                ), context
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NowPlayingMovieViewHolder holder, int position) {
        holder.bindView(movieList.get(position));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class NowPlayingMovieViewHolder extends RecyclerView.ViewHolder {

        private MovieItemBinding movieItemBinding;
        private Context context;

        public NowPlayingMovieViewHolder(MovieItemBinding viewBinding, Context context) {
            super(viewBinding.getRoot());
            this.movieItemBinding = viewBinding;
            this.context = context;
        }

        void bindView(NowPlayingMovie movieItem) {
            String posterUrl = Constants.POSTER_BASE_URL + movieItem.posterUrl;
            Glide.with(context).load(posterUrl).into(movieItemBinding.ivMovieIcon);
            movieItemBinding.tvMovieTitle.setText(movieItem.title);
            movieItemBinding.tvMovieReleaseDate.setText(movieItem.releaseDate);
        }
    }
}
