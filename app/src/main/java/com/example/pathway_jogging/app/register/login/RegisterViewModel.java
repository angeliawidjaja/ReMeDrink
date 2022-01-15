package com.example.pathway_jogging.app.register.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.example.pathway_jogging.R;
import com.example.pathway_jogging.app.register.data.LoginRepository;
import com.example.pathway_jogging.app.register.data.Result;
import com.example.pathway_jogging.app.register.data.model.LoggedInUser;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    RegisterViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void register(String fullname, String username, String email, String password, String confirmpassword) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(username, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public boolean validateRegisterData(String fullname, String username, String email, String password, String confirmPassword) {
        if (!isFullNameValid(fullname)) {
            registerFormState.setValue(new RegisterFormState(R.string.invalid_fullname, null, null, null, null));
        } else if (!isUserNameValid(username)) {
            registerFormState.setValue(new RegisterFormState(null, R.string.invalid_username, null, null, null));
        } else if (!isEmailValid(email)) {
            registerFormState.setValue(new RegisterFormState(null, null, R.string.invalid_email, null, null));
        } else if (!isPasswordValid(password)) {
            registerFormState.setValue(new RegisterFormState(null, null, null, R.string.invalid_password, null));
        } else if (!isConfirmPasswordValid(password, confirmPassword)) {
            registerFormState.setValue(new RegisterFormState(null, null, null, null, R.string.invalid_confirm_password));
        } else {
            registerFormState.setValue(new RegisterFormState());
            return true;
        }
        return false;
    }

    private boolean isFullNameValid(String fullname) {
        return fullname != null && fullname.trim().length() > 3 && fullname.matches("[ A-Za-z0-9]+$");
    }

    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    private boolean isEmailValid(String email) {
        if (email == null || !email.contains("@")) {
            return false;
        } else {
            return !email.trim().isEmpty();
        }
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    private boolean isConfirmPasswordValid(String password, String confirmPassword) {
        return confirmPassword != null && confirmPassword.equals(password);
    }
}