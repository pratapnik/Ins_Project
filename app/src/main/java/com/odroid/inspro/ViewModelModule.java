package com.odroid.inspro;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
   @Binds
   @IntoMap
   @MovieViewModelKey(MainActivityViewModel.class)
   abstract ViewModel bindUserProfileViewModel(MainActivityViewModel mainActivityViewModel);

   @Binds
   abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProvider.Factory factory);
}
