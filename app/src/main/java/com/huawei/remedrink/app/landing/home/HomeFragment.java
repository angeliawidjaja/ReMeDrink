package com.huawei.remedrink.app.landing.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.BannerAdSize;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.ads.banner.BannerView;
import com.huawei.remedrink.R;
import com.huawei.remedrink.app.PlankActivity;
import com.huawei.remedrink.app.SquatActivity;
import com.huawei.remedrink.databinding.FragmentHomeBinding;
import com.huawei.remedrink.datamodel.user.UserLoginData;
import com.huawei.remedrink.datamodel.user.UserResponse;

public class HomeFragment extends Fragment {

    private BannerView bannerView;
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private UserResponse userLoginData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HwAds.init(getContext());


        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        userLoginData = new UserLoginData(requireContext()).getUserLoginData();

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initWelcome();
        setHeightWeight();
        initListener();
        handleUpdateData();

        return root;

    }

    private void handleUpdateData() {
        homeViewModel.getUserData().observe(getViewLifecycleOwner(), userResponse -> {
            setHeightWeight();
            new UserLoginData(requireContext()).saveUser(userResponse);
        });
    }

    private void initWelcome() {
        String welcome = "Hi " + userLoginData.getFullname() + "!";
        binding.textHome.setText(welcome);
    }

    private void setHeightWeight() {
        binding.weightValue.setText(userLoginData.getWeight() + " kg");
        binding.heightValue.setText(userLoginData.getHeight() + " cm");
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(view != null) {
            bannerView = view.findViewById(R.id.hw_banner_view);
            bannerView.setAdId("testw6vs28auh3");

            AdListener adListener = new AdListener() {
                @Override
                public void onAdLoaded() {
                }
                @Override
                public void onAdFailed(int errorCode) {
                    Toast.makeText(view.getContext(), "failed " + errorCode, Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onAdOpened() {
                }
                @Override
                public void onAdClicked() {
                }
                @Override
                public void onAdLeave() {
                }
                @Override
                public void onAdClosed() {
                }
            };
            bannerView.setAdListener(adListener);

            Log.d("bannerView", "onViewCreated: "+bannerView);
            bannerView.setBannerAdSize(BannerAdSize.BANNER_SIZE_360_57);
            // Set the refresh interval to 60 seconds.
            bannerView.setBannerRefresh(60);
            // Create an ad request to load an ad.
            AdParam adParam = new AdParam.Builder().build();
            bannerView.loadAd(adParam);
        }
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
        binding.btnWeight.setOnClickListener(new View.OnClickListener() {
            TextView text;

            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View formsWeight = inflater.inflate(R.layout.form_weight, null, false);
                final NumberPicker inp_weight = (NumberPicker) formsWeight.findViewById(R.id.weight_num_pick);

                text = formsWeight.findViewById(R.id.text);
                text.setText("Weight: " + userLoginData.getWeight() + "Kg");
                NumberPicker np = formsWeight.findViewById(R.id.weight_num_pick);
                np.setMinValue(0);
                np.setMaxValue(300);
                np.setValue(userLoginData.getWeight());
                np.setOnValueChangedListener((picker, oldVal, newVal) -> text.setText("Weight: " + newVal + "Kg"));

                new AlertDialog.Builder(getActivity())
                        .setView(formsWeight)
                        .setPositiveButton("SET",
                                (dialog, id) -> {
                                    String weight = "Weight Update: " + inp_weight.getValue() + "Kg";
                                    Toast.makeText(getActivity(), weight, Toast.LENGTH_SHORT).show();
                                    userLoginData.setWeight(inp_weight.getValue());
                                    homeViewModel.updateUser(userLoginData);
                                    dialog.cancel();
                                })
                        .setNegativeButton("CANCEL",
                                (dialog, id) -> dialog.dismiss()).show();
            }
        });

        binding.btnHeight.setOnClickListener(new View.OnClickListener() {
            TextView text;

            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View formsHeight = inflater.inflate(R.layout.form_height, null, false);
                final NumberPicker inp_height = (NumberPicker) formsHeight.findViewById(R.id.height_num_pick);

                text = formsHeight.findViewById(R.id.text);
                text.setText("Height: " + userLoginData.getHeight() + "cm");
                NumberPicker np = formsHeight.findViewById(R.id.height_num_pick);
                np.setMinValue(50);
                np.setMaxValue(300);
                np.setValue(userLoginData.getHeight());
                np.setOnValueChangedListener((picker, oldVal, newVal) -> text.setText("Height: " + newVal + "cm"));

                new AlertDialog.Builder(getActivity())
                        .setView(formsHeight)
                        .setPositiveButton("SET",
                                (dialog, id) -> {
                                    String height = "Height Update: " + inp_height.getValue() + "cm";
                                    Toast.makeText(getActivity(), height, Toast.LENGTH_SHORT).show();
                                    userLoginData.setHeight(inp_height.getValue());
                                    homeViewModel.updateUser(userLoginData);
                                    dialog.cancel();
                                })
                        .setNegativeButton("CANCEL",
                                (dialog, id) -> dialog.dismiss()).show();
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}

