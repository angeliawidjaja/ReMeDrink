package com.huawei.remedrink.app.landing.tracker;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.huawei.remedrink.R;
import com.huawei.remedrink.databinding.FragmentTrackerBinding;
import com.huawei.remedrink.datamodel.drink.MyDrinkItemResponse;
import com.huawei.remedrink.datamodel.user.UserLoginData;
import com.huawei.remedrink.datamodel.user.UserResponse;
import com.huawei.remedrink.service.PushService;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class TrackerFragment extends Fragment implements PushService.NotifState {

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
        initListener();
        requestTodayDrinkHistoryData();
        handleObserveDrinkHistoryDataResult();
        waterIntake();

        return root;
    }

    private void initListener() {
        binding.ivDrinkReminder.setOnClickListener(v -> {
            if (userLoginData.getNotifOn()) {
                PushService.turnOffNotification(getContext(), this);
            } else {
                PushService.turnOnNotification(getContext(), this);
            }
        });
    }

    @SuppressLint({"SetTextI18n"})
    private void initComponents() {
        userLoginData = new UserLoginData(requireContext()).getUserLoginData();
        handleReminderIconState();
        binding.tvIdealWaterIntakeValue.setText(userLoginData.getWaterIntakeIdeal() + " ml");
        binding.tvWaterIntakeGoalValue.setText(userLoginData.getWaterIntakeGoal() + " ml");
        binding.waterIntakeProgressBar.setMax(userLoginData.getWaterIntakeGoal());
        binding.tvMaxIntake.setText(userLoginData.getWaterIntakeGoal().toString());
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void handleReminderIconState() {
        Log.d("<RE>", "handleReminderIconState: " + userLoginData.getNotifOn());
        if(userLoginData.getNotifOn()) {
            binding.ivDrinkReminder.setImageDrawable(getResources().getDrawable(R.drawable.ic_cancel_reminder));
            binding.tvDrinkReminderLabel.setText(R.string.disable_reminder);
        } else {
            binding.ivDrinkReminder.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_reminder));
            binding.tvDrinkReminderLabel.setText(R.string.enable_reminder);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        userLoginData = new UserLoginData(requireContext()).getUserLoginData();
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
            final EditText inp_water = formsAddWaterIntake.findViewById(R.id.num_add_water);

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
        else if (drinkSize <= 600) drinkSizeType = "Medium";
        else drinkSizeType = "Large";

        trackerViewModel.addNewDrink(new MyDrinkItemResponse(new Date(), drinkSizeType, drinkSize));
    }

    @Override
    public void onNotificationStateChange() {
        userLoginData = new UserLoginData(requireContext()).getUserLoginData();
        handleReminderIconState();
    }
}