package com.huawei.remedrink.retrofit;

import android.annotation.SuppressLint;

import androidx.annotation.Nullable;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Angelia Widjaja on 14-Jan-22 21:54.
 */
public class ApiClient {
    public static final String BASE_URL = "https://61e1878163f8fc0017618ce4.mockapi.io/";
    public static final String BASE_HMS_PUSH_URL = "https://push-api.cloud.huawei.com/";
    public static final String BASE_HMS_ACCESS_TOKEN_URL = "https://oauth-login.cloud.huawei.com/";
    private static Retrofit retrofit;

    public enum UrlType {
        MOCK, HMS_PUSH, HMS_ACCESS_TOKEN
    }

    public static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ApiService getApiService(UrlType urlType) {
        String usedBaseURL;
        switch (urlType) {
            case HMS_PUSH:
                usedBaseURL = BASE_HMS_PUSH_URL;
                break;
            case HMS_ACCESS_TOKEN:
                usedBaseURL = BASE_HMS_ACCESS_TOKEN_URL;
                break;
            default:
                usedBaseURL = BASE_URL;
                break;
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(usedBaseURL)
                .client(getUnsafeOkHttpClient().build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiService.class);
    }
}
