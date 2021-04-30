package com.dashwood.dashwoodcalendar.listener;

public interface OnClickCalendarListener {
    void onClickCalendarListener(String day, int month, int year, String monthName, String dayOfWeek, int dayOfWeekNumber,
                                 String fullDateWithMonthString, String fullDate, String fullGregorianDate);
}
