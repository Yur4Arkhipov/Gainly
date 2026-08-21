package com.jacqulin.gainly.feature.history.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun CalendarSection() {
    val days = listOf(
        CalendarDay("Пн", "18"),
        CalendarDay("Вт", "19"),
        CalendarDay("Ср", "20"),
        CalendarDay("Чт", "21"),
        CalendarDay("Пт", "22"),
        CalendarDay("Сб", "23"),
        CalendarDay("Вс", "24"),
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        days.forEach { day ->
            CalendarDayItem(
                day = day,
                isSelected = day.day == "20"
            )
        }
    }
}

@Composable
private fun CalendarDayItem(
    day: CalendarDay,
    isSelected: Boolean
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
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = day.day,
            fontFamily = GoogleSansFontFamily,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = Black
        )

        Text(
            text = day.weekDay,
            fontFamily = GoogleSansFontFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = GrayText,
            letterSpacing = 0.sp
        )
    }
}