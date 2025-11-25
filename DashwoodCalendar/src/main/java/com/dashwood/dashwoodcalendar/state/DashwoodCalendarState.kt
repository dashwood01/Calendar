package com.dashwood.dashwoodcalendar.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.dashwood.dashwoodcalendar.handler.CalendarLanguage
import com.dashwood.dashwoodcalendar.handler.CalendarYear
import com.dashwood.dashwoodcalendar.handler.JalaliUtils
import com.dashwood.dashwoodcalendar.handler.resolve
import kotlinx.datetime.LocalDate

@Stable
class DashwoodCalendarState internal constructor(
    val language: CalendarLanguage,
    val minYear: Int,
    val maxYear: Int
) {

    /** In Gregorian mode: gregorian year; in Persian mode: jalali year */
    var currentYear by mutableStateOf(0)
        internal set

    /** 1..12 – matches currentYear semantics (gregorian/jalali) */
    var currentMonth by mutableStateOf(1)
        internal set

    /** Always gregorian selected date */
    var selectedDate by mutableStateOf<LocalDate?>(null)
        internal set

    init {
        goToToday()
    }

    fun goToToday() {
        val todayG = JalaliUtils.todayGregorian()
        when (language) {
            CalendarLanguage.Gregorian -> {
                currentYear = todayG.year
                currentMonth = todayG.monthNumber
                selectedDate = todayG
            }
            CalendarLanguage.Persian -> {
                val (py, pm, _) = JalaliUtils.gregorianToJalali(todayG)
                currentYear = py
                currentMonth = pm
                selectedDate = todayG
            }
        }
        enforceYearBounds()
    }

    internal fun onDateSelected(date: LocalDate) {
        selectedDate = date
    }

    internal fun setMonthYear(year: Int, month: Int) {
        currentYear = year
        currentMonth = month
        enforceYearBounds()
    }

    private fun enforceYearBounds() {
        currentYear = currentYear.coerceIn(minYear, maxYear)
        if (currentMonth !in 1..12) {
            currentMonth = currentMonth.coerceIn(1, 12)
        }
    }
}

@Composable
fun rememberDashwoodCalendarState(
    language: CalendarLanguage,
    minYear: Int,
    maxYear: CalendarYear
): DashwoodCalendarState {
    val resolvedMax = maxYear.resolve(language)
    return remember(language, minYear, resolvedMax) {
        DashwoodCalendarState(
            language = language,
            minYear = minYear,
            maxYear = resolvedMax
        )
    }
}
