package com.dashwood.dashwoodcalendar.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.dashwood.dashwoodcalendar.DashwoodCalendar;
import com.dashwood.dashwoodcalendar.R;
import com.dashwood.dashwoodcalendar.adapter.AdapterRecItemCalendar;
import com.dashwood.dashwoodcalendar.adapter.AdapterRecItemMonthNameAndYear;
import com.dashwood.dashwoodcalendar.databinding.FragmentCalendarBinding;
import com.dashwood.dashwoodcalendar.handler.DateConverter;
import com.dashwood.dashwoodcalendar.handler.HandlerReturnValues;
import com.dashwood.dashwoodcalendar.inf.InformationCalendar;
import com.dashwood.dashwoodcalendar.inf.InformationDisableDay;
import com.dashwood.dashwoodcalendar.listener.OnClickCalendarListener;
import com.dashwood.dashwoodcalendar.listener.OnMonthAndYearClickListener;
import com.dashwood.dashwoodcalendar.listener.OnViewpagerDisableEnableScrolling;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import ir.huri.jcal.JalaliCalendar;


public class CalendarFragment extends Fragment {
    private FragmentCalendarBinding binding;
    private int year, month, day;
    private String language;
    private int backgroundNowDay, backgroundWeekEndDay, backgroundEnableDay, backgroundDisableDay, backgroundWeekName,
            backgroundBtnMonth, backgroundBtnYear, backgroundBtnToday, backgroundColorRootOptionCalendar;
    private int textEnableDayColor, textDisableDayColor, textWeekNameColor, textWeekEndColor,
            dayWidth, dayHeight, textNowColor, textColorBtnMonth, textColorBtnYear, textColorBtnToday;
    private int textSizeDay, textSizeNowDay, textSizeWeekName, textSizeWeekEnd;
    private int backgroundMonthAndYear, textSizeMonthAndYear, textColorMonthAndYear,
            radiusMonthAndYear;
    private OnClickCalendarListener onClickCalendarListener;
    private ArrayList<InformationCalendar> informationCalendars = new ArrayList<>();
    private ArrayList<InformationDisableDay> informationDisableDays = new ArrayList<>();
    private AdapterRecItemCalendar adapterRecItemCalendar;
    private boolean disableFriday = false;
    private AdapterRecItemMonthNameAndYear adapterRecItemMonthNameAndYear;
    private OnMonthAndYearClickListener onMonthAndYearClickListener;
    private final int minYear, maxYear;
    private OnViewpagerDisableEnableScrolling onViewpagerDisableEnableScrolling;
    private SimpleDateFormat simpleFormatMonth = new SimpleDateFormat("MMMM", Locale.US);
    private SimpleDateFormat simpleFormatWeek = new SimpleDateFormat("EEEE", Locale.US);
    private SimpleDateFormat simpleFormatFullMonthString = new SimpleDateFormat("yyyy-MMMM-dd", Locale.US);
    private SimpleDateFormat simpleFormatFull = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private int radius;

    public CalendarFragment(int year, int month, int day, int minYear, int maxYear, String language) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.language = language;
        this.minYear = minYear;
        this.maxYear = maxYear;
    }

    public CalendarFragment setBackgroundNowDay(int drawable) {
        backgroundNowDay = drawable;
        return this;
    }


    public CalendarFragment setBackgroundWeekEndDay(int drawable) {
        backgroundWeekEndDay = drawable;
        return this;
    }

    public CalendarFragment setBackgroundEnableDay(int drawable) {
        backgroundEnableDay = drawable;
        return this;
    }


    public CalendarFragment setBackgroundDisableDay(int drawable) {
        backgroundDisableDay = drawable;
        return this;
    }

    public CalendarFragment setBackgroundWeekName(int drawable) {
        this.backgroundWeekName = drawable;
        return this;
    }

    public CalendarFragment setTextWeekNameColor(int color) {
        textWeekNameColor = color;
        return this;
    }

    public CalendarFragment setTextWeekEndColor(int color) {
        textWeekEndColor = color;
        return this;
    }

    public CalendarFragment setTextEnableDayColor(int color) {
        textEnableDayColor = color;
        return this;
    }

    public CalendarFragment setTextNowColor(int color) {
        this.textNowColor = color;
        return this;
    }

    public CalendarFragment setOnClickCalendarListener(OnClickCalendarListener onClickCalendarListener) {
        this.onClickCalendarListener = onClickCalendarListener;
        return this;
    }

    public CalendarFragment setDayWidth(int dayWidth) {
        this.dayWidth = dayWidth;
        return this;
    }

    public CalendarFragment setDayHeight(int dayHeight) {
        this.dayHeight = dayHeight;
        return this;
    }

    public CalendarFragment setDisableFriday(boolean disableFriday) {
        this.disableFriday = disableFriday;
        return this;
    }

    public CalendarFragment setTextDisableDayColor(int color) {
        this.textDisableDayColor = color;
        return this;
    }

    public CalendarFragment setInformationDisableDays(ArrayList<InformationDisableDay> informationDisableDays) {
        this.informationDisableDays = informationDisableDays;
        return this;
    }

    public CalendarFragment setOnMonthAndYearClickListener(OnMonthAndYearClickListener onMonthAndYearClickListener) {
        this.onMonthAndYearClickListener = onMonthAndYearClickListener;
        return this;
    }

    public CalendarFragment setOnViewpagerDisableEnableScrolling(OnViewpagerDisableEnableScrolling onViewpagerDisableEnableScrolling) {
        this.onViewpagerDisableEnableScrolling = onViewpagerDisableEnableScrolling;
        return this;
    }

    public CalendarFragment setBackgroundBtnMonth(int backgroundBtnMonth) {
        this.backgroundBtnMonth = backgroundBtnMonth;
        return this;
    }

    public CalendarFragment setBackgroundBtnToday(int backgroundBtnToday) {
        this.backgroundBtnToday = backgroundBtnToday;
        return this;
    }

    public CalendarFragment setBackgroundBtnYear(int backgroundBtnYear) {
        this.backgroundBtnYear = backgroundBtnYear;
        return this;
    }

    public CalendarFragment setBackgroundColorRootOptionCalendar(int backgroundColorRootOptionCalendar) {
        this.backgroundColorRootOptionCalendar = backgroundColorRootOptionCalendar;
        return this;
    }

    public CalendarFragment setTextColorBtnMonth(int textColorBtnMonth) {
        this.textColorBtnMonth = textColorBtnMonth;
        return this;
    }

    public CalendarFragment setTextColorBtnYear(int textColorBtnYear) {
        this.textColorBtnYear = textColorBtnYear;
        return this;
    }

    public CalendarFragment setTextColorBtnToday(int textColorBtnToday) {
        this.textColorBtnToday = textColorBtnToday;
        return this;
    }

    public CalendarFragment setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public FragmentCalendarBinding getBinding() {
        return binding;
    }

    public CalendarFragment setTextColorMonthAndYear(int textColorMonthAndYear) {
        this.textColorMonthAndYear = textColorMonthAndYear;
        return this;
    }

    public CalendarFragment setTextSizeDay(int textSizeDay) {
        this.textSizeDay = textSizeDay;
        return this;
    }

    public CalendarFragment setTextSizeMonthAndYear(int textSizeMonthAndYear) {
        this.textSizeMonthAndYear = textSizeMonthAndYear;
        return this;
    }

    public CalendarFragment setTextSizeNowDay(int textSizeNowDay) {
        this.textSizeNowDay = textSizeNowDay;
        return this;
    }

    public CalendarFragment setTextSizeWeekEnd(int textSizeWeekEnd) {
        this.textSizeWeekEnd = textSizeWeekEnd;
        return this;
    }

    public CalendarFragment setTextSizeWeekName(int textSizeWeekName) {
        this.textSizeWeekName = textSizeWeekName;
        return this;
    }

    public CalendarFragment setBackgroundMonthAndYear(int backgorundMonthAndYear) {
        this.backgroundMonthAndYear = backgorundMonthAndYear;
        return this;
    }

    public CalendarFragment setRadiusMonthAndYear(int radiusMonthAndYear) {
        this.radiusMonthAndYear = radiusMonthAndYear;
        return this;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setView();
        setViewBackground();
        setAction();
    }

    private void setView() {
        adapterRecItemCalendar = new AdapterRecItemCalendar(requireActivity(), language);
        binding.recItemCalendar.setLayoutManager(new GridLayoutManager(requireContext(), 7));
        binding.recItemCalendar.setAdapter(adapterRecItemCalendar);
        adapterRecItemCalendar.setOnClickCalendarListener(onClickCalendarListener);
        adapterRecItemCalendar.setDayWidth(dayWidth);
        adapterRecItemCalendar.setDayHeight(dayHeight);
        adapterRecItemCalendar.setBackgroundDisableDay(backgroundDisableDay);
        adapterRecItemCalendar.setBackgroundEnableDay(backgroundEnableDay);
        adapterRecItemCalendar.setBackgroundNowDay(backgroundNowDay);
        adapterRecItemCalendar.setBackgroundWeekEndDay(backgroundWeekEndDay);
        adapterRecItemCalendar.setBackgroundWeekName(backgroundWeekName);
        adapterRecItemCalendar.setTextEnableDayColor(textEnableDayColor);
        adapterRecItemCalendar.setTextWeekEndColor(textWeekEndColor);
        adapterRecItemCalendar.setTextNowColor(textNowColor);
        adapterRecItemCalendar.setTextDisableDayColor(textDisableDayColor);
        adapterRecItemCalendar.setTextWeekNameColor(textWeekNameColor);
        adapterRecItemCalendar.setDisableFriday(disableFriday);
        adapterRecItemCalendar.setRadius(radius);
        adapterRecItemCalendar.setTextSizeDay(textSizeDay);
        adapterRecItemCalendar.setTextSizeNowDay(textSizeNowDay);
        adapterRecItemCalendar.setTextSizeWeekName(textSizeWeekName);
        adapterRecItemCalendar.setTextSizeWeekName(textSizeWeekEnd);
        if (informationCalendars.size() > 0) {
            informationCalendars.clear();
        }
        if (language.equals(DashwoodCalendar.ENGLISH_LANGUAGE)) {
            binding.recItemCalendar.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            createCalendarEnglish();
            return;
        }
        binding.recItemCalendar.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        createCalendarPersian();

    }

    private void setViewBackground() {
        if (backgroundBtnMonth != 0) {
            binding.btnMonth.setBackground(ContextCompat.getDrawable(requireContext(), backgroundBtnMonth));
        }
        if (backgroundBtnYear != 0) {
            binding.btnYear.setBackground(ContextCompat.getDrawable(requireContext(), backgroundBtnYear));
        }
        if (backgroundBtnToday != 0) {
            binding.btnToday.setBackground(ContextCompat.getDrawable(requireContext(), backgroundBtnToday));
        }
        if (backgroundColorRootOptionCalendar != 0) {
            binding.rootLayoutOptionCalendar.setBackgroundColor(ContextCompat.getColor(requireContext(), backgroundColorRootOptionCalendar));
        }
        if (textColorBtnMonth != 0) {
            binding.btnMonth.setTextColor(ContextCompat.getColor(requireContext(), textColorBtnMonth));
        }
        if (textColorBtnYear != 0) {
            binding.btnYear.setTextColor(ContextCompat.getColor(requireContext(), textColorBtnYear));
        }
        if (textColorBtnToday != 0) {
            binding.btnToday.setTextColor(ContextCompat.getColor(requireContext(), textColorBtnToday));
        }
    }

    private void setAction() {
        binding.btnMonth.setOnClickListener(v -> {
            onViewpagerDisableEnableScrolling.onViewpagerDisableEnableScrolling(false);
            createRecyclerViewMonth();
        });
        binding.btnYear.setOnClickListener(v -> {
            onViewpagerDisableEnableScrolling.onViewpagerDisableEnableScrolling(false);
            createRecyclerViewYear();
        });
        binding.btnToday.setOnClickListener(v -> {
            onMonthAndYearClickListener.setOnYearOrMonthClickListener(0, "", false);
        });
    }

    private void createCalendarPersian() {
        JalaliCalendar defaultJalali = DateConverter.getPersianDateJalali(year, month, day);
        binding.btnYear.setText(String.valueOf(year));
        binding.btnMonth.setText(defaultJalali.getMonthString());
        String[] weekNameArray = getResources().getStringArray(R.array.week_name_persian);
        int monthLen = defaultJalali.getMonthLength() + defaultJalali.getDayOfWeek() + weekNameArray.length;
        for (int i = 0; i < monthLen; i++) {
            InformationCalendar informationCalendar = new InformationCalendar();
            if (i < weekNameArray.length) {
                informationCalendar.setDay(weekNameArray[i]);
                informationCalendar.setStatus(false);
                informationCalendars.add(informationCalendar);
                continue;
            }
            if (i < defaultJalali.getDayOfWeek() + weekNameArray.length) {
                informationCalendar.setDay("");
                informationCalendar.setStatus(false);
                informationCalendars.add(informationCalendar);
                continue;
            }
            informationCalendar.setDay(String.valueOf(i - (defaultJalali.getDayOfWeek() - 1) - (weekNameArray.length)));
            JalaliCalendar jalaliCalendar = DateConverter.getPersianDateJalali(defaultJalali.getYear(),
                    defaultJalali.getMonth(), Integer.parseInt(informationCalendar.getDay()));
            informationCalendar.setMonth(jalaliCalendar.getMonth());
            informationCalendar.setYear(jalaliCalendar.getYear());
            informationCalendar.setNameOfMonth(jalaliCalendar.getMonthString());
            informationCalendar.setDayOfWeek(jalaliCalendar.getDayOfWeekString());
            informationCalendar.setDayOfWeekNumber(jalaliCalendar.getDayOfWeek());
            informationCalendar.setFullDateWithMonthString(jalaliCalendar.getDayOfWeekDayMonthString());
            informationCalendar.setFullDate(getString(R.string.full_date_format,
                    String.valueOf(jalaliCalendar.getYear()),
                    String.valueOf(jalaliCalendar.getMonth()),
                    String.valueOf(jalaliCalendar.getDay())));
            informationCalendar.setGregorianDate(DateConverter.persianDateToGregorianDate(jalaliCalendar.getYear(),
                    jalaliCalendar.getMonth(), jalaliCalendar.getDay()));
            informationCalendar.setStatus(true);
            if (informationDisableDays.size() != 0) {
                informationCalendar.setDisableDay(checkDisableDay(informationCalendar.getGregorianDate()));
            } else {
                informationCalendar.setDisableDay(false);
            }
            informationCalendars.add(informationCalendar);
        }
        adapterRecItemCalendar.sendItems(informationCalendars);
        binding.recItemCalendar.scheduleLayoutAnimation();
    }

    private void createCalendarEnglish() {
        binding.btnToday.setText(getString(R.string.btn_text_today_english));
        binding.btnToday.setTypeface(Typeface.DEFAULT_BOLD);
        binding.btnYear.setTypeface(Typeface.DEFAULT_BOLD);
        binding.btnMonth.setTypeface(Typeface.DEFAULT_BOLD);
        Calendar calendar = new GregorianCalendar(year, month, day);
        binding.btnYear.setText(String.valueOf(year));
        binding.btnMonth.setText(simpleFormatMonth.format(calendar.getTime()));
        String[] weekNameArray = getResources().getStringArray(R.array.week_name_english);
        for (String s : weekNameArray) {
            InformationCalendar informationCalendar = new InformationCalendar();
            informationCalendar.setDay(s);
            informationCalendar.setStatus(false);
            informationCalendars.add(informationCalendar);
        }
        for (int i = 0; i < calendar.get(Calendar.DAY_OF_WEEK) - 1; i++) {
            InformationCalendar informationCalendar = new InformationCalendar();
            informationCalendar.setDay("");
            informationCalendar.setStatus(false);
            informationCalendars.add(informationCalendar);
        }
        for (int i = 1; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            InformationCalendar informationCalendar = new InformationCalendar();
            calendar.set(year, month, i);
            informationCalendar.setDay(String.valueOf(i));
            informationCalendar.setMonth(calendar.get(Calendar.MONTH) + 1);
            informationCalendar.setYear(calendar.get(Calendar.YEAR));
            informationCalendar.setNameOfMonth(simpleFormatMonth.format(calendar.getTime()));
            informationCalendar.setDayOfWeek(simpleFormatWeek.format(calendar.getTime()));
            informationCalendar.setDayOfWeekNumber(calendar.get(Calendar.DAY_OF_WEEK));
            informationCalendar.setFullDateWithMonthString(simpleFormatFullMonthString.format(calendar.getTime()));
            informationCalendar.setFullDate(simpleFormatFull.format(calendar.getTime()));
            informationCalendar.setGregorianDate(simpleFormatFull.format(calendar.getTime()));
            informationCalendar.setStatus(true);
            if (informationDisableDays.size() != 0) {
                informationCalendar.setDisableDay(checkDisableDay(informationCalendar.getGregorianDate()));
            } else {
                informationCalendar.setDisableDay(false);
            }
            informationCalendars.add(informationCalendar);
        }
        adapterRecItemCalendar.sendItems(informationCalendars);
        binding.recItemCalendar.scheduleLayoutAnimation();
    }

    private boolean checkDisableDay(String date) {
        for (InformationDisableDay informationDisableDay : informationDisableDays) {
            Log.i("LOG", date);
            Log.i("LOG", "DISABLE : " + informationDisableDay.getDate());
            if (!HandlerReturnValues.checkTwoDateCompare(informationDisableDay.getDate(), date)) {
                continue;
            }
            return true;
        }
        return false;
    }

    private void createRecyclerViewMonth() {
        adapterRecItemMonthNameAndYear = new AdapterRecItemMonthNameAndYear(requireContext(), true, language);
        adapterRecItemMonthNameAndYear.setTextColorMonthAndYear(textColorMonthAndYear);
        adapterRecItemMonthNameAndYear.setTextSizeMonthAndYear(textSizeMonthAndYear);
        adapterRecItemMonthNameAndYear.setBackgroundMonthAndYear(backgroundMonthAndYear);
        adapterRecItemMonthNameAndYear.setRadiusMonthAndYear(radiusMonthAndYear);
        binding.recItemMonthAndYear.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        binding.recItemMonthAndYear.setAdapter(adapterRecItemMonthNameAndYear);
        binding.recItemMonthAndYear.setVisibility(View.VISIBLE);
        if (language.equals(DashwoodCalendar.ENGLISH_LANGUAGE)) {
            binding.recItemMonthAndYear.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        } else {
            binding.recItemMonthAndYear.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        binding.recItemCalendar.setVisibility(View.GONE);
        adapterRecItemMonthNameAndYear.setOnMonthAndYearClickListener(onMonthAndYearClickListener);
        if (language.equals(DashwoodCalendar.ENGLISH_LANGUAGE)) {
            createMonthEnglish();
            return;
        }
        createMonthPersian();
    }

    private void createMonthPersian() {
        ArrayList<String> monthName = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.month_name_persian)));
        adapterRecItemMonthNameAndYear.sendItems(monthName);
        binding.recItemMonthAndYear.scheduleLayoutAnimation();
    }

    private void createMonthEnglish() {
        ArrayList<String> monthName = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.month_name_english)));
        adapterRecItemMonthNameAndYear.sendItems(monthName);
        binding.recItemMonthAndYear.scheduleLayoutAnimation();
    }

    private void createRecyclerViewYear() {
        adapterRecItemMonthNameAndYear = new AdapterRecItemMonthNameAndYear(requireContext(), false, language);
        binding.recItemMonthAndYear.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        binding.recItemMonthAndYear.setAdapter(adapterRecItemMonthNameAndYear);
        binding.recItemMonthAndYear.setVisibility(View.VISIBLE);
        if (language.equals(DashwoodCalendar.PERSIAN_LANGUAGE)) {
            binding.recItemMonthAndYear.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            binding.recItemMonthAndYear.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        binding.recItemCalendar.setVisibility(View.GONE);
        adapterRecItemMonthNameAndYear.setOnMonthAndYearClickListener(onMonthAndYearClickListener);
        ArrayList<String> years = new ArrayList<>();
        for (int i = minYear; i <= maxYear; i++) {
            years.add(String.valueOf(i));
        }
        adapterRecItemMonthNameAndYear.sendItems(years);
        binding.recItemCalendar.scheduleLayoutAnimation();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}