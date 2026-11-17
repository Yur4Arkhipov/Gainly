package com.jacqulin.gainly.feature.workout.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.Black
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.core.designsystem.theme.SelectedToRemoveBlue
import com.jacqulin.gainly.core.designsystem.theme.SelectedToRemoveWhite
import com.jacqulin.gainly.core.designsystem.theme.UnselectedAddWorkoutItem
import com.jacqulin.gainly.core.designsystem.theme.White
import com.jacqulin.gainly.feature.workout.presentation.model.WorkoutExerciseUi

@Composable
fun ExerciseItem(
    exercise: WorkoutExerciseUi,
    isSelected: Boolean,
    selectionMode: Boolean,
    selectedColor: Color,
    selectedSetIds: Set<String>,
    invalidRepsSetIds: Set<String>,
    invalidWeightSetIds: Set<String>,
    invalidExerciseTitleIds: Set<String>,
    onToggleExpanded: () -> Unit,
    onToggleSelect: () -> Unit,
    onToggleSetSelect: (setId: String) -> Unit,
    onDeleteSelectedSets: (exerciseId: String) -> Unit,
    onSetWeightChanged: (setId: String, weight: String) -> Unit,
    onSetRepetitionsChanged: (setId: String, repetitions: String) -> Unit,
    onAddSet: () -> Unit,
    onExerciseTitleChanged: (exerciseId: String, title: String) -> Unit,
    onBodyWeightChanged: (exerciseId: String, isBodyWeight: Boolean) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    var isEditingTitle by remember(exercise.id) { mutableStateOf(false) }
    var titleFieldValue by remember(exercise.id) {
        mutableStateOf(
            TextFieldValue(
                text = exercise.title,
                selection = TextRange(exercise.title.length)
            )
        )
    }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(isEditingTitle) {
        if (isEditingTitle) {
            focusRequester.requestFocus()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(
                if (isSelected) SelectedToRemoveWhite else White
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .combinedClickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = {
                        if (selectionMode) {
                            onToggleSelect()
                        } else {
                            onToggleExpanded()
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
            Row(
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        if (!focusState.isFocused && isEditingTitle) {
                            isEditingTitle = false
                        }
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isEditingTitle) {
                    BasicTextField(
                        value = titleFieldValue,
                        onValueChange = { newValue ->
                            if (newValue.text.length <= 30) {
                                titleFieldValue = newValue
                                onExerciseTitleChanged(exercise.id, newValue.text)
                            }
                        },
                        textStyle = TextStyle(
                            fontFamily = GoogleSansFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            lineHeight = 18.sp,
                            color = selectedColor
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .focusRequester(focusRequester),
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            capitalization = KeyboardCapitalization.Sentences
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                isEditingTitle = false
                                keyboardController?.hide()
                            }
                        )
                    )
                } else {
                    Text(
                        text = exercise.title,
                        style = TextStyle(
                            fontFamily = GoogleSansFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            lineHeight = 18.sp,
                            color = selectedColor
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                }

                Icon(
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = "Редактировать название",
                    tint = if (exercise.id in invalidExerciseTitleIds) {
                        Color.Red
                    } else {
                        selectedColor.copy(alpha = 0.5f)
                    },
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(20.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            titleFieldValue = TextFieldValue(
                                text = exercise.title,
                                selection = TextRange(exercise.title.length)
                            )
                            isEditingTitle = true
                        }
                )
            }

            Spacer(Modifier.width(12.dp))

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
        AnimatedVisibility(
            visible = exercise.isExpanded && !selectionMode,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            ExerciseSetsContent(
                exercise = exercise,
                selectedColor = selectedColor,
                isSelectionMode = selectedSetIds.isNotEmpty(),
                selectedSetIds = selectedSetIds,
                onBodyWeightChanged = onBodyWeightChanged,
                invalidRepsSetIds = invalidRepsSetIds,
                invalidWeightSetIds = invalidWeightSetIds,
                onDeleteSelectedSets = {
                    onDeleteSelectedSets(exercise.id)
                },
                onAddSet = onAddSet,
                onRepsChanged = { setId, repetitions ->
                    onSetRepetitionsChanged(setId, repetitions.toString())
                },
                onWeightChanged = { setId, weight ->
                    onSetWeightChanged(setId, weight.toString())
                },
                onToggleSelect = { setId ->
                    onToggleSetSelect(setId)
                }
            )
        }
    }
}
