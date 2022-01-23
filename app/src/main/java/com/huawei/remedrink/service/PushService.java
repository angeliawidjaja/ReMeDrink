package com.huawei.remedrink.service;

import android.os.Bundle;
import android.util.Log;

import com.huawei.hms.push.HmsMessageService;

/**
 * Created by Angelia Widjaja on 20-Jan-22 13:31.
 */
public class PushService extends HmsMessageService {
    private static final String TAG = "<PushNotifLog>";

    @Override
    public void onNewToken(String token, Bundle bundle) {
        super.onNewToken(token);
        Log.d(TAG, "Receive New Token: " + token);
    }
}
