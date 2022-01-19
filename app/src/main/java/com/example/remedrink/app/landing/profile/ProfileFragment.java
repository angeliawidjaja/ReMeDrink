package com.example.remedrink.app.landing.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.remedrink.databinding.FragmentProfileBinding;
import com.example.remedrink.datamodel.user.UserLoginData;
import com.example.remedrink.datamodel.user.UserResponse;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;
    private UserResponse userLoginData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setData();

//        final TextView textView = binding.textNotifications;
//        profileViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));
        return root;
    }

    private void setData() {
        userLoginData = new UserLoginData(requireActivity().getApplicationContext()).getUserLoginData();
        binding.tvUsername.setText(userLoginData.getUsername());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}