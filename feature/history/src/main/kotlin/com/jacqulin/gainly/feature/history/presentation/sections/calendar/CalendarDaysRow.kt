package com.jacqulin.gainly.feature.history.presentation.sections.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.theme.Black
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.core.designsystem.theme.LightGrayColor
import com.jacqulin.gainly.feature.history.presentation.model.CalendarDayUi
import java.time.LocalDate

@Composable
fun CalendarDaysRow(
    days: List<CalendarDayUi>,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        days.forEach { day ->
            CalendarDayItem(
                day = day,
                isSelected = day.date == selectedDate,
                onClick = { onDateSelected(day.date) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun CalendarDayItem(
    day: CalendarDayUi,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(
                color = if (isSelected) LightGrayColor else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = day.dayNumber,
            fontFamily = GoogleSansFontFamily,
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            color = Black
        )

        Text(
            text = day.dayName,
            fontFamily = GoogleSansFontFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = Black.copy(alpha = 0.5f),
            letterSpacing = 0.sp
        )
    }
}

@Preview
@Composable
fun CalendarDaysRowPreview() {
    GainlyTheme {
        val today = LocalDate.now()
        val days = listOf(
            CalendarDayUi(today.minusDays(4), "чт", "1"),
            CalendarDayUi(today.minusDays(3), "пт", "2"),
            CalendarDayUi(today.minusDays(2),"сб", "3"),
            CalendarDayUi(today.minusDays(1), "вс", "4"),
            CalendarDayUi(today, "пн", "5"),
            CalendarDayUi(today.plusDays(1), "вт", "6"),
            CalendarDayUi(today.plusDays(2), "ср", "7"),
        )

        CalendarDaysRow(
            days = days,
            selectedDate = today,
            onDateSelected = {}
        )
    }
}
