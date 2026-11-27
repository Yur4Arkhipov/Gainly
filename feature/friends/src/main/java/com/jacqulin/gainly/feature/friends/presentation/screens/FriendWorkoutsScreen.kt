package com.jacqulin.gainly.feature.friends.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.theme.LightGrayColor
import com.jacqulin.gainly.core.designsystem.theme.White
import com.jacqulin.gainly.core.domain.model.workout.Exercise
import com.jacqulin.gainly.core.domain.model.workout.Workout
import com.jacqulin.gainly.feature.friends.presentation.components.FriendWorkoutsTopBar
import com.jacqulin.gainly.feature.friends.presentation.components.WorkoutsSection
import com.jacqulin.gainly.feature.friends.presentation.model.FriendWorkoutsUiState
import com.jacqulin.gainly.feature.friends.presentation.model.WorkoutsUiState
import com.jacqulin.gainly.feature.friends.presentation.viewmodels.FriendWorkoutsViewModel

@Composable
fun FriendWorkoutsScreenRoute(
    viewModel: FriendWorkoutsViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    FriendWorkoutsScreen(
        state = state,
        onBackClick = onBackClick,
        onRetry = viewModel::loadWorkouts
    )
}

@Composable
fun FriendWorkoutsScreen(
    state: FriendWorkoutsUiState,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White)
    ) {
        FriendWorkoutsTopBar(
            username = state.username,
            modifier = Modifier
                .padding(
                    vertical = 20.dp,
                    horizontal = 10.dp
                ),
            onBackClick = onBackClick
        )

        WorkoutsSection(
            workoutsState = state.workoutsState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(
                    color = LightGrayColor,
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                )
                .padding(horizontal = 10.dp),
            onRetry = onRetry
        )
    }
}

@Preview
@Composable
fun FriendWorkoutsScreenPreview_Loading() {
    GainlyTheme {
        FriendWorkoutsScreen(
            state = FriendWorkoutsUiState(
                username = "username",
            ),
            onBackClick = {},
            onRetry = {}
        )
    }
}

@Preview
@Composable
fun FriendWorkoutsScreenPreview_Success() {
    GainlyTheme {
        FriendWorkoutsScreen(
            state = FriendWorkoutsUiState(
                username = "username",
                workoutsState = WorkoutsUiState.Success(
                    workouts = listOf(
                        Workout(
                            title = "Тренировка верхней части тела",
                            date = "19.08.2008",
                            exercises = listOf(
                                Exercise(
                                    "Жим лёжа",
                                    listOf()
                                ),
                                Exercise(
                                    "Тяга штанги",
                                    listOf()
                                ),
                                Exercise(
                                    "Подтягивания",
                                    listOf()
                                )
                            )
                        )
                    )
                ),
            ),
            onBackClick = {},
            onRetry = {}
        )
    }
}

@Preview
@Composable
fun FriendWorkoutsScreenPreview_Error() {
    GainlyTheme {
        FriendWorkoutsScreen(
            state = FriendWorkoutsUiState(
                username = "username",
                workoutsState = WorkoutsUiState.Error("Error")
            ),
            onBackClick = {},
            onRetry = {}
        )
    }
}
