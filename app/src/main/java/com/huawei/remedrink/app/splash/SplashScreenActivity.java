package com.huawei.remedrink.app.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.huawei.hms.analytics.HiAnalytics;
import com.huawei.hms.analytics.HiAnalyticsInstance;
import com.huawei.hms.analytics.HiAnalyticsTools;
import com.huawei.remedrink.R;
import com.huawei.remedrink.app.landing.HomeActivity;
import com.huawei.remedrink.app.login.LoginActivity;
import com.huawei.remedrink.datamodel.user.UserLoginData;

import java.util.Objects;

public class SplashScreenActivity extends AppCompatActivity {
    HiAnalyticsInstance instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Objects.requireNonNull(getSupportActionBar()).hide();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            HiAnalyticsTools.enableLog();
            instance = HiAnalytics.getInstance(this);
            Context context = this.getApplicationContext();
            HiAnalyticsInstance instance = HiAnalytics.getInstance(context);
            instance.setUserProfile("userKey","value");
            handleIntent();
        }, 2500);
    }

    private void handleIntent() {
        Intent intent;
        if(new UserLoginData(getApplicationContext()).getUserLoginData().getEmail() != null) {
            intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
        }
        else {
            intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }
}