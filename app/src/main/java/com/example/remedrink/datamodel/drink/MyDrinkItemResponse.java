package com.example.remedrink.datamodel.drink;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Angelia Widjaja on 19-Jan-22 17:43.
 */
public class MyDrinkItemResponse {
    private Date createdAt;
    private String drinkSizeType;
    private Integer drinkSize;
    private String id;

    @SuppressLint("SimpleDateFormat")
    public String getCreatedAt() {
        return new SimpleDateFormat("dd MMM yyyy, hh:mm a").format(createdAt);
    }

    public Date getOriCreatedAt() {
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
