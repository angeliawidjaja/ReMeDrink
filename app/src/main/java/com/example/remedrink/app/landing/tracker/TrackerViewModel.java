package com.example.remedrink.app.landing.tracker;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.remedrink.datamodel.drink.DrinkModel;
import com.example.remedrink.datamodel.drink.MyDrinkItemResponse;
import com.example.remedrink.datamodel.user.UserLoginData;
import com.example.remedrink.datamodel.user.UserResponse;
import com.example.remedrink.repository.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class TrackerViewModel extends ViewModel {

    private final Repository repository = Repository.getInstance();
    private MutableLiveData<DrinkModel> myDrinkListResponse;
    private UserResponse userLoginData;

    public MutableLiveData<DrinkModel> getMyDrinkListResponse() {
        return myDrinkListResponse;
    }

    public TrackerViewModel(Context context) {
        myDrinkListResponse = new MutableLiveData<>();
        userLoginData = new UserLoginData(context).getUserLoginData();
    }

    @SuppressLint("SimpleDateFormat")
    public void getTodayDrinkHistoryList() {
        repository.getMyDrinkHistory(userLoginData.getId(), response -> {
            if(response != null && !response.isEmpty()) {
                Integer totalWaterIntake = 0;
                ArrayList<MyDrinkItemResponse> finalData = new ArrayList<>();
                for (MyDrinkItemResponse res: response) {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
                    String resDate = formatter.format(res.getOriCreatedAt());
                    String todayDate = formatter.format(new Date());
                    if(resDate.equals(todayDate)) {
                        totalWaterIntake += res.getDrinkSize();
                        finalData.add(res);
                    }
                }
                Collections.sort(finalData, (o1, o2) -> {
                    if (o1.getOriCreatedAt() == null || o2.getOriCreatedAt() == null)
                        return 0;
                    return o2.getOriCreatedAt().compareTo(o1.getOriCreatedAt());
                });
                myDrinkListResponse.setValue(new DrinkModel(totalWaterIntake, finalData));
            }
            else
                myDrinkListResponse.setValue(new DrinkModel(0, new ArrayList<>()));
        });
    }

    public void addNewDrink(MyDrinkItemResponse drink) {
        repository.addNewDrink(userLoginData.getId(), drink, response -> {
            if(response != null) {
                getTodayDrinkHistoryList();
            }
        });
    }
}