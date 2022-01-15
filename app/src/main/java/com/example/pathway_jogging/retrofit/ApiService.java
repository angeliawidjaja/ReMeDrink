package com.example.pathway_jogging.retrofit;

import com.example.pathway_jogging.datamodel.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Angelia Widjaja on 14-Jan-22 21:55.
 */
public interface ApiService {
    @POST("users/")
    Call<UserResponse> createUser(@Body UserResponse register);

    @GET("users/")
    Call<List<UserResponse>> getUserList();

//    @GET("users/{id}")
//    Call<UserListDetailResponse> getUserDetail(@Path("id") int id);
}
