package com.jacqulin.gainly.feature.workout.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.theme.Black
import com.jacqulin.gainly.core.designsystem.theme.BottomSheetContainerColor
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.core.designsystem.theme.UncheckedSwitchTrackColor
import com.jacqulin.gainly.core.designsystem.theme.White
import com.jacqulin.gainly.feature.workout.ui.components.SaveButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDetailBottomSheet(
    exercise: Exercise,
    selectedColor: Color,
    onNameChanged: (String) -> Unit,
    onBodyWeightChanged: (Boolean) -> Unit,
    onAddSet: () -> Unit,
    onUpdateSetReps: (Int, Int) -> Unit,
    onUpdateSetWeight: (Int, Int) -> Unit,
    onSave: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val focusManager = LocalFocusManager.current

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = BottomSheetContainerColor,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 20.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { focusManager.clearFocus() },
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = exercise.name,
                    onValueChange = onNameChanged,
                    textStyle = TextStyle(
                        fontFamily = GoogleSansFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        lineHeight = 28.sp,
                        letterSpacing = 0.sp,
                        color = selectedColor
                    ),
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                )

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
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(exercise.sets) { index, set ->
                    ExerciseSetRow(
                        set = set,
                        isBodyWeight = exercise.isBodyWeight,
                        onRepsChanged = { onUpdateSetReps(index, it) },
                        onWeightChanged = { onUpdateSetWeight(index, it) }
                    )
                }

                item {
                    Button(
                        onClick = onAddSet,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(32.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = White,
                            contentColor = Black
                        )
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Добавить сет",
                            style = TextStyle(
                                fontFamily = GoogleSansFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp
                            )
                        )
                    }
                }
            }

            Spacer(Modifier.height(2.dp))

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

@Composable
fun ExerciseSetRow(
    set: WorkoutSet,
    isBodyWeight: Boolean,
    onRepsChanged: (Int) -> Unit,
    onWeightChanged: (Int) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(White),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = set.number.toString(),
                style = TextStyle(
                    fontFamily = GoogleSansFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 0.sp,
                    color = Black
                )
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .height(56.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(White),
            contentAlignment = Alignment.Center
        ) {
            BasicTextField(
                value = if (set.reps <= 0) "" else set.reps.toString(),
                onValueChange = {
                    val newValue = it.filter { char -> char.isDigit() }
                    onRepsChanged(newValue.toIntOrNull() ?: 0)
                },
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
                    onNext = { focusManager.moveFocus(FocusDirection.Next) },
                    onDone = { focusManager.clearFocus() }
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
                    .background(White),
                contentAlignment = Alignment.Center
            ) {
                BasicTextField(
                    value = if (set.weight <= 0) "" else set.weight.toString(),
                    onValueChange = {
                       val newValue = it.filter { char -> char.isDigit() }
                       onWeightChanged(newValue.toIntOrNull() ?: 0)
                    },
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