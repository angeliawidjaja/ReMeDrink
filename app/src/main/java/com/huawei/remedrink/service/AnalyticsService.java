package com.huawei.remedrink.service;

import android.content.Context;
import android.os.Bundle;

import com.huawei.hms.analytics.HiAnalytics;
import com.huawei.hms.analytics.HiAnalyticsInstance;
import com.huawei.hms.analytics.HiAnalyticsTools;
import com.huawei.remedrink.R;

import androidx.appcompat.app.AppCompatActivity;

public class AnalyticsService extends AppCompatActivity {
    HiAnalyticsInstance instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        HiAnalyticsTools.enableLog();
        instance = HiAnalytics.getInstance(this);

        Context context = this.getApplicationContext();
        HiAnalyticsInstance instance = HiAnalytics.getInstance(context);
        instance.setUserProfile("userKey","value");

    }
}
