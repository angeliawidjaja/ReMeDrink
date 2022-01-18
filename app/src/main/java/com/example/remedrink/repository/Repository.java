package com.example.remedrink.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.remedrink.datamodel.UserResponse;
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

    public void addNewUser(String fullName, String username, String email, String password, final RequestHandler<UserResponse> requestHandler) {
        UserResponse request = new UserResponse(fullName, username, email, password);
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
}
