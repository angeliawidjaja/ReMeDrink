package com.example.remedrink.app.login;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.remedrink.R;
import com.example.remedrink.app.landing.HomeActivity;
import com.example.remedrink.app.register.RegisterActivity;
import com.example.remedrink.databinding.ActivityLoginBinding;
import com.example.remedrink.datamodel.user.UserLoginData;
import com.example.remedrink.datamodel.user.UserResponse;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    private Button loginButton;
    private Button registerButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initComponents();
        initViewModel();
        initListener();
        handleObserveFormValidationResult();
        handleObserveLoginResult();
    }

    private void doLogin() {
        binding.loading.setVisibility(View.VISIBLE);
        loginViewModel.login(binding.email.getText().toString(),
                binding.password.getText().toString());
    }

    private void handleObserveLoginResult() {
        loginViewModel.getLoginResponse().observe(this, loginResult -> {
            if (loginResult == null) {
                return;
            }
            binding.loading.setVisibility(View.GONE);
            if (loginResult.getError() != null) {
                showLoginFailed(loginResult.getError());
            }
            else {
                handleLoginSuccess(loginResult);
            }
        });
    }

    private void handleObserveFormValidationResult() {
        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            if (loginFormState.getEmailError() != null) {
                binding.email.setError(getString(loginFormState.getEmailError()));
            }
            if (loginFormState.getPasswordError() != null) {
                binding.password.setError(getString(loginFormState.getPasswordError()));
            }
        });
    }

    private void initComponents() {
        loginButton = binding.layoutBtnLoginRegister.btnAbove;
        registerButton = binding.layoutBtnLoginRegister.btnBelow;
    }

    private void initListener() {
        loginButton.setOnClickListener(v -> {
            if(loginViewModel.validateLoginData(binding.email.getText().toString(),
                    binding.password.getText().toString())) {
                doLogin();
            }
        });

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void initViewModel() {
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
    }

    private void handleLoginSuccess(UserResponse user) {
        saveUserIntoSharedPref(user);
        Toast.makeText(getApplicationContext(), getString(R.string.text_welcome) + user.getFullname(), Toast.LENGTH_LONG).show();
        intentToHome();
    }

    private void intentToHome() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveUserIntoSharedPref(UserResponse user) {
        user.setWaterIntakeIdeal(user.getWaterIntakeGoal());
        UserLoginData loginData = new UserLoginData(getApplicationContext());
        loginData.saveUser(user);
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.text_error);
        alertDialogBuilder.setMessage(errorString);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setPositiveButton(R.string.text_ok, (dialog, which) -> dialog.dismiss());
        alertDialogBuilder.create().show();
    }
}