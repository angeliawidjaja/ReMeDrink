package com.example.remedrink.app.landing.profile;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.remedrink.app.landing.profile.drinkhistory.DrinkHistoryActivity;
import com.example.remedrink.app.landing.profile.editprofile.EditProfileActivity;
import com.example.remedrink.app.splash.SplashScreenActivity;
import com.example.remedrink.databinding.FragmentProfileBinding;
import com.example.remedrink.datamodel.drink.HistoryDrinkModel;
import com.example.remedrink.datamodel.user.UserLoginData;
import com.example.remedrink.datamodel.user.UserResponse;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;
    private UserResponse userLoginData;
    private HistoryDrinkModel listDrink = new HistoryDrinkModel();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        userLoginData = new UserLoginData(requireContext()).getUserLoginData();
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        requestListDrink();
        setData();
        handleListDrink();
        initListener();
        return root;
    }

    private void handleListDrink() {
        profileViewModel.getListDrink().observe(getViewLifecycleOwner(), historyDrinkModel -> {
            listDrink = historyDrinkModel;
            handleWaterDataDisplay(listDrink);
        });
    }

    private void handleWaterDataDisplay(HistoryDrinkModel drink) {
        binding.TVWaterVolDay.setText(drink.getTodayWaterVolume() + " ml");
        binding.TVTotDrinkDay.setText(drink.getTodayTotalDrink() + " drink(s)");
        binding.waterVolWeek.setText(drink.getPastWaterVolume() + " ml");
        binding.totDrinkWeek.setText(drink.getPastTotalDrink() + " drink(s)");
    }

    private void requestListDrink() {
        profileViewModel.getAllListDrinkHistory(userLoginData.getId());
    }

    private void initListener() {
        binding.tvEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            startActivity(intent);
        });
        binding.btnHistory.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DrinkHistoryActivity.class);
            intent.putExtra("listDrink", listDrink);
            startActivity(intent);
        });
        binding.btnLogout.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Logout")
                    .setMessage("Are you sure want to Logout?")
                    .setPositiveButton("Yes", (dialog, which) -> handleLogout())
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }

    private void handleLogout() {
        new UserLoginData(requireContext()).deleteUser();
        Intent intent = new Intent(getActivity(), SplashScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
    }

    private void setData() {
        userLoginData = new UserLoginData(requireContext()).getUserLoginData();
        binding.tvUsername.setText(userLoginData.getUsername());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}