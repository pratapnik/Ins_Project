package com.odroid.inspro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.odroid.inspro.databinding.ActivityMainBinding;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ((InsApp) getApplication()).getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ((InsApp) getApplication()).getAppComponent().provideThreadManager().fetchTrendingMoviesFromRemote();
    }
}