package com.huawei.remedrink.app.landing.profile.editprofile;

import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.huawei.remedrink.R;
import com.huawei.remedrink.datamodel.user.UserResponse;
import com.huawei.remedrink.repository.Repository;

/**
 * Created by Angelia Widjaja on 20-Jan-22 09:09.
 */
public class EditProfileViewModel extends ViewModel {

    private final MutableLiveData<EditProfileFormState> editProfileFormState = new MutableLiveData<>();
    private final MutableLiveData<UserResponse> checkUserResponse = new MutableLiveData<>();
    private final MutableLiveData<UserResponse> editProfileResult = new MutableLiveData<>();
    private final Repository repository = Repository.getInstance();

    public MutableLiveData<EditProfileFormState> getEditProfileFormState() {
        return editProfileFormState;
    }

    public MutableLiveData<UserResponse> getCheckUserResponse() {
        return checkUserResponse;
    }

    public MutableLiveData<UserResponse> getEditProfileResult() {
        return editProfileResult;
    }

    public void checkUser(UserResponse userLoginData, String inputPassword) {
        String usePass = inputPassword.isEmpty() ? userLoginData.getPassword() : inputPassword;
        repository.getUsers(userList -> {
            if(userList != null && !userList.isEmpty()) {
                for (UserResponse user: userList) {
                    if(user.getEmail().equals(userLoginData.getEmail()) && user.getPassword().equals(usePass)) {
                        checkUserResponse.setValue(user);
                        return;
                    }
                }
            }
            checkUserResponse.setValue(new UserResponse(R.string.invalid_old_password));
        });
    }

    public void updateUser(UserResponse userLoginData, String fullName, String username, String newPassword) {
        UserResponse request = userLoginData;
        request.setFullname(fullName);
        request.setUsername(username);
        if(newPassword != null && !newPassword.isEmpty()) request.setPassword(newPassword);
        repository.updateUser(request, response -> {
            if(response != null) editProfileResult.setValue(response);
            else editProfileResult.setValue(new UserResponse(R.string.edit_profile));
        });
    }

    public boolean validateEditProfileData(String fullName, String username, String oldPassword, String newPassword) {
        if (!isFullNameValid(fullName)) {
            editProfileFormState.setValue(new EditProfileFormState(R.string.invalid_fullname, null, null, null));
        } else if (!isUserNameValid(username)) {
            editProfileFormState.setValue(new EditProfileFormState(null, R.string.invalid_username, null, null));
        } else if (!newPassword.isEmpty() && !isPasswordValid(newPassword)) {
            editProfileFormState.setValue(new EditProfileFormState(null, null, null, R.string.invalid_password));
        } else if (!newPassword.isEmpty() && !isPasswordValid(oldPassword)) {
            editProfileFormState.setValue(new EditProfileFormState(null, null, R.string.invalid_password, null));
        } else {
            editProfileFormState.setValue(new EditProfileFormState());
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

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
