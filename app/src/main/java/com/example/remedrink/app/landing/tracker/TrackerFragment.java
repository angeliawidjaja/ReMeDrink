package com.example.remedrink.app.landing.tracker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.remedrink.databinding.FragmentTrackerBinding;
import com.example.remedrink.datamodel.drink.MyDrinkItemResponse;

import java.util.List;

public class TrackerFragment extends Fragment {

    private TrackerViewModel trackerViewModel;
    private FragmentTrackerBinding binding;
    private HistoryDrinkAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initViewModel();

        binding = FragmentTrackerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        requestTodayDrinkHistoryData();
        handleObserveDrinkHistoryDataResult();

        return root;
    }

    @SuppressLint("SetTextI18n")
    private void handleObserveDrinkHistoryDataResult() {
        trackerViewModel.getMyDrinkListResponse().observe(getViewLifecycleOwner(), drinkModel -> {
            binding.tvCurrIntake.setText(drinkModel.getTotalWaterIntake().toString());
            binding.waterIntakeProgressBar.setProgress(drinkModel.getTotalWaterIntake());
            if(drinkModel.getDrinkList().isEmpty()) return;
            handleAdapter(drinkModel.getDrinkList());
        });
    }

    private void handleAdapter(List<MyDrinkItemResponse> listDrink) {
        if(adapter == null) {
            adapter = new HistoryDrinkAdapter(getContext(), listDrink);
            binding.rvDrinkHistory.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.rvDrinkHistory.setAdapter(adapter);
        }
        else adapter.setData(listDrink);
    }

    private void requestTodayDrinkHistoryData() {
        trackerViewModel.getTodayDrinkHistoryList();
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