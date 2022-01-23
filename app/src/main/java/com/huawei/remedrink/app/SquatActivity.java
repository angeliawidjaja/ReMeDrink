package com.huawei.remedrink.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.huawei.remedrink.R;

import java.util.Objects;

public class SquatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squat);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}