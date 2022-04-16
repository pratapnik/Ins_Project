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
   @MovieViewModelKey(SharedViewModel.class)
   abstract ViewModel bindUserProfileViewModel(SharedViewModel sharedViewModel);

   @Binds
   abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProvider.Factory factory);
}
