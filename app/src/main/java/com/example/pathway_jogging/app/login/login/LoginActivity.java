package com.example.pathway_jogging.app.login.login;

import android.app.Activity;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pathway_jogging.R;
import com.example.pathway_jogging.app.register.login.RegisterActivity;
import com.example.pathway_jogging.databinding.ActivityLoginBinding;

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
        handleObserveFormValidationResult(); // Not Yet
        handleObserveLoginResult(); // Not Yet
    }

    private void doLogin() {
        binding.loading.setVisibility(View.VISIBLE);
        loginViewModel.login(binding.email.getText().toString(),
                binding.password.getText().toString());
    }

    private void handleObserveLoginResult() {
        loginViewModel.getLoginResult().observe(this, loginResult -> {
            if (loginResult == null) {
                return;
            }
            binding.loading.setVisibility(View.GONE);
            if (loginResult.getError() != null) {
                showLoginFailed(loginResult.getError());
            }
            if (loginResult.getSuccess() != null) {
                handleLoginSuccess(loginResult.getSuccess());
            }
        });
    }

    private void handleObserveFormValidationResult() {
        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            loginButton.setEnabled(loginFormState.isDataValid());
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

    private void handleLoginSuccess(LoggedInUserView model) {
        // Save Into Shared Pref
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
//        finish();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}