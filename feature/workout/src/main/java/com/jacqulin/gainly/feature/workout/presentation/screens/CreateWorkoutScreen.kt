package com.jacqulin.gainly.feature.workout.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.theme.GrayBackgroundMain
import com.jacqulin.gainly.feature.workout.presentation.AddWorkoutViewModel
import com.jacqulin.gainly.feature.workout.presentation.components.WorkoutTopBar
import com.jacqulin.gainly.feature.workout.presentation.model.WorkoutState

@Composable
fun CreateWorkoutScreenRoute(
    viewModel: AddWorkoutViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    CreateWorkoutScreen(
        state = state,
        onBackClick = onBackClick
    )

}

@Composable
fun CreateWorkoutScreen(
    state: WorkoutState,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrayBackgroundMain)
    ) {
        WorkoutTopBar(
            title = "Новая тренировка",
            stepText = "О тренировке",
            currentStep = 1,
            onBackClick = onBackClick,
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun CreateWorkoutScreenPreview() {
    GainlyTheme {
        CreateWorkoutScreen(
            state = WorkoutState(),
            onBackClick = {}
        )
    }
}
