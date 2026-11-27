package com.jacqulin.gainly.feature.history.presentation.sections.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.theme.Black
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.core.designsystem.theme.GrayTextColor
import com.jacqulin.gainly.core.designsystem.theme.PrimaryBlue
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import java.time.LocalDate

@Composable
fun HistoryCalendarDay(
    day: CalendarDay,
    isSelected: Boolean,
    hasWorkout: Boolean,
    onClick: () -> Unit
) {
    val isCurrentMonth = day.position == DayPosition.MonthDate

    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isSelected) PrimaryBlue else Color.Transparent
            )
            .clickable(
                enabled = isCurrentMonth,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            style = TextStyle(
                fontFamily = GoogleSansFontFamily,
                fontSize = 14.sp,
                color = when {
                    isSelected -> Color.White
                    !isCurrentMonth -> GrayTextColor.copy(alpha = 0.65f)
                    else -> Black
                }
            )
        )

        if (hasWorkout && isCurrentMonth) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .size(4.dp)
                    .background(
                        color = PrimaryBlue,
                        shape = CircleShape
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryCalendarDayPreview_Not_Selected() {
    HistoryCalendarDay(
        day = CalendarDay(
            date = LocalDate.of(2026, 11, 21),
            position = DayPosition.MonthDate
        ),
        isSelected = false,
        hasWorkout = false,
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun HistoryCalendarDayPreview_Selected() {
    HistoryCalendarDay(
        day = CalendarDay(
            date = LocalDate.of(2026, 11, 22),
            position = DayPosition.MonthDate
        ),
        isSelected = true,
        hasWorkout = false,
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun HistoryCalendarDayPreview_NotSelected_HasWorkout() {
    HistoryCalendarDay(
        day = CalendarDay(
            date = LocalDate.of(2026, 11, 23),
            position = DayPosition.MonthDate
        ),
        isSelected = false,
        hasWorkout = true,
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun HistoryCalendarDayPreview_Selected_HasWorkout() {
    HistoryCalendarDay(
        day = CalendarDay(
            date = LocalDate.of(2026, 11, 23),
            position = DayPosition.MonthDate
        ),
        isSelected = true,
        hasWorkout = true,
        onClick = {}
    )
}
