package com.example.pathway_jogging.app.register;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pathway_jogging.R;
import com.example.pathway_jogging.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private RegisterViewModel registerViewModel;
    private ActivityRegisterBinding binding;
    private Button registerButton;
    private Button loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initComponents();
        initViewModel();
        initListener();
        handleObserveFormValidationResult();
        handleObserveRegisterResult(); // Not Yet
    }

    private void handleObserveRegisterResult() {
        registerViewModel.getRegisterResponse().observe(this, registerResult -> {
            if (registerResult == null) {
                return;
            }
            binding.loading.setVisibility(View.GONE);
            if (registerResult.getError() != null) {
                showLoginFailed(registerResult.getError());
            } else {
                handleRegisterSuccess();
            }
        });
    }

    private void handleObserveFormValidationResult() {
        registerViewModel.getRegisterFormState().observe(this, registerFormState -> {
            if (registerFormState == null) return;
            if (registerFormState.getFullnameError() != null) {
                binding.fullname.setError(getString(registerFormState.getFullnameError()));
            }
            if (registerFormState.getUsernameError() != null) {
                binding.username.setError(getString(registerFormState.getUsernameError()));
            }
            if (registerFormState.getEmailError() != null) {
                binding.email.setError(getString(registerFormState.getEmailError()));
            }
            if (registerFormState.getPasswordError() != null) {
                binding.password.setError(getString(registerFormState.getPasswordError()));
            }
            if (registerFormState.getConfirmPasswordError() != null) {
                binding.confirmPassword.setError(getString(registerFormState.getConfirmPasswordError()));
            }
        });
    }

    private void initComponents() {
        registerButton = binding.layoutBtnLoginRegister.btnAbove;
        loginButton = binding.layoutBtnLoginRegister.btnBelow;
    }

    private void initListener() {
        registerButton.setOnClickListener(v -> {
            String fullName, username, email, password, confirmPassword;
            fullName = binding.fullname.getText().toString();
            username = binding.username.getText().toString();
            email = binding.email.getText().toString();
            password = binding.password.getText().toString();
            confirmPassword = binding.confirmPassword.getText().toString();

            if (registerViewModel.validateRegisterData(fullName, username, email, password, confirmPassword)) {
                doRegister(fullName, username, email, password);
            }
        });

        loginButton.setOnClickListener(v -> finish());
    }

    private void doRegister(String fullName, String username, String email, String password) {
        binding.loading.setVisibility(View.VISIBLE);
        registerViewModel.register(fullName, username, email, password);
    }

    private void initViewModel() {
        registerViewModel = new ViewModelProvider(this, new RegisterViewModelFactory())
                .get(RegisterViewModel.class);
    }

    private void handleRegisterSuccess() {
        Toast.makeText(getApplicationContext(), R.string.prompt_login_account, Toast.LENGTH_LONG).show();
        finish();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}