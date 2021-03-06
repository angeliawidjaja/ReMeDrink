package com.example.remedrink.app.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.example.remedrink.R;
import com.example.remedrink.datamodel.user.UserResponse;
import com.example.remedrink.repository.Repository;

public class RegisterViewModel extends ViewModel {

    private final MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();
    private final MutableLiveData<UserResponse> registerResponse = new MutableLiveData<>();
    private final Repository repository = Repository.getInstance();

    LiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }

    LiveData<UserResponse> getRegisterResponse() {
        return registerResponse;
    }

    public void register(String fullName, String username, String email, String password) {
        repository.getUsers(userList -> {
            if(userList != null && !userList.isEmpty()) {
                for (UserResponse user: userList) {
                    if(user.getUsername().equals(username)) {
                        registerResponse.setValue(new UserResponse(R.string.username_already_exists));
                        return;
                    }
                    else if(user.getEmail().equals(email)) {
                        registerResponse.setValue(new UserResponse(R.string.email_already_exists));
                        return;
                    }
                }
            }

            Integer waterIntakeGoal = 2700;
            repository.addNewUser(fullName, username, email, password, waterIntakeGoal, response -> {
                if (response != null) {
                    registerResponse.setValue(response);
                } else {
                    registerResponse.setValue(new UserResponse(R.string.register_failed));
                }
            });

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