package com.example.remedrink.app.landing.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.remedrink.R;
import com.example.remedrink.app.splash.SplashScreenActivity;
import com.example.remedrink.databinding.FragmentProfileBinding;
import com.example.remedrink.datamodel.user.UserLoginData;
import com.example.remedrink.datamodel.user.UserResponse;

//import com.example.remedrink.databinding.ActivityLogoutBinding;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;
    private UserResponse userLoginData;
//    private ActivityLogoutBinding binding;
    private Button btn_logout;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityLogoutBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

//        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(ProfileFragment.this, SplashScreenActivity.class));
            }
        });
    }
}