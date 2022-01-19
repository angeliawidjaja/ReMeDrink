package com.example.remedrink.app.landing.tracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.remedrink.databinding.FragmentTrackerBinding;

public class TrackerFragment extends Fragment {

    private TrackerViewModel trackerViewModel;
    private FragmentTrackerBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initViewModel();

        binding = FragmentTrackerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        requestDrinkHistoryData();
        handleObserveDrinkHistoryDataResult();

        return root;
    }

    private void handleObserveDrinkHistoryDataResult() {
        trackerViewModel.getMyDrinkListResponse().observe(getViewLifecycleOwner(), listDrink -> {
            if(listDrink.isEmpty()) return;
        });
    }

    private void requestDrinkHistoryData() {
        trackerViewModel.getMyDrinkHistoryList();
    }

    private void initViewModel() {
        trackerViewModel = new ViewModelProvider(this, new TrackerViewModelFactory(getContext()))
                .get(TrackerViewModel.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}