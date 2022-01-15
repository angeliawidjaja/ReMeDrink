package com.example.pathway_jogging.retrofit;

import com.example.pathway_jogging.app.register.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Angelia Widjaja on 14-Jan-22 21:55.
 */
public interface ApiService {
    @POST("users/")
    Call<RegisterResponse> createUser(@Body RegisterResponse register);

//    @GET("users/")
//    Call<UserListResponse> getCurrentUser();
//
//    @GET("users/{id}")
//    Call<UserListDetailResponse> getUserDetail(@Path("id") int id);
}
