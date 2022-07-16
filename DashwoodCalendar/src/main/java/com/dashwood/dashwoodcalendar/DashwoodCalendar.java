package com.dashwood.dashwoodcalendar;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;


import com.dashwood.dashwoodcalendar.adapter.AdapterViewPager;
import com.dashwood.dashwoodcalendar.fragment.CalendarFragment;
import com.dashwood.dashwoodcalendar.handler.DateConverter;
import com.dashwood.dashwoodcalendar.handler.HandlerCheckerValue;
import com.dashwood.dashwoodcalendar.handler.HandlerReturnValues;
import com.dashwood.dashwoodcalendar.inf.InformationDisableDay;
import com.dashwood.dashwoodcalendar.listener.OnClickCalendarListener;

import java.util.ArrayList;

public class DashwoodCalendar {
    private final ArrayList<CalendarFragment> calendarFragments = new ArrayList<>();
    private ArrayList<InformationDisableDay> informationDisableDays = new ArrayList<>();
    private AdapterViewPager adapterViewPager;
    private AppCompatActivity activity;
    private FragmentActivity fragmentActivity;
    private final int minYear;
    private final int maxYear;
    private String language;
    private int backgroundNowDay, backgroundWeekEndDay, backgroundEnableDay, backgroundDisableDay, backgroundWeekName,
            backgroundBtnMonth, backgroundBtnYear, backgroundBtnToday, backgroundColorRootOptionCalendar;
    private int textEnableDayColor, textDisableDayColor, textWeekNameColor, textWeekEndColor,
            dayWidth, dayHeight, textNowColor, textColorBtnMonth, textColorBtnYear, textColorBtnToday;
    private int textSizeDay, textSizeNowDay, textSizeWeekName, textSizeWeekEnd;
    private int backgroundMonthAndYear, textSizeMonthAndYear, textColorMonthAndYear,
            radiusMonthAndYear;
    private OnClickCalendarListener onClickCalendarListener;
    public static final String PERSIAN_LANGUAGE = "fa";
    public static final String ENGLISH_LANGUAGE = "en";
    public  static final int THIS_YEAR_GREGORIAN = HandlerReturnValues.getNowYear();
    public  static final int THIS_YEAR_PERSIAN = HandlerReturnValues.getNowYearPersian();
    private int currentYear, currentMonth;
    private final ViewPager2 viewPager;
    private boolean disableWeekEnd = false;
    private int radius;


    public DashwoodCalendar(FragmentActivity activity, ViewPager2 viewPager, int minYear, int maxYear, String language) {
        this.fragmentActivity = activity;
        this.minYear = minYear;
        this.maxYear = maxYear;
        this.viewPager = viewPager;
        this.language = language;
        adapterViewPager = new AdapterViewPager(activity);
    }

    public DashwoodCalendar(AppCompatActivity activity, ViewPager2 viewPager, int minYear, int maxYear, String language) {
        this.activity = activity;
        this.minYear = minYear;
        this.maxYear = maxYear;
        this.viewPager = viewPager;
        this.language = language;
        adapterViewPager = new AdapterViewPager(activity);
    }


    public DashwoodCalendar setBackgroundNowDay(int drawable) {
        backgroundNowDay = drawable;
        return this;
    }

    public DashwoodCalendar setBackgroundWeekEndDay(int drawable) {
        backgroundWeekEndDay = drawable;
        return this;
    }

    public DashwoodCalendar setBackgroundEnableDay(int drawable) {
        backgroundEnableDay = drawable;
        return this;
    }

    public DashwoodCalendar setBackgroundDisableDay(int drawable) {
        backgroundDisableDay = drawable;
        return this;
    }

    public DashwoodCalendar setBackgroundWeekName(int drawable) {
        this.backgroundWeekName = drawable;
        return this;
    }

    public DashwoodCalendar setTextWeekNameColor(int color) {
        textWeekNameColor = color;
        return this;
    }

    public DashwoodCalendar setTextWeekEndColor(int color) {
        textWeekEndColor = color;
        return this;
    }

    public DashwoodCalendar setTextEnableDayColor(int color) {
        textEnableDayColor = color;
        return this;
    }

    public DashwoodCalendar setTextNowColor(int color) {
        this.textNowColor = color;
        return this;
    }

    public DashwoodCalendar setTextDisableDayColor(int color) {
        this.textDisableDayColor = color;
        return this;
    }

    public DashwoodCalendar setDayWidth(int dayWidth) {
        this.dayWidth = dayWidth;
        return this;
    }

    public DashwoodCalendar setDayHeight(int dayHeight) {
        this.dayHeight = dayHeight;
        return this;
    }

    public DashwoodCalendar setDisableWeekEnd(boolean fridayDisable) {
        this.disableWeekEnd = fridayDisable;
        return this;
    }

    public DashwoodCalendar setBackgroundBtnYear(int backgroundBtnYear) {
        this.backgroundBtnYear = backgroundBtnYear;
        return this;
    }

    public DashwoodCalendar setBackgroundBtnToday(int backgroundBtnToday) {
        this.backgroundBtnToday = backgroundBtnToday;
        return this;
    }

    public DashwoodCalendar setBackgroundBtnMonth(int backgroundBtnMonth) {
        this.backgroundBtnMonth = backgroundBtnMonth;
        return this;
    }

    public DashwoodCalendar setBackgroundColorRootOptionCalendar(int backgroundColorRootOptionCalendar) {
        this.backgroundColorRootOptionCalendar = backgroundColorRootOptionCalendar;
        return this;
    }

    public DashwoodCalendar setTextColorBtnToday(int textColorBtnToday) {
        this.textColorBtnToday = textColorBtnToday;
        return this;
    }

    public DashwoodCalendar setTextColorBtnYear(int textColorBtnYear) {
        this.textColorBtnYear = textColorBtnYear;
        return this;
    }

    public DashwoodCalendar setTextColorBtnMonth(int textColorBtnMonth) {
        this.textColorBtnMonth = textColorBtnMonth;
        return this;
    }

    public DashwoodCalendar setOnClickCalendarListener(OnClickCalendarListener onClickCalendarListener) {
        this.onClickCalendarListener = onClickCalendarListener;
        return this;
    }

    public DashwoodCalendar setInformationDisableDays(ArrayList<InformationDisableDay> informationDisableDays) {
        this.informationDisableDays = informationDisableDays;
        return this;
    }

    public DashwoodCalendar setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    public DashwoodCalendar setTextColorMonthAndYear(int textColorMonthAndYear) {
        this.textColorMonthAndYear = textColorMonthAndYear;
        return this;
    }

    public DashwoodCalendar setTextSizeMonthAndYear(int textSizeMonthAndYear) {
        this.textSizeMonthAndYear = textSizeMonthAndYear;
        return this;
    }

    public DashwoodCalendar setTextSizeDay(int textSizeDay) {
        this.textSizeDay = textSizeDay;
        return this;
    }

    public DashwoodCalendar setTextSizeWeekName(int textSizeWeekName) {
        this.textSizeWeekName = textSizeWeekName;
        return this;
    }

    public DashwoodCalendar setTextSizeWeekEnd(int textSizeWeekEnd) {
        this.textSizeWeekEnd = textSizeWeekEnd;
        return this;
    }

    public DashwoodCalendar setTextSizeNowDay(int textSizeNowDay) {
        this.textSizeNowDay = textSizeNowDay;
        return this;
    }

    public DashwoodCalendar setBackgroundMonthAndYear(int backgroundMonthAndYear) {
        this.backgroundMonthAndYear = backgroundMonthAndYear;
        return this;
    }

    public DashwoodCalendar setRadiusMonthAndYear(int radiusMonthAndYear) {
        this.radiusMonthAndYear = radiusMonthAndYear;
        return this;
    }

    public void init() {
        if (fragmentActivity == null) {
            initActivity();
            return;
        }
        initFragment();
    }

    private void initFragment() {
        adapterViewPager.sendCalendar(calendarFragments);
        viewPager.setAdapter(adapterViewPager);
        setCurrentYearAndMonth();
        if (language.equals(ENGLISH_LANGUAGE)) {
            initCalendarFragmentEnglish();
        } else {
            initCalendarFragmentPersian();
        }
    }

    private void initActivity() {
        adapterViewPager.sendCalendar(calendarFragments);
        viewPager.setAdapter(adapterViewPager);
        setCurrentYearAndMonth();
        if (language.equals(ENGLISH_LANGUAGE)) {
            initCalendarFragmentEnglish();
        } else {
            initCalendarFragmentPersian();
        }
    }

    private void initCalendarFragmentEnglish() {
        int counter = 0;
        for (int i = minYear; i <= maxYear; i++) {
            for (int j = 0; j < 12; j++) {
                calendarFragments.add(new CalendarFragment(i, j, 1, minYear, maxYear, language).
                        setOnClickCalendarListener(onClickCalendarListener)
                        .setDayHeight(dayHeight)
                        .setDayWidth(dayWidth)
                        .setInformationDisableDays(informationDisableDays)
                        .setBackgroundWeekName(backgroundWeekName)
                        .setBackgroundDisableDay(backgroundDisableDay)
                        .setBackgroundEnableDay(backgroundEnableDay)
                        .setBackgroundNowDay(backgroundNowDay)
                        .setBackgroundWeekEndDay(backgroundWeekEndDay)
                        .setBackgroundBtnMonth(backgroundBtnMonth)
                        .setBackgroundBtnToday(backgroundBtnToday)
                        .setBackgroundBtnYear(backgroundBtnYear)
                        .setBackgroundColorRootOptionCalendar(backgroundColorRootOptionCalendar)
                        .setTextWeekEndColor(textWeekEndColor)
                        .setTextNowColor(textNowColor)
                        .setTextWeekNameColor(textWeekNameColor)
                        .setTextDisableDayColor(textDisableDayColor)
                        .setDisableFriday(disableWeekEnd)
                        .setTextColorBtnMonth(textColorBtnMonth)
                        .setTextColorBtnYear(textColorBtnYear)
                        .setTextColorBtnToday(textColorBtnToday)
                        .setRadius(radius)
                        .setTextSizeDay(textSizeDay)
                        .setTextSizeNowDay(textSizeNowDay)
                        .setTextSizeWeekEnd(textSizeWeekEnd)
                        .setTextSizeWeekName(textSizeWeekName)
                        .setTextSizeMonthAndYear(textSizeMonthAndYear)
                        .setTextColorMonthAndYear(textColorMonthAndYear)
                        .setBackgroundMonthAndYear(backgroundMonthAndYear)
                        .setRadiusMonthAndYear(radiusMonthAndYear)
                        .setOnMonthAndYearClickListener((position, monthNameOrYear, checkMonthClicked) -> {
                            if (HandlerCheckerValue.checkEmptyOrNullValue(monthNameOrYear)) {
                                setGoToCurrent();
                                return;
                            }
                            viewPager.setUserInputEnabled(true);
                            if (checkMonthClicked) {
                                setGoToMonthSelected(position);
                                return;
                            }
                            setGoToYearSelected(Integer.parseInt(monthNameOrYear));
                        })
                        .setTextEnableDayColor(textEnableDayColor)
                        .setOnViewpagerDisableEnableScrolling(viewPager::setUserInputEnabled));
                if (i <= currentYear) {
                    if (i == currentYear && j >= currentMonth) {
                        continue;
                    }
                    counter++;
                }
            }
        }
        viewPager.setCurrentItem(counter, false);

    }


    private void initCalendarFragmentPersian() {
        int counter = 0;
        for (int i = minYear; i <= maxYear; i++) {
            for (int j = 1; j < 13; j++) {
                calendarFragments.add(new CalendarFragment(i, j, 1, minYear, maxYear, language).
                        setOnClickCalendarListener(onClickCalendarListener)
                        .setDayHeight(dayHeight)
                        .setDayWidth(dayWidth)
                        .setInformationDisableDays(informationDisableDays)
                        .setBackgroundWeekName(backgroundWeekName)
                        .setBackgroundDisableDay(backgroundDisableDay)
                        .setBackgroundEnableDay(backgroundEnableDay)
                        .setBackgroundNowDay(backgroundNowDay)
                        .setBackgroundWeekEndDay(backgroundWeekEndDay)
                        .setBackgroundBtnMonth(backgroundBtnMonth)
                        .setBackgroundBtnToday(backgroundBtnToday)
                        .setBackgroundBtnYear(backgroundBtnYear)
                        .setBackgroundColorRootOptionCalendar(backgroundColorRootOptionCalendar)
                        .setTextWeekEndColor(textWeekEndColor)
                        .setTextNowColor(textNowColor)
                        .setTextWeekNameColor(textWeekNameColor)
                        .setTextDisableDayColor(textDisableDayColor)
                        .setDisableFriday(disableWeekEnd)
                        .setTextColorBtnMonth(textColorBtnMonth)
                        .setTextColorBtnYear(textColorBtnYear)
                        .setTextColorBtnToday(textColorBtnToday)
                        .setRadius(radius)
                        .setTextSizeDay(textSizeDay)
                        .setTextSizeNowDay(textSizeNowDay)
                        .setTextSizeWeekEnd(textSizeWeekEnd)
                        .setTextSizeWeekName(textSizeWeekName)
                        .setTextSizeMonthAndYear(textSizeMonthAndYear)
                        .setTextColorMonthAndYear(textColorMonthAndYear)
                        .setBackgroundMonthAndYear(backgroundMonthAndYear)
                        .setRadiusMonthAndYear(radiusMonthAndYear)
                        .setOnMonthAndYearClickListener((position, monthNameOrYear, checkMonthClicked) -> {
                            if (HandlerCheckerValue.checkEmptyOrNullValue(monthNameOrYear)) {
                                setGoToCurrent();
                                return;
                            }
                            viewPager.setUserInputEnabled(true);
                            if (checkMonthClicked) {
                                setGoToMonthSelected(position);
                                return;
                            }
                            setGoToYearSelected(Integer.parseInt(monthNameOrYear));
                        })
                        .setTextEnableDayColor(textEnableDayColor)
                        .setOnViewpagerDisableEnableScrolling(viewPager::setUserInputEnabled));
                if (i <= currentYear) {
                    if (i == currentYear && j >= currentMonth) {
                        continue;
                    }
                    counter++;
                }
            }
        }
        viewPager.setCurrentItem(counter, false);
    }


    private void setCurrentYearAndMonth() {
        String nowDate = HandlerReturnValues.getNowDate();
        if (HandlerCheckerValue.checkEmptyOrNullValue(language)) {
            language = PERSIAN_LANGUAGE;
        }
        if (ENGLISH_LANGUAGE.equals(language)) {
            String[] nowDates = nowDate.split("-");
            currentYear = Integer.parseInt(nowDates[0]);
            currentMonth = Integer.parseInt(nowDates[1]) - 1;
            return;
        }
        String[] persianDate = DateConverter.gregorianToPersian(nowDate).split("-");
        currentMonth = Integer.parseInt(persianDate[1]);
        currentYear = Integer.parseInt(persianDate[0]);

    }

    private void setGoToMonthSelected(int month) {
        int counter = 0;
        for (CalendarFragment calendarFragment : calendarFragments) {
            if (calendarFragment.getBinding() != null) {
                calendarFragment.getBinding().recItemCalendar.setVisibility(View.VISIBLE);
                calendarFragment.getBinding().recItemMonthAndYear.setVisibility(View.GONE);
            }
            if (calendarFragment.getYear() != currentYear) {
                counter++;
                continue;
            }
            if (month == calendarFragment.getMonth()) {
                viewPager.setCurrentItem(counter, false);
                return;
            }
            counter++;
        }
    }

    private void setGoToYearSelected(int year) {
        int counter = 0;
        for (CalendarFragment calendarFragment : calendarFragments) {
            if (year == calendarFragment.getYear()) {
                if (calendarFragment.getBinding() != null) {
                    calendarFragment.getBinding().recItemCalendar.setVisibility(View.VISIBLE);
                    calendarFragment.getBinding().recItemMonthAndYear.setVisibility(View.GONE);
                }
                currentYear = year;
                viewPager.setCurrentItem(counter, false);
                return;
            }
            counter++;
        }
    }

    private void setGoToCurrent() {
        if (language.equals(ENGLISH_LANGUAGE)) {
            currentYear = Integer.parseInt(HandlerReturnValues.getNowDate().split("-")[0]);
            currentMonth = Integer.parseInt(HandlerReturnValues.getNowDate().split("-")[1]) - 1;
        } else {
            String nowPersianDate = DateConverter.gregorianToPersian(HandlerReturnValues.getNowDate());
            currentYear = Integer.parseInt(nowPersianDate.split("-")[0]);
            currentMonth = Integer.parseInt(nowPersianDate.split("-")[1]);
        }
        int counter = 0;
        for (CalendarFragment calendarFragment : calendarFragments) {
            if (calendarFragment.getYear() != currentYear) {
                counter++;
                continue;
            }
            if (currentMonth == calendarFragment.getMonth()) {
                if (calendarFragment.getBinding() != null) {
                    calendarFragment.getBinding().recItemCalendar.setVisibility(View.VISIBLE);
                    calendarFragment.getBinding().recItemMonthAndYear.setVisibility(View.GONE);
                }
                viewPager.setCurrentItem(counter, false);
                return;
            }
            counter++;
        }
    }
}
