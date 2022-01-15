package com.example.pathway_jogging.app.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Log;
import android.util.Patterns;

import com.example.pathway_jogging.R;
import com.example.pathway_jogging.repository.Repository;

public class RegisterViewModel extends ViewModel {

    private final MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();
    private final MutableLiveData<RegisterResponse> registerResponse = new MutableLiveData<>();
    private final Repository repository = Repository.getInstance();

    LiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }

    LiveData<RegisterResponse> getRegisterResponse() {
        return registerResponse;
    }

    public void register(String fullName, String username, String email, String password) {
        repository.addNewUser(fullName, username, email, password, response -> {
            if (response != null) {
                registerResponse.setValue(response);
            } else {
                registerResponse.setValue(new RegisterResponse(R.string.register_failed));
            }
        });
    }

    public boolean validateRegisterData(String fullName, String username, String email, String password, String confirmPassword) {
        if (!isFullNameValid(fullName)) {
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

    private boolean isFullNameValid(String fullName) {
        return fullName != null && fullName.trim().length() > 3 && fullName.matches("[A-Za-z ]+");
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