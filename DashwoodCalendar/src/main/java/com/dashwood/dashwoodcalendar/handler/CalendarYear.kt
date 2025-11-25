package com.dashwood.dashwoodcalendar.handler

import kotlinx.datetime.LocalDate

sealed class CalendarYear {
    data class Fixed(val value: Int) : CalendarYear()
    data object ThisYearGregorian : CalendarYear()
    data object ThisYearPersian : CalendarYear()
}

internal fun CalendarYear.resolve(language: CalendarLanguage): Int {
    val todayG: LocalDate = JalaliUtils.todayGregorian()
    return when (this) {
        is CalendarYear.Fixed -> value
        CalendarYear.ThisYearGregorian -> todayG.year
        CalendarYear.ThisYearPersian -> {
            val (py, _, _) = JalaliUtils.gregorianToJalali(todayG)
            py
        }
    }
}
