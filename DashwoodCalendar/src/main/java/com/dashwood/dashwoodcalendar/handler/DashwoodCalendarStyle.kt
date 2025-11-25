package com.dashwood.dashwoodcalendar.handler

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    val textSizeMonthYearList: Float,
    val textSizeDay: Float,
    val textSizeWeekName: Float,
    val textSizeWeekend: Float,
    val textSizeNowDay: Float,

    val backgroundMonthYearList: Color,

    val dayRadius: Dp,
    val monthYearRadius: Dp,

    val dayWidth: Dp,
    val dayHeight: Dp,

    val disableWeekend: Boolean
) {
    companion object {
        fun default(): DashwoodCalendarStyle = DashwoodCalendarStyle(
            backgroundNowDay = Color(0xFF2196F3),
            backgroundWeekendDay = Color(0xFFE0E0E0),
            backgroundEnabledDay = Color.Transparent,
            backgroundDisabledDay = Color.Transparent,
            backgroundWeekName = Color(0xFFF5F5F5),

            textWeekNameColor = Color.Black,
            textWeekendColor = Color.Red,
            textEnabledDayColor = Color.Black,
            textNowDayColor = Color.White,
            textDisabledDayColor = Color.Gray,

            backgroundBtnYear = Color(0xFFF5F5F5),
            backgroundBtnToday = Color(0xFF2196F3),
            backgroundBtnMonth = Color(0xFFF5F5F5),
            backgroundTopBar = Color(0xFFF5F5F5),

            textColorBtnToday = Color.White,
            textColorBtnYear = Color.Black,
            textColorBtnMonth = Color.Black,

            textColorMonthYearList = Color.Black,
            textSizeMonthYearList = 14f,
            textSizeDay = 14f,
            textSizeWeekName = 12f,
            textSizeWeekend = 12f,
            textSizeNowDay = 14f,

            backgroundMonthYearList = Color(0xFFF5F5F5),

            dayRadius = 8.dp,
            monthYearRadius = 8.dp,

            dayWidth = 40.dp,
            dayHeight = 40.dp,

            disableWeekend = false
        )
    }
}
