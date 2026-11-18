package com.jacqulin.gainly.feature.history.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.theme.LightGrayColor
import com.jacqulin.gainly.core.designsystem.theme.White
import com.jacqulin.gainly.feature.history.presentation.model.HistoryEvent
import com.jacqulin.gainly.feature.history.presentation.model.HistoryViewState
import com.jacqulin.gainly.feature.history.presentation.sections.calendar.CalendarSection
import com.jacqulin.gainly.feature.history.presentation.sections.main.HistoryTopBar
import com.jacqulin.gainly.feature.history.presentation.sections.main.WorkoutsSection
import java.time.LocalDate

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HistoryScreen(
        state = state,
    )
}

@Composable
fun HistoryScreen(
    state: HistoryViewState,
    modifier: Modifier = Modifier,
    onEvent: (HistoryEvent) -> Unit = {},
) {
//    val workouts = getMockTrainingCards(LocalDate.now())
//    Log.d("HistoryScreen", "workouts: $workouts")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            HistoryTopBar(
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }

        item {
            CalendarSection(
                days = state.calendarDays,
                selectedDate = state.selectedDate,
                month = "May",
                year = "2026",
                modifier = Modifier.padding(horizontal = 10.dp),
                onDateSelected = {
                },
                onMonthClick = {},
                onTodayClick = {}
            )
        }

        item {
            WorkoutsSection(
                workouts = state.workouts,
                modifier = Modifier
                    .background(
                        color = LightGrayColor,
                        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                    )
                    .padding(horizontal = 10.dp)
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun HistoryScreenPreview() {
    GainlyTheme {
        HistoryScreen(
            state = HistoryViewState(
                selectedDate = LocalDate.now(),
                workouts = listOf(
//                    WorkoutCardUi(),
//                    WorkoutCardUi()
                )
            )
        )
    }
}
