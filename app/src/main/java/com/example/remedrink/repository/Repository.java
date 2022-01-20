package com.example.remedrink.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.remedrink.datamodel.drink.MyDrinkItemResponse;
import com.example.remedrink.datamodel.user.UserResponse;
import com.example.remedrink.retrofit.ApiClient;
import com.example.remedrink.retrofit.ApiService;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Angelia Widjaja on 14-Jan-22 21:58.
 */
public class Repository {
    private static volatile Repository instance;
    private final ApiService apiService;

    public Repository() {
        apiService = ApiClient.getApiService();
    }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public void getUsers(final RequestHandler<List<UserResponse>> requestHandler) {
        Call<List<UserResponse>> call = apiService.getUserList();
        call.enqueue(new Callback<List<UserResponse>>() {
            @Override
            public void onResponse(Call<List<UserResponse>> call, Response<List<UserResponse>> response) {
                Log.d("<RES>", "getUsers: " + new Gson().toJson(response.body()));
                requestHandler.onResult(response.body());
            }

            @Override
            public void onFailure(Call<List<UserResponse>> call, Throwable t) {
                Log.d("<RES>", "getUsers: " + t.getMessage());
                requestHandler.onResult(null);
            }
        });
    }

    public void addNewUser(String fullName, String username, String email, String password, Integer waterIntakeGoal, final RequestHandler<UserResponse> requestHandler) {
        UserResponse request = new UserResponse(fullName, username, email, password, 100, 50, waterIntakeGoal);
        Log.d("<REQ>", "createUser: " + new Gson().toJson(request));
        Call<UserResponse> call = apiService.createUser(request);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                Log.d("<RES>", "createUser: " + new Gson().toJson(response.body()));
                requestHandler.onResult(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                Log.d("<RES>", "createUser: " + t.getMessage());
                requestHandler.onResult(null);
            }
        });
    }

    public void updateUser(UserResponse currUserData, final RequestHandler<UserResponse> requestHandler) {
        Log.d("<REQ>", "updateUser: " + new Gson().toJson(currUserData.getId()));
        Call<UserResponse> call = apiService.updateUser(currUserData.getId(), currUserData);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                Log.d("<RES>", "updateUser: " + new Gson().toJson(response.body()));
                requestHandler.onResult(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                Log.d("<RES>", "updateUser: " + t.getMessage());
                requestHandler.onResult(null);
            }
        });
    }

    public void getMyDrinkHistory(String userId, final RequestHandler<List<MyDrinkItemResponse>> requestHandler) {
        Log.d("<REQ>", "getMyDrinkHistory: " + new Gson().toJson(userId));
        Call<List<MyDrinkItemResponse>> call = apiService.getMyDrinkHistory(userId);
        call.enqueue(new Callback<List<MyDrinkItemResponse>>() {
            @Override
            public void onResponse(Call<List<MyDrinkItemResponse>> call, Response<List<MyDrinkItemResponse>> response) {
                Log.d("<RES>", "getMyDrinkHistory: " + new Gson().toJson(response.body()));
                requestHandler.onResult(response.body());
            }

            @Override
            public void onFailure(Call<List<MyDrinkItemResponse>> call, Throwable t) {
                Log.d("<RES>", "getMyDrinkHistory: " + t.getMessage());
                requestHandler.onResult(null);
            }
        });
    }

    public void addNewDrink(String userId, MyDrinkItemResponse drink, final RequestHandler<MyDrinkItemResponse> requestHandler) {
        Log.d("<REQ>", "addNewDrink: " + new Gson().toJson(userId));
        Call<MyDrinkItemResponse> call = apiService.addNewDrink(userId, drink);
        call.enqueue(new Callback<MyDrinkItemResponse>() {
            @Override
            public void onResponse(Call<MyDrinkItemResponse> call, Response<MyDrinkItemResponse> response) {
                Log.d("<RES>", "addNewDrink: " + new Gson().toJson(response.body()));
                requestHandler.onResult(response.body());
            }

            @Override
            public void onFailure(Call<MyDrinkItemResponse> call, Throwable t) {
                Log.d("<RES>", "getMyDrinkHistory: " + t.getMessage());
                requestHandler.onResult(null);
            }
        });
    }
}
