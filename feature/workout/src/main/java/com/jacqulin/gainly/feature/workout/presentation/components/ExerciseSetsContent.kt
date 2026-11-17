package com.jacqulin.gainly.feature.workout.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.Black
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.core.designsystem.theme.LightBlackTextColor
import com.jacqulin.gainly.core.designsystem.theme.SelectedToRemoveLightRed
import com.jacqulin.gainly.core.designsystem.theme.UnselectedAddWorkoutItem
import com.jacqulin.gainly.core.designsystem.theme.White
import com.jacqulin.gainly.feature.workout.presentation.model.WorkoutExerciseUi

@Composable
fun ExerciseSetsContent(
    exercise: WorkoutExerciseUi,
    selectedColor: Color,
    isSelectionMode: Boolean,
    invalidRepsSetIds: Set<String>,
    invalidWeightSetIds: Set<String>,
    selectedSetIds: Set<String>,
    onBodyWeightChanged: (String, Boolean) -> Unit,
    onRepsChanged: (String, Int) -> Unit,
    onWeightChanged: (String, Int) -> Unit,
    onToggleSelect: (String) -> Unit,
    onDeleteSelectedSets: () -> Unit,
    onAddSet: () -> Unit
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
            if (isSelectionMode) {
                Text(
                    text = "Выбрано: ${exercise.selectedSetIds.size}",
                    style = TextStyle(
                        fontFamily = GoogleSansFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        lineHeight = 18.sp,
                        color = selectedColor
                    )
                )

                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .widthIn(min = 80.dp)
                        .clip(RoundedCornerShape(32.dp))
                        .background(SelectedToRemoveLightRed)
                        .clickable(onClick = onDeleteSelectedSets),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_delete_24),
                        contentDescription = "Удалить",
                        tint = White,
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .height(16.dp)
                    )
                }
            } else {
                BodyweightToggleBlock(
                    isChecked = exercise.isBodyWeight,
                    onCheckedChange = { isBodyWeight ->
                        onBodyWeightChanged(exercise.id, isBodyWeight)
                    },
                    selectedColor = selectedColor,
                )
            }
        }

        if (exercise.sets.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Сеты",
                    modifier = Modifier.width(56.dp),
                    textAlign = TextAlign.Center,
                    color = LightBlackTextColor,
                    fontSize = 12.sp
                )

                Text(
                    text = "Повторения",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    color = LightBlackTextColor,
                    fontSize = 12.sp
                )

                if (!exercise.isBodyWeight) {
                    Text(
                        text = "Вес",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        color = LightBlackTextColor,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            exercise.sets.forEach { set ->
                ExerciseSetRow(
                    set = set,
                    isSelected = set.id in selectedSetIds,
                    selectionMode = isSelectionMode,
                    isBodyWeight = exercise.isBodyWeight,
                    isRepsError = set.id in invalidRepsSetIds,
                    isWeightError = set.id in invalidWeightSetIds,
                    onRepsChanged = { newReps ->
                        onRepsChanged(set.id, newReps)
                    },
                    onWeightChanged = { newWeight ->
                        onWeightChanged(set.id, newWeight)
                    },
                    onToggleSelect = {
                        onToggleSelect(set.id)
                    }
                )
            }

            if (!isSelectionMode) {
                AddButton(
                    text = "Добавить сет",
                    onClick = onAddSet,
                    containerColor = UnselectedAddWorkoutItem,
                    contentColor = LightBlackTextColor
                )
            }
        }
    }
}
