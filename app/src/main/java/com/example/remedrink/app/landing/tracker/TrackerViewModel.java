package com.example.remedrink.app.landing.tracker;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.remedrink.datamodel.drink.MyDrinkItemResponse;
import com.example.remedrink.datamodel.user.UserLoginData;
import com.example.remedrink.datamodel.user.UserResponse;
import com.example.remedrink.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class TrackerViewModel extends ViewModel {

    private final Repository repository = Repository.getInstance();
    private MutableLiveData<List<MyDrinkItemResponse>> myDrinkListResponse;
    private UserResponse userLoginData;

    public MutableLiveData<List<MyDrinkItemResponse>> getMyDrinkListResponse() {
        return myDrinkListResponse;
    }

    public TrackerViewModel(Context context) {
        myDrinkListResponse = new MutableLiveData<>();
        userLoginData = new UserLoginData(context).getUserLoginData();
    }

    public void getMyDrinkHistoryList() {
        repository.getMyDrinkHistory(userLoginData.getId(), response -> {
            if(response != null && !response.isEmpty())
                myDrinkListResponse.setValue(response);
            else
                myDrinkListResponse.setValue(new ArrayList<>());
        });
    }
}