package com.dashwood.dashwoodcalendar.handler

import ir.huri.jcal.JalaliCalendar
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale

object DateConverter {

    fun persianDateToGregorianDate(persianDate: String): String {
        return try {
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val date: Date? = format.parse(persianDate)
            if (date == null) {
                ""
            } else {
                val persianFormatDate = format.format(date)
                val persianDates = persianFormatDate.split("-")
                val jalaliCalendar = JalaliCalendar(
                    persianDates[0].toInt(),
                    persianDates[1].toInt(),
                    persianDates[2].toInt()
                )
                val gregorianCalendar = jalaliCalendar.toGregorian()
                val day = gregorianCalendar[Calendar.DAY_OF_MONTH]
                val stringDay = if (day < 10) "0$day" else day.toString()
                stringDay + "-" +
                        (gregorianCalendar[Calendar.MONTH] + 1) + "-" +
                        gregorianCalendar[Calendar.YEAR]
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }

    fun persianDateToGregorianDate(year: Int, month: Int, day: Int): String {
        val jalaliCalendar = JalaliCalendar(year, month, day)
        val gregorianCalendar = jalaliCalendar.toGregorian()
        val newDay = gregorianCalendar[Calendar.DAY_OF_MONTH]
        val stringDay = if (newDay < 10) "0$newDay" else newDay.toString()
        return gregorianCalendar[Calendar.YEAR].toString() + "-" +
                (gregorianCalendar[Calendar.MONTH] + 1) + "-" +
                stringDay
    }

    fun persianDateToGregorianDateFirstReturnYear(persianDate: String): String {
        return try {
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val date: Date? = format.parse(persianDate)
            if (date == null) {
                ""
            } else {
                val persianFormatDate = format.format(date)
                val persianDates = persianFormatDate.split("-")
                val jalaliCalendar = JalaliCalendar(
                    persianDates[0].toInt(),
                    persianDates[1].toInt(),
                    persianDates[2].toInt()
                )
                val gregorianCalendar = jalaliCalendar.toGregorian()
                val day = gregorianCalendar[Calendar.DAY_OF_MONTH]
                val stringDay = if (day < 10) "0$day" else day.toString()
                gregorianCalendar[Calendar.YEAR].toString() + "-" +
                        (gregorianCalendar[Calendar.MONTH] + 1) + "-" +
                        stringDay
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }

    fun gregorianToPersianWithMonthStringName(gregorianDateValue: String): String {
        return try {
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val date: Date? = format.parse(gregorianDateValue)
            if (date == null) {
                ""
            } else {
                val stringFormatData = format.format(date)
                val gregorianDate = stringFormatData.split("-")
                val jalaliCalendar = JalaliCalendar(
                    GregorianCalendar(
                        gregorianDate[0].toInt(),
                        gregorianDate[1].toInt() - 1,
                        gregorianDate[2].toInt()
                    )
                )
                val day = jalaliCalendar.day
                val stringDay = if (day < 10) "0$day" else day.toString()
                "$stringDay ${jalaliCalendar.monthString} ${jalaliCalendar.year}"
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }

    fun gregorianToPersian(gregorianDateValue: String): String {
        var value = gregorianDateValue
        val dates = value.split(" ")
        value = dates[0]
        return try {
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val date: Date? = format.parse(value)
            if (date == null) {
                ""
            } else {
                val stringFormatData = format.format(date)
                val gregorianDate = stringFormatData.split("-")
                val jalaliCalendar = JalaliCalendar(
                    GregorianCalendar(
                        gregorianDate[0].toInt(),
                        gregorianDate[1].toInt() - 1,
                        gregorianDate[2].toInt()
                    )
                )
                val day = jalaliCalendar.day
                val stringDay = if (day < 10) "0$day" else day.toString()
                "${jalaliCalendar.year}-${jalaliCalendar.month}-$stringDay"
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }

    fun gregorianToPersian(year: Int, month: Int, day: Int): String {
        val jalaliCalendar = JalaliCalendar(GregorianCalendar(year, month, day))
        val newDay = jalaliCalendar.day
        val stringDay = if (newDay < 10) "0$newDay" else newDay.toString()
        return "${jalaliCalendar.year}-${jalaliCalendar.month}-$stringDay"
    }

    fun getMonthLength(gregorianDateValue: String): Int {
        var value = gregorianDateValue
        val dates = value.split(" ")
        value = dates[0]
        return try {
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val date: Date? = format.parse(value)
            if (date == null) {
                0
            } else {
                val stringFormatData = format.format(date)
                val gregorianDate = stringFormatData.split("-")
                val jalaliCalendar = JalaliCalendar(
                    GregorianCalendar(
                        gregorianDate[0].toInt(),
                        gregorianDate[1].toInt() - 1,
                        gregorianDate[2].toInt()
                    )
                )
                jalaliCalendar.monthLength
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            0
        }
    }

    fun getMonthStartWeekName(gregorianDateValue: String): String {
        var value = gregorianDateValue
        val dates = value.split(" ")
        value = dates[0]
        return try {
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val date: Date? = format.parse(value)
            if (date == null) {
                ""
            } else {
                val stringFormatData = format.format(date)
                val gregorianDate = stringFormatData.split("-")
                val jalaliCalendar = JalaliCalendar(
                    GregorianCalendar(
                        gregorianDate[0].toInt(),
                        gregorianDate[1].toInt() - 1,
                        gregorianDate[2].toInt()
                    )
                )
                jalaliCalendar.dayOfWeekString
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }

    fun getPersianDateJalali(gregorianDateValue: String): JalaliCalendar? {
        var value = gregorianDateValue
        val dates = value.split(" ")
        value = dates[0]
        return try {
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val date: Date? = format.parse(value)
            if (date == null) {
                null
            } else {
                val stringFormatData = format.format(date)
                val gregorianDate = stringFormatData.split("-")
                JalaliCalendar(
                    GregorianCalendar(
                        gregorianDate[0].toInt(),
                        gregorianDate[1].toInt() - 1,
                        gregorianDate[2].toInt()
                    )
                )
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }

    fun getPersianDateJalali(year: Int, month: Int, day: Int): JalaliCalendar {
        return JalaliCalendar(year, month, day)
    }

    fun persianDateToGregorianDateWithSlash(persianDate: String): String {
        return try {
            val format = SimpleDateFormat("yyyy/MM/dd", Locale.ROOT)
            val date: Date? = format.parse(persianDate)
            if (date == null) {
                ""
            } else {
                val persianFormatDate = format.format(date)
                val persianDates = persianFormatDate.split("/")
                val jalaliCalendar = JalaliCalendar(
                    persianDates[0].toInt(),
                    persianDates[1].toInt(),
                    persianDates[2].toInt()
                )
                val gregorianCalendar = jalaliCalendar.toGregorian()
                val day = gregorianCalendar[Calendar.DAY_OF_MONTH]
                val stringDay = if (day < 10) "0$day" else day.toString()
                val month = gregorianCalendar[Calendar.MONTH] + 1
                val stringMonth = if (month < 10) "0$month" else month.toString()
                "${gregorianCalendar[Calendar.YEAR]}/$stringMonth/$stringDay"
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }

    fun gregorianToPersianWithSlash(gregorianDateValue: String): String {
        var value = gregorianDateValue
        val dates = value.split(" ")
        value = dates[0]
        return try {
            val format = SimpleDateFormat("yyyy/MM/dd", Locale.ROOT)
            val date: Date? = format.parse(value)
            if (date == null) {
                ""
            } else {
                val stringFormatData = format.format(date).replace("/", "-")
                val gregorianDate = stringFormatData.split("-")
                val jalaliCalendar = JalaliCalendar(
                    GregorianCalendar(
                        gregorianDate[0].toInt(),
                        gregorianDate[1].toInt() - 1,
                        gregorianDate[2].toInt()
                    )
                )
                val day = jalaliCalendar.day
                val stringDay = if (day < 10) "0$day" else day.toString()
                val month = jalaliCalendar.month
                val stringMonth = if (month < 10) "0$month" else month.toString()
                "${jalaliCalendar.year}/$stringMonth/$stringDay"
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }
}
