package com.odroid.inspro.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.odroid.inspro.common.InsApp;
import com.odroid.inspro.R;
import com.odroid.inspro.databinding.ActivityMainBinding;
import com.odroid.inspro.ui.view_model.MovieViewModelFactory;
import com.odroid.inspro.ui.view_model.SharedViewModel;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SharedViewModel sharedViewModel;

    @Inject
    MovieViewModelFactory movieViewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ((InsApp) getApplication()).getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedViewModel = new ViewModelProvider(this, movieViewModelFactory).get(SharedViewModel.class);

        sharedViewModel.fetchMovies();

        setUpNavigation();
    }

    public void setUpNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(binding.bottomNavViewDashboard,
                navHostFragment.getNavController());
    }
}