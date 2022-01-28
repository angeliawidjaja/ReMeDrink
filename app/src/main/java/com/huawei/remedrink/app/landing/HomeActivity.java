package com.huawei.remedrink.app.landing;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.huawei.remedrink.R;
import com.huawei.remedrink.databinding.ActivityHomeBinding;
import com.huawei.remedrink.datamodel.user.UserLoginData;
import com.huawei.remedrink.datamodel.user.UserResponse;
import com.huawei.remedrink.service.BroadcastService;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private UserResponse userLoginData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userLoginData = new UserLoginData(getApplicationContext()).getUserLoginData();
        checkNotifSchedule();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    private void checkNotifSchedule() {
        Date today = new Date(System.currentTimeMillis());
        if(!userLoginData.getScheduleJobDate().equals(today)) {
            userLoginData.setScheduleJobDate(today);
            userLoginData.setTodayJobScheduled(false);
        }
        if(userLoginData.getScheduleJobDate().equals(today) && !userLoginData.getTodayJobScheduled()) {
            mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            // Get Today Calendar
            Calendar date = new GregorianCalendar();
            // Reset Minute, Second, and Millisecond
            date.set(Calendar.MINUTE, 0);
            date.set(Calendar.SECOND, 0);
            date.set(Calendar.MILLISECOND, 0);
            // Current Hour
            int currentHour = date.get(Calendar.HOUR_OF_DAY);
            Log.d("<RE>", "Current Hour: " + currentHour);
            if(currentHour < 8) {
                // Breakfast Time (08:00 AM)
                Log.d("<RE>", "Add Breakfast Reminder");
                date.set(Calendar.HOUR_OF_DAY, 8);
                scheduleJob(date.getTimeInMillis());
            }
            if(currentHour < 12) {
                // Lunch Time (12:00 PM)
                Log.d("<RE>", "Add Lunch Reminder");
                date.set(Calendar.HOUR_OF_DAY, 12);
                scheduleJob(date.getTimeInMillis());
            }
            if(currentHour < 18) {
                // Dinner Time (18:00 PM)
                Log.d("<RE>", "Add Dinner Reminder");
                date.set(Calendar.HOUR_OF_DAY, 18);
                scheduleJob(date.getTimeInMillis());
            }
            userLoginData.setTodayJobScheduled(true);
        }
    }

    private AlarmManager mAlarmManager;
    private PendingIntent alarmIntent;

    @SuppressLint("UnspecifiedImmutableFlag")
    private void scheduleJob(long timeMillis) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !mAlarmManager.canScheduleExactAlarms()) {
            Log.d("<RE>", "scheduleJob: Can't Schedule Exact Alarm");
            return;
        }
        Intent intent = new Intent(getApplicationContext(), BroadcastService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmIntent = PendingIntent.getBroadcast(this, (int) timeMillis, intent, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_CANCEL_CURRENT);
        }
        else {
            alarmIntent = PendingIntent.getBroadcast(this, (int) timeMillis, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        }
        mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, timeMillis, alarmIntent);
        Log.d("<RE>", "scheduleJob: Alarm Set Done!");
    }

}