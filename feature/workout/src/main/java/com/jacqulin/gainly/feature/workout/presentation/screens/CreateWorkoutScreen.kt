package com.jacqulin.gainly.feature.workout.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.theme.GrayBackgroundMain
import com.jacqulin.gainly.core.designsystem.theme.LightBlackTextColor
import com.jacqulin.gainly.core.designsystem.theme.VeryLightGrayColor
import com.jacqulin.gainly.core.designsystem.theme.onboardingCircularIndicatorGradient
import com.jacqulin.gainly.core.util.auth.components.GradientCircularLoadingIndicator
import com.jacqulin.gainly.feature.workout.presentation.CreateWorkoutViewModel
import com.jacqulin.gainly.feature.workout.presentation.components.SaveButton
import com.jacqulin.gainly.feature.workout.presentation.components.WorkoutExercisesPage
import com.jacqulin.gainly.feature.workout.presentation.components.WorkoutInfoPage
import com.jacqulin.gainly.feature.workout.presentation.components.WorkoutTopBar
import com.jacqulin.gainly.feature.workout.presentation.model.CreateWorkoutEffect
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

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel, pagerState) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is CreateWorkoutEffect.NavigateToPage -> {
                    pagerState.animateScrollToPage(effect.page)
                }
                is CreateWorkoutEffect.NavigateToBack -> {
                    onBackClick()
                }
                is CreateWorkoutEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = effect.message
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = VeryLightGrayColor,
                    contentColor = LightBlackTextColor,
                    shape = RoundedCornerShape(16.dp)
                )
            }
        }
    ) { innerPadding ->
        when (val currentState = state) {
            is CreateWorkoutUiState.Loading -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    GradientCircularLoadingIndicator(
                        strokeWidth = 4.dp,
                        progressBrush = Brush.linearGradient(MaterialTheme.colorScheme.onboardingCircularIndicatorGradient),
                        trackColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

            is CreateWorkoutUiState.Content -> {
                CreateWorkoutScreen(
                    state = currentState,
                    pagerState = pagerState,
                    modifier = Modifier.padding(innerPadding),
                    onEvent = viewModel::onEvent,
                    onBackClick = onBackClick
                )
            }

            is CreateWorkoutUiState.Error -> {
                Text(currentState.message)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreateWorkoutScreen(
    state: CreateWorkoutUiState.Content,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    onEvent: (CreateWorkoutEvent) -> Unit,
    onBackClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    val isSelectionMode = state.selectedExerciseIds.isNotEmpty()
    val isKeyboardVisible = WindowInsets.isImeVisible

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(GrayBackgroundMain)
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

        Spacer(Modifier.height(12.dp))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp)
        ) { page ->
            when (page) {
                0 -> WorkoutInfoPage(
                    state = state,
                    isTitleError = state.invalidWorkoutTitle,
                    isTimeError = state.invalidWorkoutTime,
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
                    isKeyboardVisible = isKeyboardVisible,
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

        if (!isSelectionMode && !isKeyboardVisible) {
            SaveButton(
                enabled = state.exercises.isNotEmpty() &&
                        state.exercises.all { it.sets.isNotEmpty() },
                text = "Сохранить",
                onClick = { onEvent(CreateWorkoutEvent.OnSaveWorkout) },
                modifier = Modifier.padding(horizontal = 12.dp)
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
