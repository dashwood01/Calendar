package com.dashwood.dashwoodcalendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalLayoutDirection
import com.dashwood.dashwoodcalendar.handler.CalendarLanguage
import com.dashwood.dashwoodcalendar.handler.DashwoodCalendarState
import com.dashwood.dashwoodcalendar.handler.DashwoodCalendarStyle
import com.dashwood.dashwoodcalendar.handler.JalaliUtils
import com.dashwood.dashwoodcalendar.inf.DashwoodDay
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.isoDayNumber

@Composable
fun DashwoodCalendar(
    state: DashwoodCalendarState,
    disabledDays: List<LocalDate> = emptyList(),      // list of disabled GREGORIAN dates
    onDayClick: (DashwoodDay) -> Unit = {},
    style: DashwoodCalendarStyle = DashwoodCalendarStyle.default(),
    modifier: Modifier = Modifier,
    rtl: Boolean = true
) {
    CompositionLocalProvider(
        LocalLayoutDirection provides if (rtl) LayoutDirection.Rtl else LayoutDirection.Ltr
    ) {
        Surface(
            modifier = modifier,
            shape = RoundedCornerShape(style.dayRadius),
            tonalElevation = 2.dp
        ) {
            Column(
                modifier = Modifier
                    .background(style.backgroundTopBar)
                    .padding(8.dp)
            ) {
                CalendarTopBar(state = state, style = style)
                Spacer(modifier = Modifier.height(8.dp))
                CalendarWeekNames(style = style, language = state.language)
                Spacer(modifier = Modifier.height(4.dp))
                CalendarMonthGrid(
                    state = state,
                    disabledDays = disabledDays,
                    style = style,
                    onDayClick = onDayClick
                )
            }
        }
    }
}

@Composable
private fun CalendarTopBar(
    state: DashwoodCalendarState,
    style: DashwoodCalendarStyle
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Year
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(style.monthYearRadius))
                .background(style.backgroundBtnYear)
                .clickable { /* TODO year picker */ }
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = state.currentYear.toString(),
                color = style.textColorBtnYear,
                fontSize = style.textSizeMonthYearList.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Month
        Box(
            modifier = Modifier
                .weight(1.5f)
                .clip(RoundedCornerShape(style.monthYearRadius))
                .background(style.backgroundBtnMonth)
                .clickable { /* TODO month picker */ }
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = monthName(state.currentYear, state.currentMonth, state.language),
                color = style.textColorBtnMonth,
                fontSize = style.textSizeMonthYearList.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Today
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(style.monthYearRadius))
                .background(style.backgroundBtnToday)
                .clickable { state.goToToday() }
                .padding(horizontal = 12.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (state.language == CalendarLanguage.Persian) "امروز" else "Today",
                color = style.textColorBtnToday,
                fontSize = style.textSizeMonthYearList.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun CalendarWeekNames(
    style: DashwoodCalendarStyle,
    language: CalendarLanguage
) {
    val names = when (language) {
        CalendarLanguage.Persian -> listOf("ش", "ی", "د", "س", "چ", "پ", "ج")
        CalendarLanguage.Gregorian -> listOf("Su", "Mo", "Tu", "We", "Th", "Fr", "Sa")
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(style.backgroundWeekName)
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        names.forEachIndexed { index, name ->
            val isWeekend = when (language) {
                CalendarLanguage.Persian -> index == 6 // جمعه
                CalendarLanguage.Gregorian -> index == 0 || index == 6
            }
            Text(
                text = name,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = if (isWeekend) style.textWeekendColor else style.textWeekNameColor,
                fontSize = style.textSizeWeekName.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun CalendarMonthGrid(
    state: DashwoodCalendarState,
    disabledDays: List<LocalDate>,
    style: DashwoodCalendarStyle,
    onDayClick: (DashwoodDay) -> Unit
) {
    when (state.language) {
        CalendarLanguage.Gregorian ->
            GregorianMonthGrid(state, disabledDays, style, onDayClick)

        CalendarLanguage.Persian  ->
            PersianMonthGrid(state, disabledDays, style, onDayClick)
    }
}

@Composable
private fun GregorianMonthGrid(
    state: DashwoodCalendarState,
    disabledDays: List<LocalDate>,
    style: DashwoodCalendarStyle,
    onDayClick: (DashwoodDay) -> Unit
) {
    val year = state.currentYear
    val month = state.currentMonth

    val firstDayOfMonth = LocalDate(year, month, 1)
    val daysInMonth = daysInMonthGregorian(year, month)
    val firstDayOfWeekIndex = firstDayOfMonth.dayOfWeek.toGregorianIndex()
    val today = JalaliUtils.todayGregorian()

    Column(modifier = Modifier.fillMaxWidth()) {
        var dayCounter = 1
        val totalCells = ((firstDayOfWeekIndex + daysInMonth + 6) / 7) * 7

        for (row in 0 until totalCells / 7) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (col in 0 until 7) {
                    val cellIndex = row * 7 + col
                    if (cellIndex < firstDayOfWeekIndex || dayCounter > daysInMonth) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(style.dayHeight)
                        )
                    } else {
                        val date = LocalDate(year, month, dayCounter)
                        val isToday = date == today
                        val iso = date.dayOfWeek.isoDayNumber
                        val isWeekend = iso == 6 || iso == 7        // Sat/Sun
                        val isDisabled = disabledDays.contains(date) ||
                                (style.disableWeekend && isWeekend)

                        val dashwoodDay = buildDashwoodDay(
                            date = date,
                            language = CalendarLanguage.Gregorian,
                            isToday = isToday,
                            isDisabled = isDisabled,
                            isWeekend = isWeekend
                        )

                        DayCell(
                            day = dashwoodDay,
                            style = style,
                            isSelected = state.selectedDate == date,
                            onClick = {
                                if (!isDisabled) {
                                    state.onDateSelected(date)
                                    onDayClick(dashwoodDay)
                                }
                            },
                            modifier = Modifier.weight(1f)
                        )
                        dayCounter++
                    }
                }
            }
        }
    }
}

@Composable
private fun PersianMonthGrid(
    state: DashwoodCalendarState,
    disabledDays: List<LocalDate>,
    style: DashwoodCalendarStyle,
    onDayClick: (DashwoodDay) -> Unit
) {
    val year = state.currentYear       // Jalali
    val month = state.currentMonth     // Jalali

    val daysInMonth = JalaliUtils.jalaliMonthLength(year, month)
    val firstDayIndex = JalaliUtils.jalaliFirstDayColumnIndex(year, month)
    val todayG = JalaliUtils.todayGregorian()

    Column(modifier = Modifier.fillMaxWidth()) {
        var dayCounter = 1
        val totalCells = ((firstDayIndex + daysInMonth + 6) / 7) * 7

        for (row in 0 until totalCells / 7) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (col in 0 until 7) {
                    val cellIndex = row * 7 + col
                    if (cellIndex < firstDayIndex || dayCounter > daysInMonth) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(style.dayHeight)
                        )
                    } else {
                        val gregDate = JalaliUtils.jalaliToGregorian(year, month, dayCounter)
                        val isToday = gregDate == todayG
                        val iso = gregDate.dayOfWeek.isoDayNumber
                        val isWeekend = iso == 5 || iso == 6   // Fri & Sat
                        val isDisabled = disabledDays.contains(gregDate) ||
                                (style.disableWeekend && isWeekend)

                        val dashwoodDay = buildDashwoodDay(
                            date = gregDate,
                            language = CalendarLanguage.Persian,
                            isToday = isToday,
                            isDisabled = isDisabled,
                            isWeekend = isWeekend
                        )

                        DayCell(
                            day = dashwoodDay,
                            style = style,
                            isSelected = state.selectedDate == gregDate,
                            onClick = {
                                if (!isDisabled) {
                                    state.onDateSelected(gregDate)
                                    onDayClick(dashwoodDay)
                                }
                            },
                            modifier = Modifier.weight(1f)
                        )
                        dayCounter++
                    }
                }
            }
        }
    }
}

@Composable
private fun DayCell(
    day: DashwoodDay,
    style: DashwoodCalendarStyle,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val background = when {
        day.isToday -> style.backgroundNowDay
        day.isDisabled -> style.backgroundDisabledDay
        day.isWeekend -> style.backgroundWeekendDay
        else -> style.backgroundEnabledDay
    }

    val textColor = when {
        day.isDisabled -> style.textDisabledDayColor
        day.isToday -> style.textNowDayColor
        day.isWeekend -> style.textWeekendColor
        else -> style.textEnabledDayColor
    }

    Box(
        modifier = modifier
            .height(style.dayHeight)
            .padding(2.dp)
            .clip(RoundedCornerShape(style.dayRadius))
            .background(background)
            .then(
                if (isSelected && !day.isDisabled) {
                    Modifier.border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(style.dayRadius)
                    )
                } else Modifier
            )
            .clickable(enabled = !day.isDisabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.day.toString(),
            fontSize = style.textSizeDay.sp,
            fontWeight = if (day.isToday) FontWeight.Bold else FontWeight.Normal,
            color = textColor
        )
    }
}

/* Helpers */

private fun DayOfWeek.toGregorianIndex(): Int = when (this) {
    DayOfWeek.SUNDAY -> 0
    DayOfWeek.MONDAY -> 1
    DayOfWeek.TUESDAY -> 2
    DayOfWeek.WEDNESDAY -> 3
    DayOfWeek.THURSDAY -> 4
    DayOfWeek.FRIDAY -> 5
    DayOfWeek.SATURDAY -> 6
}

private fun daysInMonthGregorian(year: Int, month: Int): Int = when (month) {
    1, 3, 5, 7, 8, 10, 12 -> 31
    4, 6, 9, 11 -> 30
    2 -> if (isLeapYearGregorian(year)) 29 else 28
    else -> 30
}

private fun isLeapYearGregorian(year: Int): Boolean {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}

private data class Sextuple<A, B, C, D, E, F>(
    val a: A,
    val b: B,
    val c: C,
    val d: D,
    val e: E,
    val f: F
)

private fun monthName(
    year: Int,
    month: Int,
    language: CalendarLanguage
): String {
    return when (language) {
        CalendarLanguage.Gregorian -> {
            Month(month).name.lowercase().replaceFirstChar { it.titlecase() }
        }
        CalendarLanguage.Persian -> {
            // Use Jalali month string from JalaliCalendar
            val jalali = ir.huri.jcal.JalaliCalendar(year, month, 1)
            jalali.monthString
        }
    }
}

private fun buildDashwoodDay(
    date: LocalDate,                 // always Gregorian
    language: CalendarLanguage,
    isToday: Boolean,
    isDisabled: Boolean,
    isWeekend: Boolean
): DashwoodDay {

    val iso = date.dayOfWeek.isoDayNumber

    val dayOfWeekName = when (language) {
        CalendarLanguage.Persian -> {
            when (iso) {
                6 -> "شنبه"
                7 -> "یکشنبه"
                1 -> "دوشنبه"
                2 -> "سه‌شنبه"
                3 -> "چهارشنبه"
                4 -> "پنجشنبه"
                5 -> "جمعه"
                else -> ""
            }
        }
        CalendarLanguage.Gregorian -> {
            date.dayOfWeek.name.lowercase().replaceFirstChar { it.titlecase() }
        }
    }

    val dayOfWeekNumber = iso

    val (year, month, day, monthName, fullDateWithMonthStr, fullDate) =
        when (language) {
            CalendarLanguage.Gregorian -> {
                val mName = Month(date.monthNumber).name
                    .lowercase().replaceFirstChar { it.titlecase() }
                val fWithMonth = "${date.dayOfMonth} $mName ${date.year}"
                val f = "%04d/%02d/%02d".format(date.year, date.monthNumber, date.dayOfMonth)
                Sextuple(
                    date.year,
                    date.monthNumber,
                    date.dayOfMonth,
                    mName,
                    fWithMonth,
                    f
                )
            }

            CalendarLanguage.Persian -> {
                val gregStr = "%04d-%02d-%02d".format(date.year, date.monthNumber, date.dayOfMonth)
                val persianDate = com.dashwood.dashwoodcalendar.handler.DateConverter.gregorianToPersian(gregStr)
                val parts = persianDate.split("-")
                val py = parts.getOrNull(0)?.toIntOrNull() ?: date.year
                val pm = parts.getOrNull(1)?.toIntOrNull() ?: date.monthNumber
                val pd = parts.getOrNull(2)?.toIntOrNull() ?: date.dayOfMonth

                val withMonthName =
                    com.dashwood.dashwoodcalendar.handler.DateConverter
                        .gregorianToPersianWithMonthStringName(gregStr) // "dd Month yyyy"
                val tokens = withMonthName.split(" ")
                val mName = tokens.getOrNull(1) ?: ""

                val f = "%04d/%02d/%02d".format(py, pm, pd)

                Sextuple(
                    py,
                    pm,
                    pd,
                    mName,
                    withMonthName,
                    f
                )
            }
        }

    return DashwoodDay(
        day = day,
        month = month,
        year = year,
        monthName = monthName,
        dayOfWeek = dayOfWeekName,
        dayOfWeekNumber = dayOfWeekNumber,
        fullDateWithMonthString = fullDateWithMonthStr,
        fullDate = fullDate,
        gregorianDate = date,
        isToday = isToday,
        isDisabled = isDisabled,
        isWeekend = isWeekend
    )
}
