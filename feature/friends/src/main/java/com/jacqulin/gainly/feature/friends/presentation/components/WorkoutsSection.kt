package com.jacqulin.gainly.feature.friends.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.domain.model.workout.Exercise
import com.jacqulin.gainly.core.domain.model.workout.Workout
import com.jacqulin.gainly.feature.friends.presentation.model.WorkoutsUiState
import com.jacqulin.gainly.core.domain.model.workout.Set

@Composable
fun WorkoutsSection(
    workoutsState: WorkoutsUiState,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(vertical = 20.dp),
        ) {
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
                    )
                }

                is WorkoutsUiState.Error -> {
                    WorkoutErrorContent(
                        errorCause = workoutsState.message,
                        onRetry = onRetry
                    )
                }
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
                    Workout(
                        title = "Тренировка верхней части тела",
                        date = "19.08.2008",
                        exercises = listOf(
                            Exercise(
                                "Жим лёжа",
                                listOf(
                                    Set(12, 40, false),
                                    Set(10, 50, false),
                                    Set(8, 60, false)
                                )
                            ),
                            Exercise(
                                "Тяга штанги",
                                listOf(
                                    Set(12, 30, false),
                                    Set(10, 40, false),
                                    Set(10, 40, false)
                                )
                            ),
                            Exercise(
                                "Подтягивания",
                                listOf(
                                    Set(10, 1, isOwnWeight = true),
                                    Set(8, 2, isOwnWeight = true),
                                    Set(6, 3, isOwnWeight = true)
                                )
                            )
                        )
                    )
                ),
            ),
            onRetry = {}
        )
    }
}
