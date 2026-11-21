package com.jacqulin.gainly.feature.workout.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.extensions.fadeScrollableVerticalEdges
import com.jacqulin.gainly.core.designsystem.theme.Black
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.core.designsystem.theme.SelectedToRemoveLightRed
import com.jacqulin.gainly.core.designsystem.theme.White
import com.jacqulin.gainly.feature.workout.presentation.model.CreateWorkoutUiState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WorkoutExercisesPage(
    state: CreateWorkoutUiState.Content,
    isSelectionMode: Boolean,
    modifier: Modifier = Modifier,
    isKeyboardVisible: Boolean = false,
    onAddExercise: () -> Unit,
    onToggleExpanded: (String) -> Unit,
    onToggleSelect: (String) -> Unit,
    onToggleSetSelect: (exerciseId: String, setId: String) -> Unit,
    onClearSelection: () -> Unit,
    onDeleteSelected: () -> Unit,
    onSetWeightChanged: (exerciseId: String, setId: String, weight: String) -> Unit,
    onSetRepetitionsChanged: (exerciseId: String, setId: String, repetitions: String) -> Unit,
    onAddSet: (exerciseId: String) -> Unit,
    onDeleteSelectedSets: (exerciseId: String) -> Unit,
    onExerciseTitleChanged: (exerciseId: String, title: String) -> Unit,
    onBodyWeightChanged: (exerciseId: String, isBodyWeight: Boolean) -> Unit
) {
    DisposableEffect(Unit) {
        onDispose {
            onClearSelection()
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (isSelectionMode) "Выбрано: ${state.selectedExerciseIds.size}" else "Упражнения",
                style = TextStyle(
                    fontFamily = GoogleSansFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    lineHeight = 22.sp,
                    letterSpacing = 0.sp,
                    color = state.selectedColor,
                    textAlign = TextAlign.Center
                )
            )

            if (isSelectionMode) {
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .widthIn(min = 60.dp)
                        .clip(RoundedCornerShape(32.dp))
                        .background(SelectedToRemoveLightRed)
                        .clickable(onClick = onDeleteSelected),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_delete_24),
                            contentDescription = "Удалить",
                            tint = White,
                            modifier = Modifier.height(16.dp)
                        )
                    }
                }
            }
        }

        val listState = rememberLazyListState()

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .then(
                    if (isKeyboardVisible) {
                        Modifier
                    } else {
                        Modifier.fadeScrollableVerticalEdges(
                            listState = listState,
                            fadeLength = 24.dp
                        )
                    }
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            overscrollEffect = null
        ) {
            items(
                items = state.exercises,
                key = { it.id }
            ) { exercise ->
                ExerciseItem(
                    exercise = exercise,
                    isSelected = state.selectedExerciseIds.contains(exercise.id),
                    selectionMode = isSelectionMode,
                    selectedColor = state.selectedColor,
                    selectedSetIds = exercise.selectedSetIds,
                    invalidRepsSetIds = state.invalidRepsSetIds,
                    invalidWeightSetIds = state.invalidWeightSetIds,
                    invalidExerciseTitleIds = state.invalidExerciseTitleIds,
                    onToggleExpanded = { onToggleExpanded(exercise.id) },
                    onToggleSelect = { onToggleSelect(exercise.id) },
                    onToggleSetSelect = { setId ->
                        onToggleSetSelect(exercise.id, setId)
                    },
                    onSetWeightChanged = { setId, weight ->
                        onSetWeightChanged(exercise.id, setId, weight)
                    },
                    onSetRepetitionsChanged = { setId, repetitions ->
                        onSetRepetitionsChanged(exercise.id, setId, repetitions)
                    },
                    onAddSet = { onAddSet(exercise.id) },
                    onDeleteSelectedSets = { onDeleteSelectedSets(exercise.id) },
                    onExerciseTitleChanged = onExerciseTitleChanged,
                    onBodyWeightChanged = onBodyWeightChanged
                )
            }

            if (!isSelectionMode) {
                item {
                    AddButton(
                        text = "Добавить упражнение",
                        modifier = Modifier.padding(bottom = 20.dp),
                        contentColor = Black,
                        onClick = onAddExercise
                    )
                }
            }
        }
    }
}
