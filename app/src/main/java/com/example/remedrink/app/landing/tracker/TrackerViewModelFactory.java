package com.example.remedrink.app.landing.tracker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Created by Angelia Widjaja on 19-Jan-22 17:57.
 */
public class TrackerViewModelFactory implements ViewModelProvider.Factory {
    private Context context;

    public TrackerViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TrackerViewModel.class)) {
            return (T) new TrackerViewModel(context);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }

}
