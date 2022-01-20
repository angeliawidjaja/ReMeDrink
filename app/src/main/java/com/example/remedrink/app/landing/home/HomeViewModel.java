package com.example.remedrink.app.landing.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.remedrink.datamodel.user.UserResponse;
import com.example.remedrink.repository.Repository;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<UserResponse> userData;
    private Repository repository;

    public HomeViewModel() {
        userData = new MutableLiveData<>();
        repository = Repository.getInstance();
    }

    public MutableLiveData<UserResponse> getUserData() {
        return userData;
    }

    public void updateUser(UserResponse user) {
        Integer waterIntakeGoal = calculateWaterIntakeGoal(user.getWeight());
        user.setWaterIntakeGoal(waterIntakeGoal);
        user.setWaterIntakeIdeal(waterIntakeGoal);
        repository.updateUser(user, response -> {
            if(response != null) userData.setValue(user);
        });
    }

    private Integer calculateWaterIntakeGoal(Integer weight) {
        return Double.valueOf(weight * 0.033 * 1000).intValue();
    }
}