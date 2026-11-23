package com.jacqulin.gainly.feature.history.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.theme.LightGrayColor
import com.jacqulin.gainly.core.designsystem.theme.White
import com.jacqulin.gainly.feature.history.presentation.model.ExerciseInfoUi
import com.jacqulin.gainly.feature.history.presentation.model.HistoryEvent
import com.jacqulin.gainly.feature.history.presentation.model.HistoryViewState
import com.jacqulin.gainly.feature.history.presentation.model.SetInfoUi
import com.jacqulin.gainly.feature.history.presentation.model.WorkoutCardUi
import com.jacqulin.gainly.feature.history.presentation.model.WorkoutsUiState
import com.jacqulin.gainly.feature.history.presentation.sections.calendar.CalendarSection
import com.jacqulin.gainly.feature.history.presentation.sections.calendar.HistoryDatePickerSheet
import com.jacqulin.gainly.feature.history.presentation.sections.main.HistoryTopBar
import com.jacqulin.gainly.feature.history.presentation.sections.main.WorkoutsSection
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HistoryScreen(
        state = state,
        onEvent = viewModel::handleEvent
    )
}

@Composable
fun HistoryScreen(
    state: HistoryViewState,
    modifier: Modifier = Modifier,
    onEvent: (HistoryEvent) -> Unit = {},
) {
    var showDatePicker by rememberSaveable { mutableStateOf(false) }

    val russianLocale = Locale.forLanguageTag("ru-RU")
    val month = state.selectedDate.month
        .getDisplayName(TextStyle.FULL_STANDALONE, russianLocale)
        .replaceFirstChar { it.titlecase(russianLocale) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White),
    ) {
        HistoryTopBar(
            modifier = Modifier
                .padding(
                    vertical = 10.dp,
                    horizontal = 10.dp
                ),
        )

        CalendarSection(
            days = state.calendarDays,
            selectedDate = state.selectedDate,
            month = month,
            year = state.selectedDate.year.toString(),
            modifier = Modifier.padding(horizontal = 10.dp),
            onDateSelected = { date ->
                onEvent(HistoryEvent.DateSelected(date))
            },
            onMonthClick = { showDatePicker = true },
            onTodayClick = {
                onEvent(HistoryEvent.TodayClicked)
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        WorkoutsSection(
            workoutsState = state.workoutsState,
            selectedDate = state.selectedDate,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(
                    color = LightGrayColor,
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                )
                .padding(horizontal = 10.dp),
            onTodayClick = {
                onEvent(HistoryEvent.TodayClicked)
            },
            onChooseAnotherDay = { showDatePicker = true },
            onRetry = {
                onEvent(HistoryEvent.RetryLoading(state.selectedDate))
            },
            onDeleteWorkout = { }
//            onDeleteWorkout = { workoutId ->
//                onEvent(HistoryEvent.DeleteWorkout(workoutId))
//            }
        )
    }

    if (showDatePicker) {
        HistoryDatePickerSheet(
            selectedDate = state.selectedDate,
            onDismiss = { showDatePicker = false },
            onConfirm = { date ->
                onEvent(HistoryEvent.DateSelected(date))
                showDatePicker = false
            }
        )
    }
}

@Composable
@Preview(showSystemUi = true)
fun HistoryScreenPreview() {
    GainlyTheme {
        HistoryScreen(
            state = HistoryViewState(
                selectedDate = LocalDate.now(),
                workoutsState = WorkoutsUiState.Success(
                    listOf(
                        WorkoutCardUi(
                            id = "1",
                            title = "Тренировка верхней части тела",
                            date = "19.08.2008",
                            exercises = listOf(
                                ExerciseInfoUi(
                                    "Жим лёжа",
                                    listOf(
                                        SetInfoUi(12, 40, false),
                                        SetInfoUi(10, 50, false),
                                        SetInfoUi(8, 60, false)
                                    )
                                ),
                                ExerciseInfoUi(
                                    "Тяга штанги",
                                    listOf(
                                        SetInfoUi(12, 30, false),
                                        SetInfoUi(10, 40, false),
                                        SetInfoUi(10, 40, false)
                                    )
                                ),
                                ExerciseInfoUi(
                                    "Подтягивания",
                                    listOf(
                                        SetInfoUi(10, 1, isOwnWeight = true),
                                        SetInfoUi(8, 2, isOwnWeight = true),
                                        SetInfoUi(6, 3, isOwnWeight = true)
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
    }
}
