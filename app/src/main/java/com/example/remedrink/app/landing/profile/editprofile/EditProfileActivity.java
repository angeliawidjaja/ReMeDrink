package com.example.remedrink.app.landing.profile.editprofile;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.example.remedrink.R;
import com.example.remedrink.app.landing.profile.ProfileViewModel;
import com.example.remedrink.databinding.ActivityEditProfileBinding;
import com.example.remedrink.datamodel.user.UserLoginData;
import com.example.remedrink.datamodel.user.UserResponse;

import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {

    private EditProfileViewModel editProfileViewModel;
    private ActivityEditProfileBinding binding;
    private UserResponse userLoginData;
    private String username, fullname, newPass, oldPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editProfileViewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);
        userLoginData = new UserLoginData(getApplicationContext()).getUserLoginData();
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setData();
        initListener();
        handleObserveResult();
    }

    private void handleObserveResult() {
        editProfileViewModel.getEditProfileFormState().observe(this, editProfileFormState -> {
            if(editProfileFormState == null) return;
            if (editProfileFormState.getFullnameError() != null) {
                binding.fullname.setError(getString(editProfileFormState.getFullnameError()));
            }
            if (editProfileFormState.getUsernameError() != null) {
                binding.username.setError(getString(editProfileFormState.getUsernameError()));
            }
            if (editProfileFormState.getNewPasswordError() != null) {
                newPass = null;
                oldPass = null;
                binding.newPassword.setError(getString(editProfileFormState.getNewPasswordError()));
            }
            if (editProfileFormState.getOldPasswordError() != null) {
                binding.oldPassword.setError(getString(editProfileFormState.getOldPasswordError()));
            }
        });

        editProfileViewModel.getCheckUserResponse().observe(this, userResponse -> {
            if (userResponse == null) return;
            if (userResponse.getError() != null) {
                showEditFailed(userResponse.getError());
            } else {
                editProfileViewModel.updateUser(userLoginData, fullname, username, newPass);
            }
        });

        editProfileViewModel.getEditProfileResult().observe(this, userResponse -> {
            if (userResponse.getError() != null) {
                showEditFailed(userResponse.getError());
            } else {
                new UserLoginData(getApplicationContext()).saveUser(userResponse);
                finish();
            }
        });
    }

    private void setData() {
        binding.username.setText(userLoginData.getUsername());
        binding.fullname.setText(userLoginData.getFullname());
        binding.email.setText(userLoginData.getEmail());
    }

    private void initListener() {
        binding.btnEditProfile.setOnClickListener(v -> {
            username = binding.username.getText().toString();
            fullname = binding.fullname.getText().toString();
            newPass = binding.newPassword.getText().toString();
            oldPass = binding.oldPassword.getText().toString();
            if(editProfileViewModel.validateEditProfileData(fullname, username, oldPass, newPass)) {
                editProfileViewModel.checkUser(userLoginData, oldPass);
            }
        });
    }

    private void showEditFailed(@StringRes Integer errorString) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.text_error);
        alertDialogBuilder.setMessage(errorString);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setPositiveButton(R.string.text_ok, (dialog, which) -> dialog.dismiss());
        alertDialogBuilder.create().show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}