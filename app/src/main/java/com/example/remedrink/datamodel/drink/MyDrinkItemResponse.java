package com.example.remedrink.datamodel.drink;

/**
 * Created by Angelia Widjaja on 19-Jan-22 17:43.
 */
public class MyDrinkItemResponse {
    private String createdAt;
    private String drinkSizeType;
    private Integer drinkSize;
    private String id;

    public String getCreatedAt() {
        return createdAt;
    }

    public String getDrinkSizeType() {
        return drinkSizeType;
    }

    public Integer getDrinkSize() {
        return drinkSize;
    }

    public String getId() {
        return id;
    }
}
