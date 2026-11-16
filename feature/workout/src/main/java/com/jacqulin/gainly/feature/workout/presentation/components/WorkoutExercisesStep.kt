package com.jacqulin.gainly.feature.workout.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jacqulin.gainly.feature.workout.presentation.model.CreateWorkoutUiState
import com.jacqulin.gainly.feature.workout.presentation.model.WorkoutState

@Composable
fun WorkoutExercisesStep(
    state: CreateWorkoutUiState.Content
) {
    Column(
        modifier = Modifier.fillMaxSize(),

    ) { }
    Text(
        text = "exercise"
    )
}
