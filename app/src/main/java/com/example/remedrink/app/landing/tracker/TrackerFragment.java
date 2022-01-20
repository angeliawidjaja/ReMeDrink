package com.example.remedrink.app.landing.tracker;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.remedrink.R;
import com.example.remedrink.databinding.FragmentTrackerBinding;
import com.example.remedrink.datamodel.drink.MyDrinkItemResponse;
import com.example.remedrink.datamodel.user.UserLoginData;
import com.example.remedrink.datamodel.user.UserResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class TrackerFragment extends Fragment {

    private TrackerViewModel trackerViewModel;
    private FragmentTrackerBinding binding;
    private HistoryDrinkAdapter adapter;
    private UserResponse userLoginData;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initViewModel();
        userLoginData = new UserLoginData(requireContext()).getUserLoginData();

        binding = FragmentTrackerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initComponents();
        requestTodayDrinkHistoryData();
        handleObserveDrinkHistoryDataResult();
        waterIntake();

        return root;
    }

    private void initComponents() {
        binding.tvIdealWaterIntakeValue.setText(userLoginData.getWaterIntakeIdeal().toString());
        binding.tvWaterIntakeGoalValue.setText(userLoginData.getWaterIntakeGoal().toString());
        binding.waterIntakeProgressBar.setMax(userLoginData.getWaterIntakeGoal());
        binding.tvMaxIntake.setText(userLoginData.getWaterIntakeGoal().toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        initComponents();
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

    public void waterIntake() {
        binding.ivAddWaterIntake.setOnClickListener(v -> {
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final View formsAddWaterIntake = inflater.inflate(R.layout.pick_water, null, false);
            final EditText inp_water = (EditText) formsAddWaterIntake.findViewById(R.id.num_add_water);

            new AlertDialog.Builder(getActivity())
                    .setView(formsAddWaterIntake)
                    .setPositiveButton("ADD",
                            (dialog, id) -> {
                                String water = "Water : " + inp_water.getText() + "ml";
                                Toast.makeText(getActivity(), water, Toast.LENGTH_SHORT).show();
                                handleAddDrink(Integer.valueOf(inp_water.getText().toString()));
                                dialog.cancel();
                            })
                    .setNegativeButton("CANCEL",
                            (dialog, id) -> dialog.dismiss()).show();
        });
    }

    private void handleAddDrink(Integer drinkSize) {
        String drinkSizeType;
        if (drinkSize <= 300) drinkSizeType = "Small";
        else if (drinkSize > 300 && drinkSize <= 600) drinkSizeType = "Medium";
        else drinkSizeType = "Large";

        trackerViewModel.addNewDrink(new MyDrinkItemResponse(new Date(), drinkSizeType, drinkSize));
    }
}