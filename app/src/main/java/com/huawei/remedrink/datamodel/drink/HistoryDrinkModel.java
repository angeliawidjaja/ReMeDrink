package com.huawei.remedrink.datamodel.drink;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Angelia Widjaja on 20-Jan-22 07:43.
 */
public class HistoryDrinkModel implements Serializable {
    private List<MyDrinkItemResponse> todayDrink;
    private List<MyDrinkItemResponse> pastDrink;

    public HistoryDrinkModel(List<MyDrinkItemResponse> todayDrink, List<MyDrinkItemResponse> pastDrink) {
        this.todayDrink = todayDrink;
        this.pastDrink = pastDrink;
    }

    public HistoryDrinkModel() {
        this.todayDrink = new ArrayList<>();
        this.pastDrink = new ArrayList<>();
    }

    public List<MyDrinkItemResponse> getTodayDrink() {
        return todayDrink;
    }

    public void setTodayDrink(List<MyDrinkItemResponse> todayDrink) {
        this.todayDrink = todayDrink;
    }

    public List<MyDrinkItemResponse> getPastDrink() {
        return pastDrink;
    }

    public void setPastDrink(List<MyDrinkItemResponse> pastDrink) {
        this.pastDrink = pastDrink;
    }

    public Integer getTodayWaterVolume() {
        Integer waterVolume = 0;
        for (MyDrinkItemResponse water : todayDrink) {
            waterVolume += water.getDrinkSize();
        }
        return waterVolume;
    }

    public Integer getPastWaterVolume() {
        Integer waterVolume = 0;
        for (MyDrinkItemResponse water : pastDrink) {
            waterVolume += water.getDrinkSize();
        }
        return waterVolume;
    }

    public Integer getTodayTotalDrink() {
        return todayDrink.size();
    }

    public Integer getPastTotalDrink() {
        return pastDrink.size();
    }
}
