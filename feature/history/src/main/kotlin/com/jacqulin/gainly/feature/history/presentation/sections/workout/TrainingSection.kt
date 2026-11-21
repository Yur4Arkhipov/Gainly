package com.jacqulin.gainly.feature.history.presentation.sections.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.jacqulin.gainly.feature.history.presentation.model.WorkoutCardUi
import java.time.LocalDate

@Composable
fun TrainingSection(
    trainings: List<WorkoutCardUi>,
    selectedDate: LocalDate,
    onChooseAnotherDay: () -> Unit,
    onTodayClick: () -> Unit
) {
    if (trainings.isEmpty()) {
        EmptyTrainingsCard(
            selectedDate = selectedDate,
            onChooseAnotherDay = onChooseAnotherDay,
            onTodayClick = onTodayClick
        )
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            trainings.forEach { workout ->
                WorkoutCard(
                    workoutTitle = workout.title,
                    exercises = workout.exercises
                )
            }
        }
    }
}
