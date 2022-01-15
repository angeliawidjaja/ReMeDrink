package com.example.pathway_jogging.datamodel;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by Angelia Widjaja on 15-Jan-22 10:40.
 */
public class UserLoginData {
    private SharedPreferences sharedPreferences;
    private Context context;
    private Gson gson;
    private String key = "user_login_data";

    public UserLoginData(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(Constant.USER_SHARED_PREF, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public ArrayList<UserResponse> getUserLoginData() {
        return sharedPreferences.contains(key) ?
                gson.fromJson(
                        sharedPreferences.getString(key, ""),
                        new TypeToken<ArrayList<UserResponse>>(){}.getType())
                : new ArrayList<>();
    }

    public void saveUser(UserResponse user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<UserResponse> data = getUserLoginData();
        data.add(user);
        String json = gson.toJson(data);
        editor.putString(key, json);
        editor.apply();
    }

    public void deleteUser() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
