package com.huawei.remedrink.datamodel.drink;

import java.util.List;

/**
 * Created by Angelia Widjaja on 20-Jan-22 04:44.
 */
public class DrinkModel {
    private Integer totalWaterIntake;
    private List<MyDrinkItemResponse> drinkList;

    public DrinkModel(Integer totalWaterIntake, List<MyDrinkItemResponse> drinkList) {
        this.totalWaterIntake = totalWaterIntake;
        this.drinkList = drinkList;
    }

    public Integer getTotalWaterIntake() {
        return totalWaterIntake;
    }

    public void setTotalWaterIntake(Integer totalWaterIntake) {
        this.totalWaterIntake = totalWaterIntake;
    }

    public List<MyDrinkItemResponse> getDrinkList() {
        return drinkList;
    }

    public void setDrinkList(List<MyDrinkItemResponse> drinkList) {
        this.drinkList = drinkList;
    }
}
