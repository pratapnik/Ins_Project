package com.odroid.inspro;

import static com.odroid.inspro.InsApp.getApplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.odroid.inspro.databinding.FragmentBookmarkedMoviesBinding;

public class BookmarkedMoviesFragment extends Fragment {

    private FragmentBookmarkedMoviesBinding binding;

    public BookmarkedMoviesFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBookmarkedMoviesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((InsApp) getApplication()).getAppComponent().inject(this);
    }
}
