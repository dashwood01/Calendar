package com.dashwood.calendar;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.compose.ui.platform.ComposeView;
import androidx.fragment.app.DialogFragment;

import com.dashwood.calendar.databinding.FragmentDialogCalendarBinding;
import com.dashwood.dashwoodcalendar.DashwoodCalendarKt;
import com.dashwood.dashwoodcalendar.handler.CalendarLanguage;
import com.dashwood.dashwoodcalendar.handler.DashwoodCalendarStyle;
import com.dashwood.dashwoodcalendar.handler.DashwoodYear;

public class DialogCalendarFragment extends DialogFragment {

    private FragmentDialogCalendarBinding fragmentDialogCalendarBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentDialogCalendarBinding = FragmentDialogCalendarBinding.inflate(inflater, container, false);
        return fragmentDialogCalendarBinding.getRoot();
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);

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
