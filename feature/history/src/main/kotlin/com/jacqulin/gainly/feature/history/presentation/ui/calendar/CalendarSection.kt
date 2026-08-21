package com.jacqulin.gainly.feature.history.presentation.ui.calendar

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.theme.Black
import com.jacqulin.gainly.core.designsystem.theme.BottomSheetContainerColor
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.core.designsystem.theme.GrayText
import com.jacqulin.gainly.feature.history.presentation.model.CalendarDay
import java.time.LocalDate

@Composable
fun CalendarSection(
    days: List<CalendarDay>,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        days.forEach { day ->
            CalendarDayItem(
                day = day,
                isSelected = day.date == selectedDate,
                onClick = {
                    onDateSelected(day.date)
                }
            )
        }
    }
}

@Composable
private fun CalendarDayItem(
    day: CalendarDay,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(
                color = if (isSelected)
                    BottomSheetContainerColor
                else
                    Color.Transparent,
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
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = Black
        )

        Text(
            text = day.dayName,
            fontFamily = GoogleSansFontFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = GrayText,
            letterSpacing = 0.sp
        )
    }
}