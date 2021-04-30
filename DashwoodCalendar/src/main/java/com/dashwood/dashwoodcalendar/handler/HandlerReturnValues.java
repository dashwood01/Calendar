package com.dashwood.dashwoodcalendar.handler;


import com.dashwood.dashwoodcalendar.DashwoodCalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class HandlerReturnValues {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    public static String getNowDate() {
        return simpleDateFormat.format(new Date());
    }

    public static int getNowYear() {
        return Integer.parseInt(simpleDateFormat.format(new Date()).split("-")[0]);
    }

    public static int getNowYearPersian() {
        return Integer.parseInt(DateConverter.gregorianToPersian(simpleDateFormat.format(new Date())).split("-")[0]);
    }


    public static boolean checkDateCompare(String date) {
        try {
            Date dateOne = simpleDateFormat.parse(date);
            Date dateTwo = simpleDateFormat.parse(getNowDate());
            return Objects.requireNonNull(dateOne).compareTo(dateTwo) == 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkTwoDateCompare(String date1, String date2) {
        try {
            Date dateOne = simpleDateFormat.parse(date1);
            Date dateTwo = simpleDateFormat.parse(date2);
            return Objects.requireNonNull(dateOne).compareTo(dateTwo) == 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getCurrentYear(String language) {
        if (language.equals(DashwoodCalendar.ENGLISH_LANGUAGE)) {
            return Integer.parseInt(getNowDate().split("-")[0]);
        }
        return Integer.parseInt(DateConverter.gregorianToPersian(getNowDate()).split("-")[0]);
    }
}
