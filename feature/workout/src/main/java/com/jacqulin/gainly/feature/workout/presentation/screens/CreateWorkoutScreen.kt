package com.jacqulin.gainly.feature.workout.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.theme.GrayBackgroundMain
import com.jacqulin.gainly.feature.workout.presentation.AddWorkoutViewModel
import com.jacqulin.gainly.feature.workout.presentation.components.WorkoutExercisesStep
import com.jacqulin.gainly.feature.workout.presentation.components.WorkoutInfoStep
import com.jacqulin.gainly.feature.workout.presentation.components.WorkoutTopBar
import com.jacqulin.gainly.feature.workout.presentation.model.WorkoutState

@Composable
fun CreateWorkoutScreenRoute(
    viewModel: AddWorkoutViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 2 }
    )

    CreateWorkoutScreen(
        state = state,
        pagerState = pagerState,
        onBackClick = onBackClick
    )
}

@Composable
fun CreateWorkoutScreen(
    state: WorkoutState,
    pagerState: PagerState,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrayBackgroundMain)
    ) {
        WorkoutTopBar(
            title = "Новая тренировка",
            stepText = if (pagerState.currentPage == 0) {
                "О тренировке"
            } else {
                "Упражнения"
            },
            currentStep = pagerState.currentPage + 1,
            onBackClick = onBackClick,
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            when (page) {
                0 -> WorkoutInfoStep(state = state)
                1 -> WorkoutExercisesStep(state = state)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CreateWorkoutScreenPreview() {
    GainlyTheme {
        val pagerState = rememberPagerState(
            initialPage = 0,
            pageCount = { 2 }
        )

        CreateWorkoutScreen(
            state = WorkoutState(),
            pagerState = pagerState,
            onBackClick = {}
        )
    }
}
