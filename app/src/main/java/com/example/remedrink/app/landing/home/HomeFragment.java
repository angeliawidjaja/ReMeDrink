package com.example.remedrink.app.landing.home;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.remedrink.PlankActivity;
import com.example.remedrink.R;
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

//        weight

        binding.btnWeight.setOnClickListener(new View.OnClickListener() {
            AlertDialog.Builder dialog;
            LayoutInflater inflater;
            View dialogView;
            TextView text;

            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View formsWeight = inflater.inflate(R.layout.form_weight,null, false);
                final NumberPicker inp_weight = (NumberPicker) formsWeight.findViewById(R.id.weight_num_pick);

                text = formsWeight.findViewById(R.id.text);
                NumberPicker np = formsWeight.findViewById(R.id.weight_num_pick);
                np.setMinValue(0);
                np.setMaxValue(500);
                np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        text.setText("Weight: " + newVal + "Kg");
                    }
                });

                new AlertDialog.Builder(getActivity())
                        .setView(formsWeight)
                        .setPositiveButton("SET",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        String weight = "Weight Update: " + inp_weight.getValue() + "Kg";
                                        Toast.makeText(getActivity(), weight, Toast.LENGTH_SHORT).show();
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

//        height
        binding.btnHeight.setOnClickListener(new View.OnClickListener() {
            AlertDialog.Builder dialog;
            LayoutInflater inflater;
            View dialogView;
            TextView text;

            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View formsHeight = inflater.inflate(R.layout.form_height,null, false);
                final NumberPicker inp_height = (NumberPicker) formsHeight.findViewById(R.id.height_num_pick);

                text = formsHeight.findViewById(R.id.text);
                NumberPicker np = formsHeight.findViewById(R.id.height_num_pick);
                np.setMinValue(0);
                np.setMaxValue(500);
                np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        text.setText("Height: " + newVal + "cm");
                    }
                });

                new AlertDialog.Builder(getActivity())
                        .setView(formsHeight)
                        .setPositiveButton("SET",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        String height = "Height Update: " + inp_height.getValue() + "cm";
                                        Toast.makeText(getActivity(), height, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}