package com.example.pathway_jogging.app.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pathway_jogging.R;
import com.example.pathway_jogging.datamodel.UserResponse;
import com.example.pathway_jogging.repository.Repository;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private final MutableLiveData<UserResponse> loginResponse = new MutableLiveData<>();
    private final Repository repository = Repository.getInstance();

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public MutableLiveData<UserResponse> getLoginResponse() {
        return loginResponse;
    }

    public void login(String email, String password) {
        repository.getUsers(userList -> {
            if(userList != null && !userList.isEmpty()) {
                for (UserResponse user: userList) {
                    if(user.getEmail().equals(email) && user.getPassword().equals(password)) {
                        loginResponse.setValue(user);
                        return;
                    }
                }
            }
            loginResponse.setValue(new UserResponse(R.string.invalid_email_or_password));
        });
    }

    public boolean validateLoginData(String email, String password) {
        if (!isEmailValid(email)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_email, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState());
            return true;
        }
        return false;
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
}