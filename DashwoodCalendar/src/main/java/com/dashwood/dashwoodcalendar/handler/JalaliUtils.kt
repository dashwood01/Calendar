package com.dashwood.dashwoodcalendar.handler

import ir.huri.jcal.JalaliCalendar
import kotlinx.datetime.LocalDate
import java.util.Calendar

object JalaliUtils {

    fun todayGregorian(): LocalDate {
        val cal = Calendar.getInstance()
        val y = cal.get(Calendar.YEAR)
        val m = cal.get(Calendar.MONTH) + 1
        val d = cal.get(Calendar.DAY_OF_MONTH)
        return LocalDate(y, m, d)
    }

    /** Today in Jalali (Persian) as Triple<year, month, day> */
    fun todayJalali(): Triple<Int, Int, Int> {
        val g = todayGregorian()
        return gregorianToJalali(g)
    }

    fun gregorianToJalali(date: LocalDate): Triple<Int, Int, Int> {
        val gregStr = "%04d-%02d-%02d".format(date.year, date.monthNumber, date.dayOfMonth)
        val persian = DateConverter.gregorianToPersian(gregStr) // "yyyy-M-d"
        val parts = persian.split("-")
        val y = parts.getOrNull(0)?.toIntOrNull() ?: date.year
        val m = parts.getOrNull(1)?.toIntOrNull() ?: date.monthNumber
        val d = parts.getOrNull(2)?.toIntOrNull() ?: date.dayOfMonth
        return Triple(y, m, d)
    }

    fun jalaliToGregorian(year: Int, month: Int, day: Int): LocalDate {
        val jalali = JalaliCalendar(year, month, day)
        val gregCal = jalali.toGregorian()
        val y = gregCal.get(Calendar.YEAR)
        val m = gregCal.get(Calendar.MONTH) + 1
        val d = gregCal.get(Calendar.DAY_OF_MONTH)
        return LocalDate(y, m, d)
    }

    /** Jalali month length for given Jalali year/month. */
    fun jalaliMonthLength(year: Int, month: Int): Int {
        val jalali = JalaliCalendar(year, month, 1)
        return jalali.monthLength
    }

    /**
     * First day-of-week column index for a Jalali month, with week header:
     * [ "ش", "ی", "د", "س", "چ", "پ", "ج" ]
     * => Saturday=0, Sunday=1, ..., Friday=6
     */
    fun jalaliFirstDayColumnIndex(year: Int, month: Int): Int {
        val jalali = JalaliCalendar(year, month, 1)
        val greg = jalali.toGregorian()
        val dow = greg.get(Calendar.DAY_OF_WEEK) // 1=Sun..7=Sat
        return when (dow) {
            Calendar.SATURDAY -> 0
            Calendar.SUNDAY -> 1
            Calendar.MONDAY -> 2
            Calendar.TUESDAY -> 3
            Calendar.WEDNESDAY -> 4
            Calendar.THURSDAY -> 5
            Calendar.FRIDAY -> 6
            else -> 0
        }
    }
}
