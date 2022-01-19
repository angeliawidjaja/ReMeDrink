package com.example.remedrink.app.landing.tracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.remedrink.R;
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
        waterIntake();

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

    public void waterIntake() {
        binding.ivAddWaterIntake.setOnClickListener(new View.OnClickListener() {
            AlertDialog.Builder dialog;
            LayoutInflater inflater;
            View dialogView;

            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View formsAddWaterIntake = inflater.inflate(R.layout.pick_water, null, false);
                final EditText inp_water = (EditText) formsAddWaterIntake.findViewById(R.id.num_add_water);

                new AlertDialog.Builder(getActivity())
                        .setView(formsAddWaterIntake)
                        .setPositiveButton("ADD",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        String water = "Water : " + inp_water.getText() + "ml";
                                        Toast.makeText(getActivity(), water, Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                    }
                                })
                        .setNegativeButton("CANCEL",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                }).show();
            }
        });
    }
}