package com.dashwood.calendar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.dashwood.calendar.databinding.ActivityMainBinding;
import com.dashwood.dashwoodcalendar.DashwoodCalendar;
import com.dashwood.dashwoodcalendar.inf.InformationDisableDay;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        // You can use calendar on Dialog Fragment
        activityMainBinding.btnDialogFragmentCalendar.setOnClickListener(v -> {
            DialogCalendarFragment dialogCalendarFragment = new DialogCalendarFragment();
            dialogCalendarFragment.show(getSupportFragmentManager(), "calendar");
        });


        // Also you can use calendar on alert dialog
        activityMainBinding.btnAlertDialogCalendar.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.fragment_dialog_calendar, null, false);
            ViewPager2 viewPager = view.findViewById(R.id.viewPager);
            DashwoodCalendar dashwoodCalendar = new DashwoodCalendar(this,
                    viewPager, 1300, 1500, DashwoodCalendar.PERSIAN_LANGUAGE);
            dashwoodCalendar.setOnClickCalendarListener((day, month, year, monthName, dayOfWeek, dayOfWeekNumber, fullDateWithMonthString, fullDate, gregorianDate) -> {
                Toast.makeText(this, "Day : " + day + "\nMonth : " + month + "\nyear : " + year + "\nmonthName : " + monthName + "\nday of week : " + dayOfWeek + "\nday of week number : " + dayOfWeekNumber +
                        "\nfull date with month string : " + fullDateWithMonthString + "\nfull date : " + fullDate + "\ngregorian date : " + gregorianDate, Toast.LENGTH_LONG).show();
            });
            dashwoodCalendar.init();
            builder.setView(view);
            builder.create().show();
        });


        // And can use in activity or fragment
        DashwoodCalendar dashwoodCalendar = new DashwoodCalendar(this,
                activityMainBinding.viewPager, 1900, DashwoodCalendar.THIS_YEAR_PERSIAN, DashwoodCalendar.ENGLISH_LANGUAGE);
        dashwoodCalendar.setOnClickCalendarListener((day, month, year, monthName, dayOfWeek, dayOfWeekNumber, fullDateWithMonthString, fullDate, gregorianDate) -> {
            Toast.makeText(this, "Day : " + day + "\nMonth : " + month + "\nyear : " + year + "\nmonthName : " + monthName + "\nday of week : " + dayOfWeek + "\nday of week number : " + dayOfWeekNumber +
                    "\nfull date with month string : " + fullDateWithMonthString + "\nfull date : " + fullDate + "\ngregorian date : " + gregorianDate, Toast.LENGTH_LONG).show();
        });

        // Simple code for disable day
        ArrayList<InformationDisableDay> informationDisableDays = new ArrayList<>();
        String[] dates = {"2021-05-17", "2021-05-18", "2021-05-19"};
        for (String date : dates) {
            InformationDisableDay informationDisableDay = new InformationDisableDay();
            informationDisableDay.setDate(date);
            informationDisableDays.add(informationDisableDay);
        }
        dashwoodCalendar.setInformationDisableDays(informationDisableDays);
        dashwoodCalendar.init();

    }
}