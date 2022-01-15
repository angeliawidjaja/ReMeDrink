package com.example.pathway_jogging.app.register.login;

import android.app.Activity;

import androidx.lifecycle.ViewModelProvider;

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

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final ProgressBar loadingProgressBar = binding.loading;

        registerViewModel.getLoginResult().observe(this, loginResult -> {
            if (loginResult == null) {
                return;
            }
            loadingProgressBar.setVisibility(View.GONE);
            if (loginResult.getError() != null) {
                showLoginFailed(loginResult.getError());
            }
            if (loginResult.getSuccess() != null) {
                updateUiWithUser(loginResult.getSuccess());
            }
            setResult(Activity.RESULT_OK);

            //Complete and destroy login activity once successful
            finish();
        });
    }

    private void handleObserveFormValidationResult() {
        registerViewModel.getRegisterFormState().observe(this, registerFormState -> {
            if (registerFormState == null) return;
            if(registerFormState.getFullnameError() != null) {
                binding.fullname.setError(getString(registerFormState.getFullnameError()));
            }
            if (registerFormState.getUsernameError() != null) {
                binding.username.setError(getString(registerFormState.getUsernameError()));
            }
            if(registerFormState.getEmailError() != null) {
                binding.email.setError(getString(registerFormState.getEmailError()));
            }
            if (registerFormState.getPasswordError() != null) {
                binding.password.setError(getString(registerFormState.getPasswordError()));
            }
            if(registerFormState.getConfirmPasswordError() != null) {
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
            String fullname, username, email, password, confirmpassword;
            fullname = binding.fullname.toString();
            username = binding.username.toString();
            email = binding.email.toString();
            password = binding.password.toString();
            confirmpassword = binding.confirmPassword.toString();

            if(registerViewModel.validateRegisterData(fullname, username, email, password, confirmpassword)) {
                doRegister(fullname, username, email, password, confirmpassword);
            }
        });

        loginButton.setOnClickListener(v -> {
            finish();
        });
    }

    private void doRegister(String fullname, String username, String email, String password, String confirmpassword) {
        binding.loading.setVisibility(View.VISIBLE);
        registerViewModel.register(fullname, username, email, password, confirmpassword);
    }

    private void initViewModel() {
        registerViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(RegisterViewModel.class);
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}