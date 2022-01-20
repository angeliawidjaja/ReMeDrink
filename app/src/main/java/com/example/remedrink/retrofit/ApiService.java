package com.example.remedrink.retrofit;

import com.example.remedrink.datamodel.drink.MyDrinkItemResponse;
import com.example.remedrink.datamodel.user.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Angelia Widjaja on 14-Jan-22 21:55.
 */
public interface ApiService {
    @POST("users/")
    Call<UserResponse> createUser(@Body UserResponse register);

    @GET("users/")
    Call<List<UserResponse>> getUserList();

    @Headers({"Content-Type: application/json"})
    @PUT("users/{id}")
    Call<UserResponse> updateUser(@Path("id") String id, @Body UserResponse body);

    @GET("users/{id}/drink")
    Call<List<MyDrinkItemResponse>> getMyDrinkHistory(@Path("id") String id);

    @POST("users/{id}/drink")
    Call<MyDrinkItemResponse> addNewDrink(@Path("id") String id, @Body MyDrinkItemResponse drink);
}
