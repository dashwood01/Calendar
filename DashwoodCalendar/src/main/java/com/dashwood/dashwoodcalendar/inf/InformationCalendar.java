package com.dashwood.dashwoodcalendar.inf;

public class InformationCalendar {
    private int id, month, year, dayOfWeekNumber;
    private String day, nameOfMonth, dayOfWeek, fullDateWithMonthString, fullDate, gregorianDate;
    private boolean status, disableDay;

    public void setId(int id) {
        this.id = id;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setNameOfMonth(String nameOfMonth) {
        this.nameOfMonth = nameOfMonth;
    }


    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public String getDay() {
        return day;
    }

    public String getNameOfMonth() {
        return nameOfMonth;
    }

    public int getYear() {
        return year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getMonth() {
        return month;
    }

    public boolean getStatus() {
        return status;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeekNumber(int dayOfWeekNumber) {
        this.dayOfWeekNumber = dayOfWeekNumber;
    }

    public void setFullDate(String fullDate) {
        this.fullDate = fullDate;
    }

    public void setFullDateWithMonthString(String fullDateWithMonthString) {
        this.fullDateWithMonthString = fullDateWithMonthString;
    }

    public int getDayOfWeekNumber() {
        return dayOfWeekNumber;
    }

    public String getFullDate() {
        return fullDate;
    }

    public String getFullDateWithMonthString() {
        return fullDateWithMonthString;
    }

    public void setGregorianDate(String gregorianDate) {
        this.gregorianDate = gregorianDate;
    }

    public String getGregorianDate() {
        return gregorianDate;
    }

    public void setDisableDay(boolean disableDay) {
        this.disableDay = disableDay;
    }

    public boolean getDisableDay() {
        return disableDay;
    }
}
