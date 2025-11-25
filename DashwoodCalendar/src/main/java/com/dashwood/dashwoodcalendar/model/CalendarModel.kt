package com.dashwood.dashwoodcalendar.model
import kotlinx.datetime.LocalDate

data class CalendarModel(
    val day: Int,                 // 1..31
    val month: Int,               // 1..12
    val year: Int,
    val monthName: String,
    val dayOfWeek: String,        // "Saturday", "Sunday", ... (or Persian names)
    val dayOfWeekNumber: Int,     // 1..7
    val fullDateWithMonthString: String, // e.g. "26 Aban 1404"
    val fullDate: String,         // e.g. "1404/08/26"
    val gregorianDate: LocalDate, // always Gregorian
    val isToday: Boolean,
    val isDisabled: Boolean,
    val isWeekend: Boolean
)
