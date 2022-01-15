package com.example.pathway_jogging.app.login;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pathway_jogging.R;
import com.example.pathway_jogging.app.register.RegisterActivity;
import com.example.pathway_jogging.databinding.ActivityLoginBinding;
import com.example.pathway_jogging.datamodel.UserResponse;

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
        // Save Into Shared Pref
        String welcome = "Hi " + user.getFullname();
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
//        finish();
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