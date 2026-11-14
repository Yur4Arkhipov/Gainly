package com.jacqulin.gainly.feature.history.presentation.sections.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.Black
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.core.designsystem.theme.LightGrayColor

@Composable
fun CalendarHeader(
    month: String,
    year: String,
    onMonthClick: () -> Unit,
    onTodayClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(20.dp))
                .background(LightGrayColor)
                .clickable(onClick = onMonthClick)
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_calendar),
                    contentDescription = null,
                    tint = Black,
                    modifier = Modifier.size(20.dp)
                )

                Text(
                    text = "$month $year",
                    fontFamily = GoogleSansFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Black,
                    lineHeight = 22.sp
                )
            }

            Icon(
                painter = painterResource(R.drawable.ic_arrow_down),
                contentDescription = null,
                tint = Black,
                modifier = Modifier.size(20.dp)
            )
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(LightGrayColor)
                .clickable(onClick = onTodayClick)
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Text(
                text = "Сегодня",
                fontFamily = GoogleSansFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Black,
                lineHeight = 22.sp
            )
        }
    }
}

@Preview
@Composable
fun CalendarHeaderPreview() {
    GainlyTheme {
        CalendarHeader(
            month = "Август",
            year = "2025",
            onMonthClick = {},
            onTodayClick = {},
        )
    }
}
