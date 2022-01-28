package com.huawei.remedrink.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Angelia Widjaja on 28-Jan-22 11:00.
 */
public class BroadcastService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("<RE>", "onReceive: Masuk Broadcast Receiver");
        PushService.sendNotification(
                "Water Intake Reminder"
                ,"Don't forget to track down your recent water intake"
                , context
        );
    }
}
