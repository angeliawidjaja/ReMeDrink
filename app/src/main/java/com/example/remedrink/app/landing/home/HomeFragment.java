package com.example.remedrink.app.landing.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.remedrink.PlankActivity;
import com.example.remedrink.SquatActivity;
import com.example.remedrink.app.landing.HomeActivity;
import com.example.remedrink.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initListener();

        return root;
    }

    private void initListener() {
        binding.btnSquat.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SquatActivity.class);
            startActivity(intent);
        });
        binding.btnPlank.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PlankActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}