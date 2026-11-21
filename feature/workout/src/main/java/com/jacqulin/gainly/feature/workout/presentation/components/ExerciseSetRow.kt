package com.jacqulin.gainly.feature.workout.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.Black
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.core.designsystem.theme.Red
import com.jacqulin.gainly.core.designsystem.theme.SelectedToRemoveBlue
import com.jacqulin.gainly.core.designsystem.theme.SelectedToRemoveWhite
import com.jacqulin.gainly.core.designsystem.theme.UnselectedAddWorkoutItem
import com.jacqulin.gainly.core.designsystem.theme.White
import com.jacqulin.gainly.feature.workout.presentation.model.WorkoutSetUi

@Composable
fun ExerciseSetRow(
    set: WorkoutSetUi,
    isSelected: Boolean,
    selectionMode: Boolean,
    isBodyWeight: Boolean,
    isRepsError: Boolean,
    isWeightError: Boolean,
    onRepsChanged: (Int) -> Unit,
    onWeightChanged: (Int) -> Unit,
    onToggleSelect: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val repsFocusRequester = remember { FocusRequester() }
    val weightFocusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    if (selectionMode) {
                        onToggleSelect()
                    }
                },
                onLongClick = {
                    onToggleSelect()
                }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
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
                    text = set.number.toString(),
                    style = TextStyle(
                        fontFamily = GoogleSansFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        lineHeight = 16.sp,
                        letterSpacing = 0.sp,
                        color = if (isSelected) White else Black
                    )
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .height(56.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(
                    color = if (isSelected) SelectedToRemoveWhite
                    else UnselectedAddWorkoutItem,
                    shape = RoundedCornerShape(32.dp)
                )
                .border(
                    width = if (isRepsError) 1.dp else 0.dp,
                    color = if (isRepsError) Red else Color.Transparent,
                    shape = RoundedCornerShape(32.dp)
                )
                .focusRequester(repsFocusRequester),
            contentAlignment = Alignment.Center
        ) {
            BasicTextField(
                value = if (set.reps <= 0) "" else set.reps.toString(),
                onValueChange = { value ->
                    val newValue = value.filter { it.isDigit() }
                    onRepsChanged(newValue.toIntOrNull() ?: 0)
                },
                enabled = !isSelected && !selectionMode,
                textStyle = TextStyle(
                    fontFamily = GoogleSansFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 0.sp,
                    color = Black,
                    textAlign = TextAlign.Center
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = if (isBodyWeight) ImeAction.Done else ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        weightFocusRequester.requestFocus()
                    },
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (!isBodyWeight) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(
                        color = if (isSelected) SelectedToRemoveWhite
                        else UnselectedAddWorkoutItem,
                        shape = RoundedCornerShape(32.dp)
                    )
                    .border(
                        width = if (isWeightError) 1.dp else 0.dp,
                        color = if (isWeightError) Red else Color.Transparent,
                        shape = RoundedCornerShape(32.dp)
                    )
                    .focusRequester(weightFocusRequester),
                contentAlignment = Alignment.Center
            ) {
                BasicTextField(
                    value = if (set.weight <= 0) "" else set.weight.toString(),
                    onValueChange = { value ->
                        val newValue = value.filter { it.isDigit() }
                        onWeightChanged(newValue.toIntOrNull() ?: 0)
                    },
                    enabled = !isSelected && !selectionMode,
                    textStyle = TextStyle(
                        fontFamily = GoogleSansFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        lineHeight = 16.sp,
                        letterSpacing = 0.sp,
                        color = Black,
                        textAlign = TextAlign.Center
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
