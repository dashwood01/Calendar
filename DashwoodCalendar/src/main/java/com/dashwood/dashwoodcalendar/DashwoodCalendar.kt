package com.dashwood.dashwoodcalendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dashwood.dashwoodcalendar.handler.CalendarLanguage
import com.dashwood.dashwoodcalendar.state.DashwoodCalendarState
import com.dashwood.dashwoodcalendar.theme.DashwoodCalendarStyle
import com.dashwood.dashwoodcalendar.handler.DateConverter
import com.dashwood.dashwoodcalendar.handler.JalaliUtils
import com.dashwood.dashwoodcalendar.model.CalendarModel
import kotlinx.coroutines.launch
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.isoDayNumber

@Composable
fun DashwoodCalendar(
    state: DashwoodCalendarState,
    disabledDays: List<LocalDate> = emptyList(),
    onDayClick: (CalendarModel) -> Unit = {},
    style: DashwoodCalendarStyle = DashwoodCalendarStyle.default(),
    modifier: Modifier = Modifier,
    rtl: Boolean = true
) {
    val layoutDirection = if (rtl) LayoutDirection.Rtl else LayoutDirection.Ltr

    val minYear = state.minYear
    val maxYear = state.maxYear
    val totalMonths = (maxYear - minYear + 1) * 12

    val initialPage = remember {
        val clampedYear = state.currentYear.coerceIn(minYear, maxYear)
        val month = state.currentMonth.coerceIn(1, 12)
        (clampedYear - minYear) * 12 + (month - 1)
    }

    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { totalMonths }
    )

    val scope = rememberCoroutineScope()

    // keep state.currentYear/currentMonth in sync with pager
    LaunchedEffect(pagerState.currentPage) {
        val idx = pagerState.currentPage
        val year = minYear + idx / 12
        val month = 1 + idx % 12
        state.setMonthYear(year, month)
    }

    var showYearPicker by remember { mutableStateOf(false) }
    var showMonthPicker by remember { mutableStateOf(false) }

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
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
                CalendarTopBar(
                    state = state,
                    style = style,
                    onYearClick = { showYearPicker = true },
                    onMonthClick = { showMonthPicker = true },
                    onTodayClick = {
                        state.goToToday()
                        val year = state.currentYear
                        val month = state.currentMonth
                        val targetPage = (year - minYear) * 12 + (month - 1)
                        scope.launch {
                            pagerState.animateScrollToPage(targetPage)
                        }
                    }
                )
                Spacer(Modifier.height(8.dp))
                CalendarWeekNames(style = style, language = state.language)
                Spacer(Modifier.height(4.dp))

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth()
                ) { page ->
                    val pageYear = minYear + page / 12
                    val pageMonth = 1 + page % 12

                    CalendarMonthPage(
                        language = state.language,
                        year = pageYear,
                        month = pageMonth,
                        disabledDays = disabledDays,
                        style = style,
                        selectedDate = state.selectedDate,
                        onSelectDate = { date, dashwoodDay ->
                            state.onDateSelected(date)
                            onDayClick(dashwoodDay)
                        }
                    )
                }
            }
        }

        if (showYearPicker) {
            YearPickerDialog(
                currentYear = state.currentYear,
                minYear = minYear,
                maxYear = maxYear,
                style = style,
                onDismiss = { showYearPicker = false },
                onYearSelected = { year ->
                    val month = state.currentMonth
                    val targetPage = (year - minYear) * 12 + (month - 1)
                    scope.launch {
                        pagerState.animateScrollToPage(targetPage)
                    }
                    showYearPicker = false
                }
            )
        }

        if (showMonthPicker) {
            MonthPickerDialog(
                language = state.language,
                currentYear = state.currentYear,
                currentMonth = state.currentMonth,
                style = style,
                onDismiss = { showMonthPicker = false },
                onMonthSelected = { month ->
                    val year = state.currentYear
                    val targetPage = (year - minYear) * 12 + (month - 1)
                    scope.launch {
                        pagerState.animateScrollToPage(targetPage)
                    }
                    showMonthPicker = false
                }
            )
        }
    }
}

/* -------------------- Top bar & week names -------------------- */

@Composable
private fun CalendarTopBar(
    state: DashwoodCalendarState,
    style: DashwoodCalendarStyle,
    onYearClick: () -> Unit,
    onMonthClick: () -> Unit,
    onTodayClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Year button
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(style.monthYearRadius))
                .background(style.backgroundBtnYear)
                .clickable { onYearClick() }
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = state.currentYear.toString(),
                color = style.textColorBtnYear,
                fontSize = style.textSizeMonthYearListSp.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(Modifier.width(8.dp))

        // Month button
        Box(
            modifier = Modifier
                .weight(1.5f)
                .clip(RoundedCornerShape(style.monthYearRadius))
                .background(style.backgroundBtnMonth)
                .clickable { onMonthClick() }
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = monthName(state.currentYear, state.currentMonth, state.language),
                color = style.textColorBtnMonth,
                fontSize = style.textSizeMonthYearListSp.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(Modifier.width(8.dp))

        // Today button
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(style.monthYearRadius))
                .background(style.backgroundBtnToday)
                .clickable { onTodayClick() }
                .padding(horizontal = 12.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (state.language == CalendarLanguage.Persian) "امروز" else "Today",
                color = style.textColorBtnToday,
                fontSize = style.textSizeMonthYearListSp.sp,
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
                CalendarLanguage.Persian -> index == 6  // جمعه
                CalendarLanguage.Gregorian -> index == 0 || index == 6
            }
            Text(
                text = name,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = if (isWeekend) style.textWeekendColor else style.textWeekNameColor,
                fontSize = style.textSizeWeekNameSp.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

/* -------------------- Month page (grid) -------------------- */

@Composable
private fun CalendarMonthPage(
    language: CalendarLanguage,
    year: Int,
    month: Int,
    disabledDays: List<LocalDate>,
    style: DashwoodCalendarStyle,
    selectedDate: LocalDate?,
    onSelectDate: (LocalDate, CalendarModel) -> Unit
) {
    when (language) {
        CalendarLanguage.Gregorian -> GregorianMonthGrid(
            year = year,
            month = month,
            disabledDays = disabledDays,
            style = style,
            selectedDate = selectedDate,
            onSelectDate = onSelectDate
        )

        CalendarLanguage.Persian -> PersianMonthGrid(
            year = year,
            month = month,
            disabledDays = disabledDays,
            style = style,
            selectedDate = selectedDate,
            onSelectDate = onSelectDate
        )
    }
}

@Composable
private fun GregorianMonthGrid(
    year: Int,
    month: Int,
    disabledDays: List<LocalDate>,
    style: DashwoodCalendarStyle,
    selectedDate: LocalDate?,
    onSelectDate: (LocalDate, CalendarModel) -> Unit
) {
    val firstDayOfMonth = LocalDate(year, month, 1)
    val daysInMonth = daysInMonthGregorian(year, month)
    val firstDayOfWeekIndex = firstDayOfMonth.dayOfWeek.toGregorianIndex()
    val today = JalaliUtils.todayGregorian()

    Column(Modifier.fillMaxWidth()) {
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
                        val isWeekend = iso == 6 || iso == 7   // Sat/Sun
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
                            isSelected = (selectedDate == date),
                            onClick = {
                                if (!isDisabled) {
                                    onSelectDate(date, dashwoodDay)
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
    year: Int,                         // Jalali year
    month: Int,                        // Jalali month
    disabledDays: List<LocalDate>,
    style: DashwoodCalendarStyle,
    selectedDate: LocalDate?,
    onSelectDate: (LocalDate, CalendarModel) -> Unit
) {
    val daysInMonth = JalaliUtils.jalaliMonthLength(year, month)
    val firstDayIndex = JalaliUtils.jalaliFirstDayColumnIndex(year, month)
    val todayG = JalaliUtils.todayGregorian()

    Column(Modifier.fillMaxWidth()) {
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
                        val gregDate =
                            JalaliUtils.jalaliToGregorian(year, month, dayCounter)
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
                            isSelected = (selectedDate == gregDate),
                            onClick = {
                                if (!isDisabled) {
                                    onSelectDate(gregDate, dashwoodDay)
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
    day: CalendarModel,
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
            fontSize = style.textSizeDaySp.sp,
            fontWeight = if (day.isToday) FontWeight.Bold else FontWeight.Normal,
            color = textColor
        )
    }
}

/* -------------------- Year / Month pickers -------------------- */

@Composable
private fun YearPickerDialog(
    currentYear: Int,
    minYear: Int,
    maxYear: Int,
    style: DashwoodCalendarStyle,
    onDismiss: () -> Unit,
    onYearSelected: (Int) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        title = {
            Text(text = "انتخاب سال")
        },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp)
            ) {
                items((minYear..maxYear).toList()) { year ->
                    val isSelected = year == currentYear
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onYearSelected(year) }
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = year.toString(),
                            fontSize = style.textSizeMonthYearListSp.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun MonthPickerDialog(
    language: CalendarLanguage,
    currentYear: Int,
    currentMonth: Int,
    style: DashwoodCalendarStyle,
    onDismiss: () -> Unit,
    onMonthSelected: (Int) -> Unit
) {
    val months = (1..12).map { month ->
        month to monthName(currentYear, month, language)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        title = {
            Text(text = if (language == CalendarLanguage.Persian) "انتخاب ماه" else "Select month")
        },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp)
            ) {
                items(items = months) { (m, label) ->
                    val isSelected = (m == currentMonth)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onMonthSelected(m) }
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = label,
                            fontSize = style.textSizeMonthYearListSp.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }

                /*months.forEach { (m, label) ->

                }*/
            }
        }
    )
}

/* -------------------- Helpers -------------------- */

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
            val jalali = ir.huri.jcal.JalaliCalendar(year, month, 1)
            jalali.monthString
        }
    }
}

private fun buildDashwoodDay(
    date: LocalDate,                 // ALWAYS Gregorian date
    language: CalendarLanguage,
    isToday: Boolean,
    isDisabled: Boolean,
    isWeekend: Boolean
): CalendarModel {

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
                val persianDate = DateConverter.gregorianToPersian(gregStr)
                val parts = persianDate.split("-")
                val py = parts.getOrNull(0)?.toIntOrNull() ?: date.year
                val pm = parts.getOrNull(1)?.toIntOrNull() ?: date.monthNumber
                val pd = parts.getOrNull(2)?.toIntOrNull() ?: date.dayOfMonth

                val withMonthName =
                    DateConverter.gregorianToPersianWithMonthStringName(gregStr)
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

    return CalendarModel(
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
