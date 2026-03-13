package com.jacqulin.gainly.feature.workout.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jacqulin.gainly.core.designsystem.theme.Black
import com.jacqulin.gainly.core.designsystem.theme.BottomSheetContainerColor
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.core.designsystem.theme.UnselectedAddWorkoutItem
import com.jacqulin.gainly.core.designsystem.theme.White
import com.jacqulin.gainly.feature.workout.ui.components.SaveButton
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.TextButton
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.rememberDatePickerState
import java.util.Calendar
import java.util.TimeZone
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWorkoutBottomSheet(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddWorkoutViewModel = viewModel()
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    if (uiState.isColorPickerVisible) {
        ColorPickerBottomSheet(
            initialColor = uiState.selectedColor,
            onColorSelected = viewModel::updateColor,
            onDismiss = viewModel::hideColorPicker
        )
    }

    if (uiState.isTypePickerVisible) {
        WorkoutTypeBottomSheet(
            initialType = uiState.workoutType,
            onTypeSelected = viewModel::updateWorkoutType,
            onDismiss = viewModel::hideTypePicker
        )
    }

    if (uiState.isSetsPickerVisible) {
        SetsBottomSheet(
            selectedColor = uiState.selectedColor,
            initialSets = uiState.workoutSets,
            isBodyWeight = uiState.isBodyWeight,
            onBodyWeightChanged = viewModel::toggleBodyWeight,
            onAddSet = viewModel::addSet,
            onUpdateSetReps = viewModel::updateSetReps,
            onUpdateSetWeight = viewModel::updateSetWeight,
            onSave = viewModel::hideSetsPicker,
            onDismiss = viewModel::hideSetsPicker
        )
    }

    if (uiState.isDatePickerVisible) {
        DatePickerModal(
            onDateSelected = { dateMillis ->
                dateMillis?.let {
                    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                    calendar.timeInMillis = it
                    val day = calendar.get(Calendar.DAY_OF_MONTH)
                    val month = calendar.get(Calendar.MONTH) + 1
                    val year = calendar.get(Calendar.YEAR)
                    val formattedDate = String.format("%02d%02d%04d", day, month, year)
                    viewModel.updateDate(formattedDate)
                }
            },
            onDismiss = viewModel::hideDatePicker
        )
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = BottomSheetContainerColor,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
                .padding(horizontal = 20.dp)
                .padding(bottom = 20.dp)
                .imePadding()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Новая тренировка",
                style = TextStyle(
                    fontFamily = GoogleSansFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    lineHeight = 23.sp,
                    letterSpacing = 0.sp,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

//            val titleFocusRequester = remember { FocusRequester() }
//            var isTitleFocused by remember { mutableStateOf(false) }
//
//            if (isTitleFocused) {
//                BasicTextField(
//                    value = uiState.title,
//                    onValueChange = viewModel::updateTitle,
//                    textStyle = TextStyle(
//                        fontFamily = GoogleSansFontFamily,
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 42.sp,
//                        lineHeight = 40.sp,
//                        letterSpacing = 0.sp,
//                        color = uiState.selectedColor
//                    ),
//                    keyboardOptions = KeyboardOptions(
//                        imeAction = ImeAction.Done
//                    ),
//                    keyboardActions = KeyboardActions(
//                        onDone = {
//                            isTitleFocused = false
//                            focusManager.clearFocus()
//                        }
//                    ),
//                    singleLine = false,
//                    maxLines = 2,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .focusRequester(titleFocusRequester)
//                        .onFocusChanged { state ->
//                            if (!state.isFocused) {
//                                isTitleFocused = false
//                            }
//                        }
//                )
//                LaunchedEffect(Unit) {
//                    titleFocusRequester.requestFocus()
//                }
//            } else {
//                Text(
//                    text = uiState.title.ifEmpty { "Название упражнения" },
//                    style = TextStyle(
//                        fontFamily = GoogleSansFontFamily,
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 42.sp,
//                        lineHeight = 40.sp,
//                        letterSpacing = 0.sp,
//                        color = uiState.selectedColor
//                    ),
//                    maxLines = 2,
//                    overflow = TextOverflow.Ellipsis,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clickable { isTitleFocused = true }
//                )
//            }

            Spacer(modifier = Modifier.height(10.dp))

            WorkoutFormItem(
                label = "Цвет",
                onClick = {
                    focusManager.clearFocus()
                    viewModel.showColorPicker()
                }
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(uiState.selectedColor)
                )
            }

            WorkoutFormItem(
                label = "Вид тренировки",
                onClick = {
                    focusManager.clearFocus()
                    viewModel.showTypePicker()
                }
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(UnselectedAddWorkoutItem),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(uiState.workoutType.iconRes),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            WorkoutFormItem(
                label = "Сеты",
                onClick = {
                    focusManager.clearFocus()
                    viewModel.showSetsPicker()
                }
            ) {
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .widthIn(min = 120.dp)
                        .clip(RoundedCornerShape(32.dp))
                        .background(UnselectedAddWorkoutItem),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (uiState.workoutSets.isEmpty()) "0" else "${uiState.workoutSets.size}",
                        style = TextStyle(
                            fontFamily = GoogleSansFontFamily,
                            fontSize = 16.sp,
                            lineHeight = 16.sp,
                            fontWeight = FontWeight.Normal,
                            letterSpacing = 0.sp,
                            color = Black
                        )
                    )
                }
            }

            WorkoutFormItem(
                label = "Дата",
                onClick = {
                    focusManager.clearFocus()
                    viewModel.showDatePicker()
                }
            ) {
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .widthIn(min = 120.dp)
                        .clip(RoundedCornerShape(32.dp))
                        .background(UnselectedAddWorkoutItem),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (uiState.date.isNotEmpty()) formatDate(uiState.date)
                        else "Выбрать дату",
                        style = TextStyle(
                            fontFamily = GoogleSansFontFamily,
                            fontSize = 16.sp,
                            lineHeight = 16.sp,
                            fontWeight = FontWeight.Normal,
                            letterSpacing = 0.sp,
                            color = Black,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }

            SaveButton(
                text = "Добавить запись",
                onClick = {
                    focusManager.clearFocus()
                    /* TODO: Save action */
                }
            )
        }
    }
}

fun formatDate(digits: String): String {
    return buildString {
        digits.forEachIndexed { index, c ->
            append(c)
            if ((index == 1 || index == 3) && index != digits.lastIndex) {
                append('.')
            }
        }
    }
}

@Composable
private fun WorkoutFormItem(
    label: String,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(White)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontFamily = GoogleSansFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.sp,
                color = Black
            )
        )
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}