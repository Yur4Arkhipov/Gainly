package com.jacqulin.gainly.feature.history.presentation.sections.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jacqulin.gainly.core.designsystem.extensions.fadeScrollableVerticalEdges
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
        val listState = rememberLazyListState()

        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            overscrollEffect = null,
            modifier = Modifier
                .fadeScrollableVerticalEdges(
                    listState = listState,
                    fadeLength = 24.dp
                )
        ) {
            items(
                items = trainings,
                key = { it.id }
            ) { workout ->
                WorkoutCard(
                    workoutTitle = workout.title,
                    exercises = workout.exercises
                )
            }
        }
    }
}
