package com.example.remedrink.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Angelia Widjaja on 14-Jan-22 21:54.
 */
public class ApiClient {
    public static final String BASE_URL = "https://61e1878163f8fc0017618ce4.mockapi.io/";
    private static Retrofit retrofit;

    public static ApiService getApiService(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(ApiService.class);
    }
}
