package com.example.datastorejava;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.rxjava3.RxDataStore;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class SettingPreferences {

    //this initialization for datastore prefrences name theme_setting, this name must unique
    private final Preferences.Key<Boolean> THEME_KEY = PreferencesKeys.booleanKey("theme_setting");
    private final RxDataStore<Preferences> dataStore;

    private SettingPreferences(RxDataStore<Preferences> dataStore) {
        this.dataStore = dataStore;
    }

    private static volatile SettingPreferences INSTANCE;

    //This function used for create Singelton object of datastore.
    //Singleton object just only used one instance for all class.
    //Singleton can solve memory limit of android or memory leaks.
    static SettingPreferences getInstance(final RxDataStore<Preferences> dataStore) {
        //this condition check is instance available or not
        if (INSTANCE == null) {
            //syncronized function used for only one thread can run this function.
            //Because in android app can be multi threaded proccess,So an instance could potentially be created on a different thread.
            //If there are 2 instances alive with different data in each instance, it will result in inconsistent data anomalies.
            //If there are 2 instances alive with different data in each instance, it will result in inconsistent data anomalies. That's why Singleton plays a crucial role here.
            synchronized (SettingPreferences.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SettingPreferences(dataStore);
                }
            }
        }
        return INSTANCE;
    }

    //This function used for get prefrences data store value.
    public Flowable<Boolean> getThemeSetting() {
        return dataStore.data().map(preferences -> {
                    if (preferences.get(THEME_KEY) != null) {
                        return preferences.get(THEME_KEY);
                    } else {
                        return false;
                    }
                }
        );
    }

    public void saveThemeSetting(Boolean isDarkModeActive) {
        dataStore.updateDataAsync(preferences -> {
            MutablePreferences mutablePreferences = preferences.toMutablePreferences();
            mutablePreferences.set(THEME_KEY, isDarkModeActive);
            return Single.just(mutablePreferences);
        });
    }
}
