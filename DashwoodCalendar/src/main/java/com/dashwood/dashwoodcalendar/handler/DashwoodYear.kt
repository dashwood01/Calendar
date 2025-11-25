package com.dashwood.dashwoodcalendar.handler

import kotlinx.datetime.LocalDate

sealed class DashwoodYear {
    data class Fixed(val value: Int) : DashwoodYear()
    data object ThisYearGregorian : DashwoodYear()
    data object ThisYearPersian : DashwoodYear()
}

internal fun DashwoodYear.resolve(language: CalendarLanguage): Int {
    val todayG: LocalDate = JalaliUtils.todayGregorian()
    return when (this) {
        is DashwoodYear.Fixed -> value
        DashwoodYear.ThisYearGregorian -> todayG.year
        DashwoodYear.ThisYearPersian -> {
            val (py, _, _) = JalaliUtils.gregorianToJalali(todayG)
            py
        }
    }
}
