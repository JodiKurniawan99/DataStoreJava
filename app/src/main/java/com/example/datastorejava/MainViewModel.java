package com.example.datastorejava;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;


public class MainViewModel extends ViewModel {
    private final SettingPreferences pref;

    public MainViewModel(SettingPreferences pref) {
        this.pref = pref;
    }

    //LiveDataReactiveStreams.fromPublisher used for convert Flowable to Livedata
    public LiveData<Boolean> getThemeSettings() {
        return LiveDataReactiveStreams.fromPublisher(pref.getThemeSetting());
    }

    public void saveThemeSetting(Boolean isDarkModeActive) {
        pref.saveThemeSetting(isDarkModeActive);
    }
}