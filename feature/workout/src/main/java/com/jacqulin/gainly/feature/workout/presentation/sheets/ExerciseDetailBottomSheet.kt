package com.jacqulin.gainly.feature.workout.presentation.sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.Black
import com.jacqulin.gainly.core.designsystem.theme.BottomSheetContainerColor
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.core.designsystem.theme.SelectedToRemoveLightRed
import com.jacqulin.gainly.core.designsystem.theme.UncheckedSwitchTrackColor
import com.jacqulin.gainly.core.designsystem.theme.White
import com.jacqulin.gainly.feature.workout.presentation.components.AddButton
import com.jacqulin.gainly.feature.workout.presentation.components.ExerciseSetRow
import com.jacqulin.gainly.feature.workout.presentation.components.SaveButton
import com.jacqulin.gainly.feature.workout.presentation.model.WorkoutExerciseUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDetailBottomSheet(
    exercise: WorkoutExerciseUi,
    selectedColor: Color,
    selectedSetIndices: Set<Int>,
    onNameChanged: (String) -> Unit,
    onBodyWeightChanged: (Boolean) -> Unit,
    onAddSet: () -> Unit,
    onUpdateSetReps: (Int, Int) -> Unit,
    onUpdateSetWeight: (Int, Int) -> Unit,
    onToggleSetSelect: (Int) -> Unit,
    onClearSetSelection: () -> Unit,
    onDeleteSelectedSets: () -> Unit,
    onSave: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val focusRequester = remember { FocusRequester() }
    val isSelectionMode = selectedSetIndices.isNotEmpty()

    DisposableEffect(Unit) {
        onDispose {
            onClearSetSelection()
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
                .padding(bottom = 20.dp)
                .focusRequester(focusRequester)
                .focusable()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { focusRequester.requestFocus() },
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
                        text = "Выбрано: ${selectedSetIndices.size}",
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
                    Box(
                        modifier = Modifier
                            .height(40.dp)
                            .widthIn(min = 80.dp)
                            .clip(RoundedCornerShape(32.dp))
                            .background(SelectedToRemoveLightRed)
                            .clickable(onClick = onDeleteSelectedSets),
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
                } else {
                    Row(
                        modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BasicTextField(
                                value = exercise.name,
                                onValueChange = { newText ->
                                    if (newText.length <= 24) {
                                        onNameChanged(newText)
                                    }
                                },
                                textStyle = TextStyle(
                                    fontFamily = GoogleSansFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 28.sp,
                                    lineHeight = 28.sp,
                                    letterSpacing = 0.sp,
                                    color = selectedColor
                                ),
                                modifier = Modifier.weight(1f),
                                maxLines = 2,
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done,
                                    capitalization = KeyboardCapitalization.Sentences
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        focusRequester.requestFocus()
                                    }
                                )
                            )

                            Icon(
                                painter = painterResource(R.drawable.ic_edit),
                                contentDescription = "Редактировать",
                                tint = selectedColor.copy(alpha = 0.5f),
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Свой вес",
                                style = TextStyle(
                                    fontFamily = GoogleSansFontFamily,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 12.sp,
                                    lineHeight = 12.sp,
                                    letterSpacing = 0.sp
                                ),
                                color = Black
                            )
                            Switch(
                                checked = exercise.isBodyWeight,
                                onCheckedChange = onBodyWeightChanged,
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = White,
                                    checkedTrackColor = selectedColor,
                                    uncheckedThumbColor = White,
                                    uncheckedTrackColor = UncheckedSwitchTrackColor,
                                    uncheckedBorderColor = Color.Transparent
                                )
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Сеты",
                    style = TextStyle(
                        fontFamily = GoogleSansFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        letterSpacing = 0.sp
                    ),
                    color = Black,
                    modifier = Modifier.width(56.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Повторения",
                    style = TextStyle(
                        fontFamily = GoogleSansFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        letterSpacing = 0.sp
                    ),
                    color = Black,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                if (!exercise.isBodyWeight) {
                    Text(
                        text = "Вес",
                        style = TextStyle(
                            fontFamily = GoogleSansFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            letterSpacing = 0.sp
                        ),
                        color = Black,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(exercise.sets, key = { index, _ -> index }) { index, set ->
                    ExerciseSetRow(
                        set = set,
                        isSelected = selectedSetIndices.contains(index),
                        selectionMode = isSelectionMode,
                        isBodyWeight = exercise.isBodyWeight,
                        onRepsChanged = { onUpdateSetReps(index, it) },
                        onWeightChanged = { onUpdateSetWeight(index, it) },
                        onToggleSelect = { onToggleSetSelect(index) }
                    )
                }

                if (!isSelectionMode) {
                    item {
                        AddButton(
                            text = "Добавить сет",
                            onClick = onAddSet
                        )
                    }
                }
            }

            if (!isSelectionMode) {
                SaveButton(
                    enabled = exercise.sets.isNotEmpty(),
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
