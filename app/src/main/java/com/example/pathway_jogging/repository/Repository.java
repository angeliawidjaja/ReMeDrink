package com.example.pathway_jogging.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.pathway_jogging.app.register.RegisterResponse;
import com.example.pathway_jogging.retrofit.ApiClient;
import com.example.pathway_jogging.retrofit.ApiService;
import com.google.gson.Gson;

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

    public void addNewUser(String fullName, String username, String email, String password, final RequestHandler<RegisterResponse> requestHandler) {
        RegisterResponse request = new RegisterResponse(fullName, username, email, password);
        Log.d("<REQ>", "createUser: " + new Gson().toJson(request));
        Call<RegisterResponse> call = apiService.createUser(request);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(@NonNull Call<RegisterResponse> call, @NonNull Response<RegisterResponse> response) {
                Log.d("<RES>", "createUser: " + new Gson().toJson(response.body()));
                requestHandler.onResult(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<RegisterResponse> call, @NonNull Throwable t) {
                Log.d("<RES>", "createUser: " + t.getMessage());
                requestHandler.onResult(null);
            }
        });
    }
}
