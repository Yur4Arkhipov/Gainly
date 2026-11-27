package com.jacqulin.gainly.feature.history.presentation.sections.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.Black
import com.jacqulin.gainly.core.designsystem.theme.GrayTextColor
import com.jacqulin.gainly.core.designsystem.theme.LightGrayColor
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.OutDateStyle
import com.kizitonwose.calendar.core.daysOfWeek
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.util.Locale
import java.time.format.TextStyle.FULL_STANDALONE as FullStandalone
import java.time.format.TextStyle.SHORT as Short

@Composable
fun HistoryMonthCalendar(
    selectedDate: LocalDate,
    workoutDates: Set<LocalDate>,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    val calendarState = rememberCalendarState(
        startMonth = YearMonth.of(2025, 1),
        endMonth = YearMonth.of(2028, 1),
        firstVisibleMonth = YearMonth.from(selectedDate),
        firstDayOfWeek = DayOfWeek.MONDAY,
        outDateStyle = OutDateStyle.EndOfGrid
    )

    val displayedMonth by remember {
        derivedStateOf {
            val info = calendarState.layoutInfo
            val viewportCenter = (info.viewportStartOffset + info.viewportEndOffset) / 2

            info.visibleItemsInfo
                .firstOrNull { item ->
                    viewportCenter in item.offset..(item.offset + item.size)
                }
                ?.key as? YearMonth
                ?: calendarState.firstVisibleMonth.yearMonth
        }
    }

    val scope = rememberCoroutineScope()

    val daysOfWeek = remember {
        daysOfWeek(firstDayOfWeek = DayOfWeek.MONDAY)
    }
    val locale = remember { Locale.forLanguageTag("ru-RU") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .clip(CircleShape)
                    .background(LightGrayColor)
                    .padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = displayedMonth.month
                        .getDisplayName(FullStandalone, locale)
                        .replaceFirstChar {
                            it.titlecase(locale)
                        } + " ${displayedMonth.year}",
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = Black
                    )
                )
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(LightGrayColor)
                    .clickable {
                        scope.launch {
                            calendarState.animateScrollToMonth(
                                displayedMonth.minusMonths(1)
                            )
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_left_calendar),
                    contentDescription = "Предыдущий месяц",
                    tint = Black,
                    modifier = Modifier.size(14.dp)
                )
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(LightGrayColor)
                    .clickable {
                        scope.launch {
                            calendarState.animateScrollToMonth(
                                displayedMonth.plusMonths(1)
                            )
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_right_calendar),
                    contentDescription = "Следующий месяц",
                    tint = Black,
                    modifier = Modifier.size(14.dp)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            daysOfWeek.forEach { dayOfWeek ->
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = dayOfWeek
                            .getDisplayName(Short, locale)
                            .replaceFirstChar {
                                it.titlecase(locale)
                            },
                        fontSize = 10.sp,
                        color = GrayTextColor
                    )
                }
            }
        }

        HorizontalCalendar(
            state = calendarState,
            calendarScrollPaged = true,
            modifier = Modifier.fillMaxWidth(),
            dayContent = { day ->
                HistoryCalendarDay(
                    day = day,
                    isSelected = day.date == selectedDate,
                    hasWorkout = day.date in workoutDates,
                    onClick = {
                        onDateSelected(day.date)
                    }
                )
            }
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    widthDp = 380
)
@Composable
private fun HistoryMonthCalendarPreview() {
    HistoryMonthCalendar(
        selectedDate = LocalDate.of(2026, 11, 21),
        workoutDates = setOf(
            LocalDate.of(2026, 11, 3),
            LocalDate.of(2026, 11, 5),
            LocalDate.of(2026, 11, 8),
            LocalDate.of(2026, 11, 12),
            LocalDate.of(2026, 11, 15),
            LocalDate.of(2026, 11, 21),
            LocalDate.of(2026, 11, 25),
            LocalDate.of(2026, 11, 28)
        ),
        onDateSelected = {}
    )
}
