package com.huawei.remedrink.datamodel.user;

import android.content.Context;
import android.content.SharedPreferences;

import com.huawei.remedrink.datamodel.Constant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

    public UserResponse getUserLoginData() {
        return sharedPreferences.contains(key) ?
                gson.fromJson(
                        sharedPreferences.getString(key, ""),
                        new TypeToken<UserResponse>(){}.getType())
                : new UserResponse();
    }

    public boolean saveUser(UserResponse user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(user);
        editor.putString(key, json);
        editor.apply();
        return true;
    }

    public void deleteUser() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
