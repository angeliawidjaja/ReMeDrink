package com.example.remedrink.app.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.remedrink.R;
import com.example.remedrink.app.landing.HomeActivity;
import com.example.remedrink.app.login.LoginActivity;
import com.example.remedrink.datamodel.user.UserLoginData;

import java.util.Objects;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Objects.requireNonNull(getSupportActionBar()).hide();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
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