package com.example.pathway_jogging.repository;

import com.example.pathway_jogging.app.login.data.LoginDataSource;
import com.example.pathway_jogging.app.login.data.LoginRepository;
import com.example.pathway_jogging.retrofit.ApiClient;
import com.example.pathway_jogging.retrofit.ApiService;

/**
 * Created by Angelia Widjaja on 14-Jan-22 21:58.
 */
public class Repository {
    private static volatile Repository instance;
    private ApiService apiService;

    public Repository() {
        apiService = ApiClient.getApiService();
    }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public void requestLogin(String email, String password) {

    }

//    public void requestData(int perPage, final int currPage, final RequestHandler requestHandler){
//        Call<UserListResponse> call = apiService.getUserList(perPage, currPage);
//
//        call.enqueue(new Callback<UserListResponse>() {
//            @Override
//            public void onResponse(Call<UserListResponse> call, Response<UserListResponse> userListResponse) {
//                requestHandler.onResult(userListResponse.body());
//            }
//
//            @Override
//            public void onFailure(Call<UserListResponse> call, Throwable t) {
//                requestHandler.onResult(null);
//            }
//        });
//    }
//
//    public void requestUserDetailData(int id, final RequestHandler requestHandler){
//        Call<UserListDetailResponse> call = apiService.getUserDetail(id);
//
//        call.enqueue(new Callback<UserListDetailResponse>() {
//            @Override
//            public void onResponse(Call<UserListDetailResponse> call, Response<UserListDetailResponse> response) {
//                requestHandler.onResult(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<UserListDetailResponse> call, Throwable t) {
//                requestHandler.onResult(null);
//            }
//        });
//    }
}
