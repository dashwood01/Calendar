package com.dashwood.dashwoodcalendar.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dashwood.dashwoodcalendar.DashwoodCalendar;
import com.dashwood.dashwoodcalendar.R;
import com.dashwood.dashwoodcalendar.listener.OnMonthAndYearClickListener;

import java.util.ArrayList;

public class AdapterRecItemMonthNameAndYear extends RecyclerView.Adapter<AdapterRecItemMonthNameAndYear.ViewHolder> {

    private ArrayList<String> monthNamesAndYears = new ArrayList<>();
    private OnMonthAndYearClickListener onMonthAndYearClickListener;
    private int backgroundMonthAndYear, textColorMonthAndYear, textSizeMonthAndYear,
            radiusMonthAndYear;
    private final boolean checkMonthClicked;
    private final String language;
    private Context context;

    public AdapterRecItemMonthNameAndYear(Context context, boolean checkMonthClicked, String language) {
        this.checkMonthClicked = checkMonthClicked;
        this.language = language;
        this.context = context;
    }

    public void setTextSizeMonthAndYear(int textSizeMonthAndYear) {
        this.textSizeMonthAndYear = textSizeMonthAndYear;
    }

    public void setTextColorMonthAndYear(int textColorMonthAndYear) {
        this.textColorMonthAndYear = textColorMonthAndYear;
    }

    public void setBackgroundMonthAndYear(int backgroundMonthAndYear) {
        this.backgroundMonthAndYear = backgroundMonthAndYear;
    }

    public void setRadiusMonthAndYear(int radiusMonthAndYear) {
        this.radiusMonthAndYear = radiusMonthAndYear;
    }

    public void setOnMonthAndYearClickListener(OnMonthAndYearClickListener onMonthAndYearClickListener) {
        this.onMonthAndYearClickListener = onMonthAndYearClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_item_month_name, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.btnYearAndMonth.setText(monthNamesAndYears.get(position));
        if (textSizeMonthAndYear != 0) {
            holder.btnYearAndMonth.setTextSize(textSizeMonthAndYear);
        }
        if (textColorMonthAndYear != 0) {
            holder.btnYearAndMonth.setTextColor(textColorMonthAndYear);
        }
        if (backgroundMonthAndYear != 0) {
            holder.btnYearAndMonth.setBackground(ContextCompat.getDrawable(context, backgroundMonthAndYear));
        }
        if (radiusMonthAndYear != 0) {
            holder.cardViewMonthAndYear.setRadius(radiusMonthAndYear);
        }

        if (language.equals(DashwoodCalendar.PERSIAN_LANGUAGE)) {
            holder.btnYearAndMonth.setOnClickListener(v -> onMonthAndYearClickListener.setOnYearOrMonthClickListener(position + 1, monthNamesAndYears.get(position), checkMonthClicked));
            return;
        }
        holder.btnYearAndMonth.setOnClickListener(v -> onMonthAndYearClickListener.setOnYearOrMonthClickListener(position, monthNamesAndYears.get(position), checkMonthClicked));
        holder.btnYearAndMonth.setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    public int getItemCount() {
        return monthNamesAndYears.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        holder.itemView.clearAnimation();
    }

    public void sendItems(ArrayList<String> list) {
        monthNamesAndYears = list;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final Button btnYearAndMonth;
        private final CardView cardViewMonthAndYear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnYearAndMonth = itemView.findViewById(R.id.btnMonthAndYear);
            cardViewMonthAndYear = itemView.findViewById(R.id.cardViewMonthAndYear);
        }
    }
}
