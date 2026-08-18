package com.jacqulin.gainly.feature.workout.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.Black
import com.jacqulin.gainly.core.designsystem.theme.BottomSheetContainerColor
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.core.designsystem.theme.SelectedToRemoveBlue
import com.jacqulin.gainly.core.designsystem.theme.SelectedToRemoveLightRed
import com.jacqulin.gainly.core.designsystem.theme.SelectedToRemoveWhite
import com.jacqulin.gainly.core.designsystem.theme.UnselectedAddWorkoutItem
import com.jacqulin.gainly.core.designsystem.theme.White
import com.jacqulin.gainly.feature.workout.ui.components.AddButton
import com.jacqulin.gainly.feature.workout.ui.components.SaveButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExercisesListBottomSheet(
    exercises: List<Exercise>,
    selectedColor: Color,
    selectedExerciseIds: Set<String>,
    onAddExercise: () -> Unit,
    onEditExercise: (String) -> Unit,
    onToggleSelect: (String) -> Unit,
    onClearSelection: () -> Unit,
    onDeleteSelected: () -> Unit,
    onSave: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val isSelectionMode = selectedExerciseIds.isNotEmpty()

    DisposableEffect(Unit) {
        onDispose {
            onClearSelection()
        }
    }

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState,
        containerColor = BottomSheetContainerColor,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (isSelectionMode) "Выбрано: ${selectedExerciseIds.size}" else "Упражнения",
                    style = TextStyle(
                        fontFamily = GoogleSansFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        lineHeight = 28.sp,
                        letterSpacing = 0.sp,
                        color = selectedColor,
                        textAlign = TextAlign.Center
                    )
                )

                if (isSelectionMode) {
                    Box(
                        modifier = Modifier
                            .height(40.dp)
                            .widthIn(min = 80.dp)
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
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Удалить",
                                tint = White,
                                modifier = Modifier.height(16.dp)
                            )
                        }
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(exercises, key = { it.id }) { exercise ->
                    ExerciseItem(
                        exercise = exercise,
                        isSelected = selectedExerciseIds.contains(exercise.id),
                        selectionMode = isSelectionMode,
                        onClick = { onEditExercise(exercise.id) },
                        onToggleSelect = { onToggleSelect(exercise.id) }
                    )
                }

                if (!isSelectionMode) {
                    item {
                        AddButton(
                            text = "Добавить упражнение",
                            onClick = onAddExercise
                        )
                    }
                }
            }

            if (!isSelectionMode) {
                SaveButton(
                    onClick = {
                        onSave()
                        onDismiss()
                    },
                    text = "Сохранить"
                )
            }
        }
    }
}

@Composable
private fun ExerciseItem(
    exercise: Exercise,
    isSelected: Boolean,
    selectionMode: Boolean,
    onClick: () -> Unit,
    onToggleSelect: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(
                if (isSelected) SelectedToRemoveWhite else White
            )
            .combinedClickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    if (selectionMode) {
                        onToggleSelect()
                    } else {
                        onClick()
                    }
                },
                onLongClick = {
                    onToggleSelect()
                }
            )
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = exercise.name,
            style = TextStyle(
                fontFamily = GoogleSansFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.sp,
                color = Black
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )

        Box(
            modifier = Modifier
                .height(40.dp)
                .widthIn(min = 80.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(
                    if (isSelected) SelectedToRemoveBlue else UnselectedAddWorkoutItem
                ),
            contentAlignment = Alignment.Center
        ) {

            if (isSelected && selectionMode) {
                Icon(
                    painter = painterResource(R.drawable.ic_checkmark),
                    contentDescription = "Selected",
                    tint = White,
                    modifier = Modifier.size(16.dp)
                )
            } else {
                Text(
                    text = exercise.sets.size.toString(),
                    style = TextStyle(
                        fontFamily = GoogleSansFontFamily,
                        fontSize = 14.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 0.sp,
                        color = if (isSelected) White else Black
                    ),
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }
    }
}