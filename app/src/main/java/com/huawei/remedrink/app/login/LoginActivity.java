package com.huawei.remedrink.app.login;

import static com.huawei.remedrink.service.HuaweiConstants.HMS_APP_ID;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.remedrink.R;
import com.huawei.remedrink.app.landing.HomeActivity;
import com.huawei.remedrink.app.register.RegisterActivity;
import com.huawei.remedrink.databinding.ActivityLoginBinding;
import com.huawei.remedrink.datamodel.user.UserLoginData;
import com.huawei.remedrink.datamodel.user.UserResponse;
import com.huawei.remedrink.service.PushService;

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
                obtainToken(loginResult);
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
        PushService.sendNotification("Welcome to ReMeDrink!", "Hi, " + user.getFullname() + "!\nThank you for downloading ReMeDrink!\nAlways track down your water intake every day!");
        intentToHome();
    }

    private void obtainToken(UserResponse user) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String token = HmsInstanceId.getInstance(getApplicationContext()).getToken(HMS_APP_ID, "HCM");
                    Log.d("<PushNotifLog>", "Token: " + token);
                    user.setToken(token);
                    handleLoginSuccess(user);
                } catch (Exception e) {
                    e.printStackTrace();
                    handleLoginSuccess(user);
                }
            }
        }.start();
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