package com.jacqulin.gainly.feature.history.presentation.sections.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.feature.history.presentation.model.ExerciseInfoUi
import com.jacqulin.gainly.feature.history.presentation.model.SetInfoUi
import com.jacqulin.gainly.feature.history.presentation.model.WorkoutCardUi
import com.jacqulin.gainly.feature.history.presentation.model.WorkoutsUiState
import com.jacqulin.gainly.feature.history.presentation.sections.workout.TrainingSection
import com.jacqulin.gainly.feature.history.presentation.sections.workout.WorkoutCardShimmer
import java.time.LocalDate

@Composable
fun WorkoutsSection(
    workoutsState: WorkoutsUiState,
    selectedDate: LocalDate,
    onChooseAnotherDay: () -> Unit,
    onTodayClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = 20.dp,
                    bottom = 100.dp
                )
        ) {
//            GoalsSection()
//
//            Spacer(Modifier.height(20.dp))

            when (workoutsState) {
                is WorkoutsUiState.Loading -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        repeat(3) {
                            WorkoutCardShimmer()
                        }
                    }
                }

                is WorkoutsUiState.Success -> {
                    TrainingSection(
                        trainings = workoutsState.workouts,
                        selectedDate = selectedDate,
                        onChooseAnotherDay = onChooseAnotherDay,
                        onTodayClick = onTodayClick
                    )
                }

                is WorkoutsUiState.Error -> {}
            }
        }
    }
}

@Preview
@Composable
fun WorkoutSectionPreview() {
    GainlyTheme {
        WorkoutsSection(
            workoutsState = WorkoutsUiState.Success(
                workouts = listOf(
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
                ),
            ),
            selectedDate = LocalDate.now(),
            onTodayClick = {},
            onChooseAnotherDay = {}
        )
    }
}
