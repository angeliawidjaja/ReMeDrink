package com.huawei.remedrink.retrofit;

import static com.huawei.remedrink.service.HuaweiConstants.ACCESS_TOKEN_CONTENT_TYPE;
import static com.huawei.remedrink.service.HuaweiConstants.PUSH_CONTENT_TYPE;

import com.huawei.remedrink.datamodel.drink.MyDrinkItemResponse;
import com.huawei.remedrink.datamodel.user.AccessTokenModel;
import com.huawei.remedrink.datamodel.user.UserResponse;
import com.huawei.remedrink.datamodel.pushmodel.PushNotifModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

    @Headers("Content-Type:" + PUSH_CONTENT_TYPE)
    @POST("v1/{app_id}/messages:send")
    Call<ResponseBody> postNotification(@Path("app_id") String appId, @Header("Authorization") String authorization, @Body PushNotifModel notification);

    @FormUrlEncoded
    @Headers("Content-Type:" + ACCESS_TOKEN_CONTENT_TYPE)
    @POST("oauth2/v3/token")
    Call<AccessTokenModel> createAccessToken(@Field("grant_type") String grant_type, @Field("client_secret") String client_secret, @Field("client_id") String client_id);
}
