package com.example.datastorejava;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

//This class created, because class MainViewModel with contructor cannot call directly.
//We need to create a class that extends ViewModelProvider in order to be able to inject a constructor into the ViewModel.
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final SettingPreferences pref;

    public ViewModelFactory(SettingPreferences dataStore) {
        this.pref = dataStore;
    }


    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(pref);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}