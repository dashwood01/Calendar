package com.dashwood.dashwoodcalendar.handler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.datetime.LocalDate

@Stable
class DashwoodCalendarState internal constructor(
    initialLanguage: CalendarLanguage,
    private val minYear: Int,
    private val maxYear: Int
) {

    var language by mutableStateOf(initialLanguage)
        internal set

    /** In Gregorian mode: Gregorian year; in Persian mode: Jalali year */
    var currentYear by mutableStateOf(0)
        internal set

    /** 1..12, same semantics as currentYear (Gregorian or Jalali) */
    var currentMonth by mutableStateOf(1)
        internal set

    /** Always Gregorian selected date */
    var selectedDate by mutableStateOf<LocalDate?>(null)
        internal set

    init {
        val todayG = JalaliUtils.todayGregorian()
        when (initialLanguage) {
            CalendarLanguage.Gregorian -> {
                currentYear = todayG.year
                currentMonth = todayG.monthNumber
            }
            CalendarLanguage.Persian -> {
                val (py, pm, _) = JalaliUtils.gregorianToJalali(todayG)
                currentYear = py
                currentMonth = pm
            }
        }
        enforceYearBounds()
    }

    fun goToNextMonth() {
        if (currentMonth == 12) {
            currentMonth = 1
            currentYear += 1
        } else {
            currentMonth += 1
        }
        enforceYearBounds()
    }

    fun goToPreviousMonth() {
        if (currentMonth == 1) {
            currentMonth = 12
            currentYear -= 1
        } else {
            currentMonth -= 1
        }
        enforceYearBounds()
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

    private fun enforceYearBounds() {
        val boundedYear = currentYear.coerceIn(minYear, maxYear)
        if (boundedYear != currentYear) {
            currentYear = boundedYear
        }
    }

    internal fun onDateSelected(date: LocalDate) {
        selectedDate = date
    }
}

@Composable
fun rememberDashwoodCalendarState(
    language: CalendarLanguage,
    minYear: Int,
    maxYear: DashwoodYear
): DashwoodCalendarState {
    val resolvedMax = maxYear.resolve(language)
    return remember(language, minYear, resolvedMax) {
        DashwoodCalendarState(
            initialLanguage = language,
            minYear = minYear,
            maxYear = resolvedMax
        )
    }
}
