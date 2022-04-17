package com.odroid.inspro.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.odroid.inspro.databinding.BookmarkedMovieItemBinding;
import com.odroid.inspro.entity.BaseMovie;
import com.odroid.inspro.databinding.MovieItemBinding;
import com.odroid.inspro.entity.MovieViewHolderType;
import com.odroid.inspro.ui.view_holder.BaseViewHolder;
import com.odroid.inspro.ui.view_holder.BookmarkedMovieViewHolder;
import com.odroid.inspro.ui.view_holder.MovieViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MoviesListAdapter extends RecyclerView.Adapter {

    private Context context;

    private ArrayList<BaseMovie> movieList = new ArrayList<>();

    private MovieClickListener movieClickListener;
    private MovieViewHolderType movieViewHolderType;

    public MoviesListAdapter(Context context, MovieClickListener movieClickListener,
                             MovieViewHolderType movieViewHolderType) {
        this.context = context;
        this.movieClickListener = movieClickListener;
        this.movieViewHolderType = movieViewHolderType;
    }

    public void updateMovieList(List<BaseMovie> newMoviesList) {
        MoviesListDiffCallBack diffCallback = new MoviesListDiffCallBack(movieList, newMoviesList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        movieList.clear();
        movieList.addAll(newMoviesList);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        if (movieViewHolderType == MovieViewHolderType.BOOKMARKED) {
            return new BookmarkedMovieViewHolder(
                    BookmarkedMovieItemBinding.inflate(layoutInflater, parent, false), context, movieClickListener
            );
        } else {
            return new MovieViewHolder(
                    MovieItemBinding.inflate(layoutInflater, parent, false), context, movieClickListener
            );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (movieViewHolderType == MovieViewHolderType.OTHERS) {
            MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
            movieViewHolder.bindView(movieList.get(position));
        } else {
            BookmarkedMovieViewHolder bookmarkedMovieViewHolder = (BookmarkedMovieViewHolder) holder;
            bookmarkedMovieViewHolder.bindView(movieList.get(position));
        }

    }


    @Override
    public int getItemCount() {
        return movieList.size();
    }


    public interface MovieClickListener {
        void onMovieClicked(BaseMovie baseMovie);

        void bookmarkMovie(long movieId, boolean bookmark);
    }
}
