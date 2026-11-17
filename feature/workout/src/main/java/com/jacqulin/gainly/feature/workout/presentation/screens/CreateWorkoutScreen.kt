package com.jacqulin.gainly.feature.workout.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.theme.GrayBackgroundMain
import com.jacqulin.gainly.feature.workout.presentation.CreateWorkoutViewModel
import com.jacqulin.gainly.feature.workout.presentation.components.SaveButton
import com.jacqulin.gainly.feature.workout.presentation.components.WorkoutExercisesPage
import com.jacqulin.gainly.feature.workout.presentation.components.WorkoutInfoPage
import com.jacqulin.gainly.feature.workout.presentation.components.WorkoutTopBar
import com.jacqulin.gainly.feature.workout.presentation.model.CreateWorkoutEvent
import com.jacqulin.gainly.feature.workout.presentation.model.CreateWorkoutUiState

@Composable
fun CreateWorkoutScreenRoute(
    viewModel: CreateWorkoutViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 2 }
    )

    when (val currentState = state) {
        is CreateWorkoutUiState.Loading -> {
            CircularProgressIndicator()
        }

        is CreateWorkoutUiState.Content -> {
            CreateWorkoutScreen(
                state = currentState,
                pagerState = pagerState,
                onEvent = viewModel::onEvent,
                onBackClick = onBackClick
            )
        }

        is CreateWorkoutUiState.Error -> {
            Text(currentState.message)
        }
    }
}

@Composable
fun CreateWorkoutScreen(
    state: CreateWorkoutUiState.Content,
    pagerState: PagerState,
    onEvent: (CreateWorkoutEvent) -> Unit,
    onBackClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    val isSelectionMode = state.selectedExerciseIds.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrayBackgroundMain)
            .padding(12.dp)
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                }
            }
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
                0 -> WorkoutInfoPage(
                    state = state,
                    onTypeSelected = { type ->
//                        onEvent(CreateWorkoutEvent.OnWorkoutTypeChanged(type))
                    },
                    onTitleChanged = { title ->
                        onEvent(CreateWorkoutEvent.OnWorkoutTitleChanged(title))
                    },
                    onDateChanged = { date ->
                        onEvent(CreateWorkoutEvent.OnDateChanged(date))
                    },
                    onTimeChanged = { time ->
                        onEvent(CreateWorkoutEvent.OnTimeChanged(time))
                    },
                    onNoteChanged = {},
                )
                1 -> WorkoutExercisesPage(
                    state = state,
                    isSelectionMode = isSelectionMode,
                    onAddExercise = { onEvent(CreateWorkoutEvent.OnCreateExercise) },
                    onToggleExpanded = { exerciseId ->
                        onEvent(CreateWorkoutEvent.OnExerciseExpanded(exerciseId))
                    },
                    onToggleSelect = { exerciseId ->
                        onEvent(CreateWorkoutEvent.OnToggleExerciseSelection(exerciseId))
                    },
                    onToggleSetSelect = { exerciseId, setId ->
                        onEvent(CreateWorkoutEvent.OnToggleExercisesSetsSelection(exerciseId, setId))
                    },
                    onClearSelection = { onEvent(CreateWorkoutEvent.OnClearExerciseSelection) },
                    onDeleteSelected = { onEvent(CreateWorkoutEvent.OnDeleteSelectedExercises) },
                    onSetWeightChanged = { exerciseId, setId, weight ->
                        onEvent(CreateWorkoutEvent.OnSetWeightChanged(exerciseId, setId, weight))
                    },
                    onSetRepetitionsChanged = { exerciseId, setId, reps ->
                        onEvent(CreateWorkoutEvent.OnSetRepetitionsChanged(exerciseId, setId, reps))
                    },
                    onAddSet = { exerciseId ->
                        onEvent(CreateWorkoutEvent.OnSetAdded(exerciseId))
                    },
                    onDeleteSelectedSets = { exerciseId ->
                        onEvent(CreateWorkoutEvent.OnDeleteSelectedSets(exerciseId))
                    },
                    onExerciseTitleChanged = { exerciseId, title ->
                        onEvent(CreateWorkoutEvent.OnExerciseTitleChanged(exerciseId, title))
                    },
                    onBodyWeightChanged = {exerciseId, isBodyWeight ->
                        onEvent(CreateWorkoutEvent.OnBodyWeightChanged(exerciseId, isBodyWeight))
                    }
                )
            }
        }


        if (!isSelectionMode) {
            SaveButton(
                enabled = state.exercises.isNotEmpty(),
                text = "Сохранить",
                onClick = {}
            )
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
            state = CreateWorkoutUiState.Content(),
            pagerState = pagerState,
            onEvent = {},
            onBackClick = {}
        )
    }
}
