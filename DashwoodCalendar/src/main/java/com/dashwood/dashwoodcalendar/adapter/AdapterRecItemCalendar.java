package com.dashwood.dashwoodcalendar.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.dashwood.dashwoodcalendar.DashwoodCalendar;
import com.dashwood.dashwoodcalendar.R;
import com.dashwood.dashwoodcalendar.handler.HandlerReturnValues;
import com.dashwood.dashwoodcalendar.inf.InformationCalendar;
import com.dashwood.dashwoodcalendar.listener.OnClickCalendarListener;

import java.util.ArrayList;

public class AdapterRecItemCalendar extends RecyclerView.Adapter<AdapterRecItemCalendar.ViewHolder> {
    private ArrayList<InformationCalendar> informationCalendars = new ArrayList<>();
    private OnClickCalendarListener onClickCalendarListener;
    private int backgroundNowDay, backgroundWeekEndDay, backgroundEnableDay, backgroundDisableDay,
            textEnableDayColor, textDisableDayColor, textWeekNameColor, textWeekEndColor,
            dayWidth, dayHeight, textNowColor, backgroundWeekName;
    private int textSizeWeekName, textSizeDay, textSizeWeekEnd, textSizeNowDay;
    private final Activity context;
    private final String language;
    private boolean disableFriday = false;
    private Typeface typeface;
    private int radius;

    public AdapterRecItemCalendar(Activity context, String language) {
        this.context = context;
        this.language = language;
    }


    public void setOnClickCalendarListener(OnClickCalendarListener onClickCalendarListener) {
        this.onClickCalendarListener = onClickCalendarListener;
    }

    public void setBackgroundNowDay(int backgroundNowDay) {
        this.backgroundNowDay = backgroundNowDay;
    }

    public void setBackgroundDisableDay(int backgroundDisableDay) {
        this.backgroundDisableDay = backgroundDisableDay;
    }

    public void setBackgroundWeekEndDay(int backgroundWeekEndDay) {
        this.backgroundWeekEndDay = backgroundWeekEndDay;
    }

    public void setBackgroundEnableDay(int backgroundEnableDay) {
        this.backgroundEnableDay = backgroundEnableDay;
    }

    public void setBackgroundWeekName(int backgroundWeekName) {
        this.backgroundWeekName = backgroundWeekName;
    }

    public void setTextWeekNameColor(int textWeekNameColor) {
        this.textWeekNameColor = textWeekNameColor;
    }

    public void setTextEnableDayColor(int textEnableDayColor) {
        this.textEnableDayColor = textEnableDayColor;
    }

    public void setTextDisableDayColor(int textDisableDayColor) {
        this.textDisableDayColor = textDisableDayColor;
    }

    public void setTextNowColor(int textNowColor) {
        this.textNowColor = textNowColor;
    }

    public void setTextWeekEndColor(int textWeekEndColor) {
        this.textWeekEndColor = textWeekEndColor;
    }

    public void setDayHeight(int dayHeight) {
        this.dayHeight = dayHeight;
    }

    public void setDayWidth(int dayWidth) {
        this.dayWidth = dayWidth;
    }

    public void setDisableFriday(boolean disableFriday) {
        this.disableFriday = disableFriday;
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setTextSizeDay(int textSizeDay) {
        this.textSizeDay = textSizeDay;
    }

    public void setTextSizeNowDay(int textSizeNowDay) {
        this.textSizeNowDay = textSizeNowDay;
    }

    public void setTextSizeWeekEnd(int textSizeWeekEnd) {
        this.textSizeWeekEnd = textSizeWeekEnd;
    }

    public void setTextSizeWeekName(int textSizeWeekName) {
        this.textSizeWeekName = textSizeWeekName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_item_calendar,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InformationCalendar informationCalendar = informationCalendars.get(position);
        if (language.equals(DashwoodCalendar.ENGLISH_LANGUAGE)) {
            holder.btnDay.setTypeface(Typeface.DEFAULT_BOLD);
        }
        if (typeface != null) {
            holder.btnDay.setTypeface(typeface);
        }
        if (!informationCalendar.getStatus()) {
            holder.btnDay.setEnabled(false);
            holder.btnDay.setBackground(null);
            holder.btnDay.setText(informationCalendar.getDay());
            holder.cardView.setBackground(null);
            if (position <= 6) {
                if (textWeekNameColor != 0) {
                    holder.btnDay.setTextColor(ContextCompat.getColor(context, textWeekNameColor));
                }
                if (backgroundWeekName != 0) {
                    holder.btnDay.setBackground(ContextCompat.getDrawable(context, backgroundWeekName));
                }
                if (textSizeWeekName != 0) {
                    holder.btnDay.setTextSize(textSizeWeekName);
                }
            }
            return;
        }
        holder.btnDay.setText(informationCalendar.getDay());
        if (textSizeDay != 0) {
            holder.btnDay.setTextSize(textSizeDay);
        }
        holder.btnDay.setOnClickListener(v -> onClickCalendarListener.onClickCalendarListener(informationCalendar.getDay(),
                informationCalendar.getMonth(), informationCalendar.getYear(), informationCalendar.getNameOfMonth(),
                informationCalendar.getDayOfWeek(), informationCalendar.getDayOfWeekNumber(),
                informationCalendar.getFullDateWithMonthString(), informationCalendar.getFullDate(),
                informationCalendar.getGregorianDate()));
        if (informationCalendar.getDisableDay()) {
            holder.btnDay.setBackground(ContextCompat.getDrawable(context, R.drawable.background_button_gray));
            holder.btnDay.setEnabled(false);
            if (backgroundDisableDay != 0) {
                holder.btnDay.setBackground(ContextCompat.getDrawable(context, backgroundDisableDay));
            }
            if (textDisableDayColor != 0) {
                holder.btnDay.setTextColor(ContextCompat.getColor(context, textDisableDayColor));
            }
        } else {
            holder.btnDay.setBackground(ContextCompat.getDrawable(context, R.drawable.background_button_green));
            holder.btnDay.setEnabled(true);
            if (backgroundEnableDay != 0) {
                holder.btnDay.setBackground(ContextCompat.getDrawable(context, backgroundEnableDay));
            }
            if (textEnableDayColor != 0) {
                holder.btnDay.setTextColor(ContextCompat.getColor(context, textEnableDayColor));
            }
        }
        if (checkWeekEndDate(informationCalendar.getDayOfWeek())) {
            holder.btnDay.setBackground(ContextCompat.getDrawable(context, R.drawable.background_button_gray));
            if (textSizeWeekEnd != 0) {
                holder.btnDay.setTextSize(textSizeWeekName);
            }
            if (backgroundWeekEndDay != 0) {
                holder.btnDay.setBackground(ContextCompat.getDrawable(context, backgroundWeekEndDay));
            }
            if (textWeekEndColor != 0) {
                holder.btnDay.setTextColor(ContextCompat.getColor(context, textWeekEndColor));
            }
            holder.btnDay.setEnabled(!disableFriday);
        }
        if (HandlerReturnValues.checkDateCompare(informationCalendar.getGregorianDate())) {
            holder.btnDay.setBackground(ContextCompat.getDrawable(context, R.drawable.background_button_dark_green));
            if (textSizeNowDay != 0) {
                holder.btnDay.setTextSize(textSizeNowDay);
            }
            if (backgroundNowDay != 0) {
                holder.btnDay.setBackground(ContextCompat.getDrawable(context,
                        backgroundNowDay));
            }
            if (textNowColor != 0) {
                holder.btnDay.setTextColor(ContextCompat.getColor(context, textNowColor));
            }
        }
        if (dayHeight != 0 && dayWidth != 0) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dayWidth, dayHeight);
            layoutParams.setMargins(5, 5, 5, 5);
            layoutParams.gravity = Gravity.END;
            holder.btnDay.setLayoutParams(layoutParams);
        }
        if (radius != 0) {
            holder.cardView.setRadius(radius);
        }

    }

    @Override
    public int getItemCount() {
        return informationCalendars.size();
    }

    private boolean checkWeekEndDate(String value) {
        if (language.equals(DashwoodCalendar.ENGLISH_LANGUAGE)) {
            return value.equals("Saturday") || value.equals("Sunday");
        }
        return value.equals("جمعه");
    }

    public void sendItems(ArrayList<InformationCalendar> list) {
        informationCalendars = list;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private Button btnDay;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnDay = itemView.findViewById(R.id.btnDay);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
