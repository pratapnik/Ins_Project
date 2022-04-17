package com.odroid.inspro.ui;

import androidx.recyclerview.widget.DiffUtil;

import com.odroid.inspro.entity.BaseMovie;

import java.util.List;

public class MoviesListDiffCallBack extends DiffUtil.Callback {

    private List<BaseMovie> oldMovies;
    private List<BaseMovie> newMovies;

    public MoviesListDiffCallBack(List<BaseMovie> oldMovies, List<BaseMovie> newMovies) {
        this.oldMovies = oldMovies;
        this.newMovies = newMovies;
    }

    @Override
    public int getOldListSize() {
        return oldMovies.size();
    }

    @Override
    public int getNewListSize() {
        return newMovies.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldMovies.get(oldItemPosition).id == newMovies.get(newItemPosition).id;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldMovies.get(oldItemPosition).equals(newMovies.get(newItemPosition));
    }
}
