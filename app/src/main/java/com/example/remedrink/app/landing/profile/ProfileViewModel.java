package com.example.remedrink.app.landing.profile;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.remedrink.datamodel.drink.HistoryDrinkModel;
import com.example.remedrink.datamodel.drink.MyDrinkItemResponse;
import com.example.remedrink.repository.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<HistoryDrinkModel> listDrink;
    private Repository repository;

    public ProfileViewModel() {
        listDrink = new MutableLiveData<>();
        repository = Repository.getInstance();
    }

    public MutableLiveData<HistoryDrinkModel> getListDrink() {
        return listDrink;
    }

    public void getAllListDrinkHistory(String id) {
        repository.getMyDrinkHistory(id, response -> {
            if(response != null && !response.isEmpty()) {
                ArrayList<MyDrinkItemResponse> todayDrink = new ArrayList<>();
                ArrayList<MyDrinkItemResponse> pastDrink = new ArrayList<>();
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
                for (MyDrinkItemResponse res: response) {
                    String resDate = formatter.format(res.getOriCreatedAt());
                    String todayDate = formatter.format(new Date());
                    if(resDate.equals(todayDate)) {
                        todayDrink.add(res);
                    } else {
                        pastDrink.add(res);
                    }
                }
                listDrink.setValue(new HistoryDrinkModel(todayDrink, pastDrink));
            }
            else
                listDrink.setValue(new HistoryDrinkModel());
        });
    }
}