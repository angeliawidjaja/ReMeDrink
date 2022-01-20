package com.example.remedrink.app.landing.profile.drinkhistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.example.remedrink.R;
import com.example.remedrink.app.landing.tracker.HistoryDrinkAdapter;
import com.example.remedrink.databinding.ActivityDrinkHistoryBinding;
import com.example.remedrink.datamodel.drink.HistoryDrinkModel;

import java.util.Objects;

public class DrinkHistoryActivity extends AppCompatActivity {

    private ActivityDrinkHistoryBinding binding;
    private HistoryDrinkModel listDrink = new HistoryDrinkModel();
    private HistoryDrinkAdapter todayAdapter;
    private HistoryDrinkAdapter pastAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDrinkHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getDrinkListData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void initAdapter() {
        todayAdapter = new HistoryDrinkAdapter(getApplicationContext(), listDrink.getTodayDrink());
        binding.rvTodayDrink.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.rvTodayDrink.setAdapter(todayAdapter);

        pastAdapter = new HistoryDrinkAdapter(getApplicationContext(), listDrink.getPastDrink());
        binding.rvPastDrink.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.rvPastDrink.setAdapter(pastAdapter);
    }

    private void getDrinkListData() {
        listDrink = (HistoryDrinkModel) getIntent().getSerializableExtra("listDrink");
        initAdapter();
    }
}