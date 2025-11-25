package com.dashwood.dashwoodcalendar.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class DashwoodCalendarStyle(
    val backgroundNowDay: Color,
    val backgroundWeekendDay: Color,
    val backgroundEnabledDay: Color,
    val backgroundDisabledDay: Color,
    val backgroundWeekName: Color,

    val textWeekNameColor: Color,
    val textWeekendColor: Color,
    val textEnabledDayColor: Color,
    val textNowDayColor: Color,
    val textDisabledDayColor: Color,

    val backgroundBtnYear: Color,
    val backgroundBtnToday: Color,
    val backgroundBtnMonth: Color,
    val backgroundTopBar: Color,

    val textColorBtnToday: Color,
    val textColorBtnYear: Color,
    val textColorBtnMonth: Color,

    val textColorMonthYearList: Color,
    val textSizeMonthYearListSp: Float,
    val textSizeDaySp: Float,
    val textSizeWeekNameSp: Float,

    val backgroundMonthYearList: Color,

    val dayRadius: Dp,
    val monthYearRadius: Dp,

    val dayHeight: Dp,

    val disableWeekend: Boolean
) {
    companion object {
        fun default(): DashwoodCalendarStyle = DashwoodCalendarStyle(
            backgroundNowDay = Color(0xFF2196F3),
            backgroundWeekendDay = Color(0xFFE0E0E0),
            backgroundEnabledDay = Color.Companion.Transparent,
            backgroundDisabledDay = Color.Companion.Transparent,
            backgroundWeekName = Color(0xFFF5F5F5),

            textWeekNameColor = Color.Companion.Black,
            textWeekendColor = Color(0xFFD32F2F),
            textEnabledDayColor = Color.Companion.Black,
            textNowDayColor = Color.Companion.White,
            textDisabledDayColor = Color.Companion.Gray,

            backgroundBtnYear = Color(0xFFF5F5F5),
            backgroundBtnToday = Color(0xFF2196F3),
            backgroundBtnMonth = Color(0xFFF5F5F5),
            backgroundTopBar = Color(0xFFF5F5F5),

            textColorBtnToday = Color.Companion.White,
            textColorBtnYear = Color.Companion.Black,
            textColorBtnMonth = Color.Companion.Black,

            textColorMonthYearList = Color.Companion.Black,
            textSizeMonthYearListSp = 14f,
            textSizeDaySp = 14f,
            textSizeWeekNameSp = 12f,

            backgroundMonthYearList = Color(0xFFF5F5F5),

            dayRadius = 8.dp,
            monthYearRadius = 8.dp,

            dayHeight = 40.dp,

            disableWeekend = false
        )
    }
}