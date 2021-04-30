package com.dashwood.calendar;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.dashwood.calendar.databinding.FragmentDialogCalendarBinding;
import com.dashwood.dashwoodcalendar.DashwoodCalendar;

public class DialogCalendarFragment extends DialogFragment {

    private FragmentDialogCalendarBinding fragmentDialogCalendarBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentDialogCalendarBinding = FragmentDialogCalendarBinding.inflate(inflater, container, false);
        return fragmentDialogCalendarBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DashwoodCalendar dashwoodCalendar = new DashwoodCalendar(requireActivity(),
                fragmentDialogCalendarBinding.viewPager, 1300, 1500, DashwoodCalendar.PERSIAN_LANGUAGE);
        dashwoodCalendar.setOnClickCalendarListener((day, month, year, monthName, dayOfWeek, dayOfWeekNumber, fullDateWithMonthString, fullDate, gregorianDate) -> {
            Toast.makeText(requireContext(), "Day : " + day + "\nMonth : " + month + "\nyear : " + year + "\nmonthName : " + monthName + "\nday of week : " + dayOfWeek + "\nday of week number : " + dayOfWeekNumber +
                    "\nfull date with month string : " + fullDateWithMonthString + "\nfull date : " + fullDate + "\ngregorian date : " + gregorianDate, Toast.LENGTH_LONG).show();
        });
        dashwoodCalendar.init();
    }

    // for full screen horizontal dialog
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(layoutParams);
    }
}
