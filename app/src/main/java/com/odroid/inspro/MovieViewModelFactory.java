package com.odroid.inspro;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

public class MovieViewModelFactory implements ViewModelProvider.Factory {
   private final Map<Class<? extends ViewModel>, Provider<ViewModel>> creators;

   @Inject
   public MovieViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
      this.creators = creators;
   }

   @Override
   public <T extends ViewModel> T create(Class<T> modelClass) {
      Provider<? extends ViewModel> creator = creators.get(modelClass);
      if (creator == null) {
         throw new IllegalArgumentException("Unknown model class " + modelClass);
      }
      return (T) creator.get();
   }
}