package com.dashwood.dashwoodcalendar.handler;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import ir.huri.jcal.JalaliCalendar;

public class DateConverter {

    public static String persianDateToGregorianDate(String persianDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd", Locale.US);
            Date date = format.parse(persianDate);
            if (date == null) {
                return "";
            }
            String persianFormatDate = format.format(date);
            String[] persianDates = persianFormatDate.split("-");
            JalaliCalendar jalaliCalendar = new JalaliCalendar(Integer.parseInt(persianDates[0]),
                    Integer.parseInt(persianDates[1]), Integer.parseInt(persianDates[2]));
            GregorianCalendar gregorianCalendar = jalaliCalendar.toGregorian();
            int day = gregorianCalendar.get(Calendar.DAY_OF_MONTH);
            String stringDay;
            if (day < 10) {
                stringDay = "0" + day;
            } else {
                stringDay = String.valueOf(day);
            }
            return stringDay + "-"
                    + (gregorianCalendar.get(Calendar.MONTH) + 1) + "-"
                    + gregorianCalendar.get(Calendar.YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String persianDateToGregorianDate(int year, int month, int day) {
        JalaliCalendar jalaliCalendar = new JalaliCalendar(year, month, day);
        GregorianCalendar gregorianCalendar = jalaliCalendar.toGregorian();
        int newDay = gregorianCalendar.get(Calendar.DAY_OF_MONTH);
        String stringDay;
        if (newDay < 10) {
            stringDay = "0" + newDay;
        } else {
            stringDay = String.valueOf(newDay);
        }
        return gregorianCalendar.get(Calendar.YEAR) + "-"
                + (gregorianCalendar.get(Calendar.MONTH) + 1) + "-"
                + stringDay;
    }


    public static String persianDateToGregorianDateFirstReturnYear(String persianDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd", Locale.US);
            Date date = format.parse(persianDate);
            if (date == null) {
                return "";
            }
            String persianFormatDate = format.format(date);
            String[] persianDates = persianFormatDate.split("-");
            JalaliCalendar jalaliCalendar = new JalaliCalendar(Integer.parseInt(persianDates[0]),
                    Integer.parseInt(persianDates[1]), Integer.parseInt(persianDates[2]));
            GregorianCalendar gregorianCalendar = jalaliCalendar.toGregorian();
            int day = gregorianCalendar.get(Calendar.DAY_OF_MONTH);
            String stringDay;
            if (day < 10) {
                stringDay = "0" + day;
            } else {
                stringDay = String.valueOf(day);
            }
            return gregorianCalendar.get(Calendar.YEAR) + "-" +
                    (gregorianCalendar.get(Calendar.MONTH) + 1) + "-" + stringDay;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String gregorianToPersianWithMonthStringName(String gregorianDateValue) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd", Locale.US);
            Date date = format.parse(gregorianDateValue);
            if (date == null) {
                return "";
            }
            String stringFormatData = format.format(date);
            String[] gregorianDate = stringFormatData.split("-");
            JalaliCalendar jalaliCalendar = new JalaliCalendar(new GregorianCalendar(Integer.parseInt(gregorianDate[0]),
                    Integer.parseInt(gregorianDate[1]) - 1, Integer.parseInt(gregorianDate[2])));
            int day = jalaliCalendar.getDay();
            String stringDay;
            if (day < 10) {
                stringDay = "0" + day;
            } else {
                stringDay = String.valueOf(day);
            }
            return stringDay + " "
                    + jalaliCalendar.getMonthString() + " " + jalaliCalendar.getYear();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String gregorianToPersian(String gregorianDateValue) {
        String[] dates = gregorianDateValue.split(" ");
        gregorianDateValue = dates[0];
        try {
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd", Locale.US);
            Date date = format.parse(gregorianDateValue);
            if (date == null) {
                return "";
            }
            String stringFormatData = format.format(date);
            String[] gregorianDate = stringFormatData.split("-");
            JalaliCalendar jalaliCalendar = new JalaliCalendar(new GregorianCalendar(Integer.parseInt(gregorianDate[0]),
                    Integer.parseInt(gregorianDate[1]) - 1, Integer.parseInt(gregorianDate[2])));
            int day = jalaliCalendar.getDay();
            String stringDay;
            if (day < 10) {
                stringDay = "0" + day;
            } else {
                stringDay = String.valueOf(day);
            }
            return jalaliCalendar.getYear() + "-" + jalaliCalendar.getMonth() + "-" + stringDay;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String gregorianToPersian(int year, int month, int day) {

        JalaliCalendar jalaliCalendar = new JalaliCalendar(new GregorianCalendar(year, month, day));
        int newDay = jalaliCalendar.getDay();
        String stringDay;
        if (newDay < 10) {
            stringDay = "0" + newDay;
        } else {
            stringDay = String.valueOf(newDay);
        }
        return jalaliCalendar.getYear() + "-" + jalaliCalendar.getMonth() + "-" + stringDay;
    }

    public static int getMonthLength(String gregorianDateValue) {
        String[] dates = gregorianDateValue.split(" ");
        gregorianDateValue = dates[0];
        try {
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd", Locale.US);
            Date date = format.parse(gregorianDateValue);
            if (date == null) {
                return 0;
            }
            String stringFormatData = format.format(date);
            String[] gregorianDate = stringFormatData.split("-");
            JalaliCalendar jalaliCalendar = new JalaliCalendar(new GregorianCalendar(Integer.parseInt(gregorianDate[0]),
                    Integer.parseInt(gregorianDate[1]) - 1, Integer.parseInt(gregorianDate[2])));
            return jalaliCalendar.getMonthLength();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getMonthStartWeekName(String gregorianDateValue) {
        String[] dates = gregorianDateValue.split(" ");
        gregorianDateValue = dates[0];
        try {
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd", Locale.US);
            Date date = format.parse(gregorianDateValue);
            if (date == null) {
                return "";
            }
            String stringFormatData = format.format(date);
            String[] gregorianDate = stringFormatData.split("-");
            JalaliCalendar jalaliCalendar = new JalaliCalendar(new GregorianCalendar(Integer.parseInt(gregorianDate[0]),
                    Integer.parseInt(gregorianDate[1]) - 1, Integer.parseInt(gregorianDate[2])));
            return jalaliCalendar.getDayOfWeekString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static JalaliCalendar getPersianDateJalali(String gregorianDateValue) {
        String[] dates = gregorianDateValue.split(" ");
        gregorianDateValue = dates[0];
        try {
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd", Locale.US);
            Date date = format.parse(gregorianDateValue);
            if (date == null) {
                return null;
            }
            String stringFormatData = format.format(date);
            String[] gregorianDate = stringFormatData.split("-");
            return new JalaliCalendar(new GregorianCalendar(Integer.parseInt(gregorianDate[0]),
                    Integer.parseInt(gregorianDate[1]) - 1, Integer.parseInt(gregorianDate[2])));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JalaliCalendar getPersianDateJalali(int year, int month, int day) {
        return new JalaliCalendar(year, month, day);
    }


}