package com.huawei.remedrink.service;

import static android.app.PendingIntent.FLAG_ONE_SHOT;

import static com.huawei.remedrink.service.HuaweiConstants.CHANNEL_ID;
import static com.huawei.remedrink.service.HuaweiConstants.CLIENT_SECRET;
import static com.huawei.remedrink.service.HuaweiConstants.HMS_APP_ID;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;
import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.HmsMessaging;
import com.huawei.hms.push.RemoteMessage;
import com.huawei.remedrink.R;
import com.huawei.remedrink.app.splash.SplashScreenActivity;
import com.huawei.remedrink.datamodel.user.AccessTokenModel;
import com.huawei.remedrink.retrofit.ApiClient;
import com.huawei.remedrink.retrofit.ApiService;
import com.huawei.remedrink.service.pushmodel.NotifMessageModel;
import com.huawei.remedrink.service.pushmodel.PushBodyModel;
import com.huawei.remedrink.service.pushmodel.PushNotifModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Angelia Widjaja on 20-Jan-22 13:31.
 */
// Dokumentasi Push Message: https://github.com/myuksektepe/HMSPushDataMessage
public class PushService extends HmsMessageService {
    private static final String TAG = "<RE>";
    private static String token;
    private static String accessToken;

    public static String getToken() {
        return token;
    }

    public void setToken(String token) {
        PushService.token = token;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        PushService.accessToken = accessToken;
    }

    @Override
    public void onNewToken(String token, Bundle bundle) {
        super.onNewToken(token);
        Log.d(TAG, "Receive New Token: " + token);
        setToken(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getDataOfMap() != null) {
            Log.d(TAG, "onMessageReceived: " + new Gson().toJson(remoteMessage.getDataOfMap()));
            Log.d(TAG, "Message title:" + remoteMessage.getDataOfMap().get("title"));
            Log.d(TAG, "Message body:" + remoteMessage.getDataOfMap().get("description"));
        }

        Intent intent = new Intent(this, SplashScreenActivity.class);
        NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int notifId = Integer.parseInt(remoteMessage.getMessageId());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notifManager);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_ONE_SHOT);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(remoteMessage.getDataOfMap().get("title"))
                .setContentText(remoteMessage.getDataOfMap().get("description"))
                .setSmallIcon(R.drawable.app_logo)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notifManager.notify(notifId, notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(NotificationManager notificationManager) {
        String channelName = "ReMeDrinkChannel";
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("ReMeDrink's Channel Description");
        channel.enableLights(true);
        channel.setLightColor(Color.GREEN);
        notificationManager.createNotificationChannel(channel);
    }

    private static void obtainAccessToken(String title, String message) {
        ApiService apiService = ApiClient.getApiService(ApiClient.UrlType.HMS_ACCESS_TOKEN);
        Call<AccessTokenModel> call = apiService.createAccessToken(
                "client_credentials",
                CLIENT_SECRET,
                HMS_APP_ID
        );
        Log.d("<REQ>", "Access Token: " + call.toString());
        call.enqueue(new Callback<AccessTokenModel>() {
            @Override
            public void onResponse(Call<AccessTokenModel> call, Response<AccessTokenModel> response) {
                Log.d("<RES>", "Access Token: " + new Gson().toJson(response.body()));
                if (response.body() != null)
                    setAccessToken(response.body().getAccess_token());
                executeSendNotif(title, message);
            }

            @Override
            public void onFailure(Call<AccessTokenModel> call, Throwable t) {
                Log.e("<RES>", "Access Token: " + t.getMessage());
            }
        });
    }

    private static void executeSendNotif(String title, String message) {
        PushNotifModel notifReqModel = stringifyJSONDataModelAndHandleFinalModel(title, message);
        Log.d("<REQ>", "pushNotification: " + new Gson().toJson(notifReqModel));
        String auth = "Bearer " + getAccessToken();
        ApiService apiService = ApiClient.getApiService(ApiClient.UrlType.HMS_PUSH);
        Call<ResponseBody> call = apiService.postNotification(HMS_APP_ID, auth, notifReqModel);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("<RES>", "pushNotification: " + response);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("<RES>", "pushNotification: " + t.getMessage());
            }
        });
    }

    public static void sendNotification(String title, String message) {
        obtainAccessToken(title, message);
    }

    private static PushNotifModel stringifyJSONDataModelAndHandleFinalModel(String title, String message) {
//        NotifContentDataModel dataJSON = new NotifContentDataModel();
//        dataJSON.setPushbody(pushbody);
        PushBodyModel pushbody = new PushBodyModel(title, message);
        String data = new Gson().toJson(pushbody);

        NotifMessageModel notifData = new NotifMessageModel(data, new String[]{getToken()});
        return new PushNotifModel(notifData);
    }

    public static void turnOnNotification(Context context) {
        HmsMessaging.getInstance(context).turnOnPush().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.i(TAG, "turnOnPush Complete");
            } else {
                Log.e(TAG, "turnOnPush failed: ret=" + task.getException().getMessage());
            }
        });
    }

    public static void turnOffNotification(Context context) {
        HmsMessaging.getInstance(context).turnOffPush().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.i(TAG, "turnOnPush Complete");
            } else {
                Log.e(TAG, "turnOnPush failed: ret=" + task.getException().getMessage());
            }
        });
    }
}
