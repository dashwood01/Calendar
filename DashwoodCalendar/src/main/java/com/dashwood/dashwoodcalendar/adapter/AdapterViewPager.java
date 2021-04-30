package com.dashwood.dashwoodcalendar.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.dashwood.dashwoodcalendar.fragment.CalendarFragment;

import java.util.ArrayList;

public class AdapterViewPager extends FragmentStateAdapter {

    private ArrayList<CalendarFragment> calendarFragment;

    public AdapterViewPager(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    public void sendCalendar(ArrayList<CalendarFragment> calendarFragment){
        this.calendarFragment = calendarFragment;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return calendarFragment.get(position);
    }

    @Override
    public int getItemCount() {
        return calendarFragment.size();
    }
}
