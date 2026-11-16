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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.Black
import com.jacqulin.gainly.core.designsystem.theme.BottomSheetContainerColor
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.core.designsystem.theme.UnselectedAddWorkoutItem
import com.jacqulin.gainly.feature.workout.presentation.AddWorkoutViewModel
import com.jacqulin.gainly.feature.workout.presentation.components.SaveButton
import com.jacqulin.gainly.feature.workout.presentation.components.WorkoutFormItem
import com.jacqulin.gainly.feature.workout.presentation.model.WorkoutExerciseUi
import com.jacqulin.gainly.feature.workout.presentation.model.WorkoutSetUi
import com.jacqulin.gainly.feature.workout.presentation.model.WorkoutState
import com.jacqulin.gainly.feature.workout.presentation.model.WorkoutType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWorkoutBottomSheet(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddWorkoutViewModel = viewModel()
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val uiState by viewModel.uiState.collectAsState()
    val focusRequester = remember { FocusRequester() }

/*    if (uiState.isColorPickerVisible) {
        ColorPickerBottomSheet(
            initialColor = uiState.selectedColor,
            onColorSelected = viewModel::updateColor,
            onDismiss = viewModel::hideColorPicker
        )
    }*/

/*    if (uiState.isTypePickerVisible) {
        WorkoutTypeBottomSheet(
            initialType = uiState.workoutType,
            onTypeSelected = viewModel::updateWorkoutType,
            onDismiss = viewModel::hideTypePicker
        )
    }*/

    if (uiState.isExercisesListVisible) {
        ExercisesListBottomSheet(
            exercises = uiState.exercises,
            selectedColor = uiState.selectedColor,
            selectedExerciseIds = uiState.selectedExerciseIds,
            onAddExercise = viewModel::addExercise,
            onEditExercise = viewModel::editExercise,
            onToggleSelect = viewModel::toggleExerciseSelection,
            onClearSelection = viewModel::clearExerciseSelection,
            onDeleteSelected = viewModel::deleteSelectedExercises,
            onSave = viewModel::hideExercisesList,
            onDismiss = viewModel::hideExercisesList
        )
    }

    if (uiState.isExerciseDetailVisible) {
        val currentExercise = uiState.exercises.find { it.id == uiState.editingExerciseId }
        if (currentExercise != null) {
            ExerciseDetailBottomSheet(
                exercise = currentExercise,
                selectedColor = uiState.selectedColor,
                selectedSetIndices = uiState.selectedSetIndices,
                onNameChanged = viewModel::updateCurrentExerciseName,
                onBodyWeightChanged = viewModel::toggleCurrentExerciseBodyWeight,
                onAddSet = viewModel::addSetToCurrentExercise,
                onUpdateSetReps = { index, reps -> viewModel.updateSetRepsInCurrentExercise(index, reps) },
                onUpdateSetWeight = { index, weight -> viewModel.updateSetWeightInCurrentExercise(index, weight) },
                onToggleSetSelect = viewModel::toggleSetSelection,
                onClearSetSelection = viewModel::clearSetSelection,
                onDeleteSelectedSets = viewModel::deleteSelectedSets,
                onSave = viewModel::hideExerciseDetail,
                onDismiss = viewModel::hideExerciseDetail
            )
        }
    }

/*    if (uiState.isDatePickerVisible) {
        DatePickerModal(
            onDateSelected = { dateMillis ->
                dateMillis?.let {
                    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                    calendar.timeInMillis = it
                    val day = calendar.get(Calendar.DAY_OF_MONTH)
                    val month = calendar.get(Calendar.MONTH) + 1
                    val year = calendar.get(Calendar.YEAR)
                    val formattedDate = String.format(Locale.US, "%02d%02d%04d", day, month, year)
                    viewModel.updateDate(formattedDate)
                }
            },
            onDismiss = viewModel::hideDatePicker
        )
    }*/

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = BottomSheetContainerColor,
        modifier = modifier
    ) {
        AddWorkoutBottomSheetContent(
            state = uiState,
            onTitleChanged = { newText ->
                if (newText.length <= 30) viewModel.updateTitle(newText)
            },
            onDateClick = {
                focusRequester.requestFocus()
                viewModel.showDatePicker()
            },
            onColorClick = {
                focusRequester.requestFocus()
                viewModel.showColorPicker()
            },
            onWorkoutTypeClick = {
                focusRequester.requestFocus()
                viewModel.showTypePicker()
            },
            onExercisesClick = {
                focusRequester.requestFocus()
                viewModel.showExercisesList()
            },
            onSave = {
                focusRequester.requestFocus()
                viewModel.saveWorkout()
                onDismiss()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWorkoutBottomSheetContent(
    state: WorkoutState,
    onTitleChanged: (String) -> Unit = {},
    onDateClick: () -> Unit = {},
    onColorClick: () -> Unit = {},
    onWorkoutTypeClick: () -> Unit = {},
    onExercisesClick: () -> Unit = {},
    onSave: () -> Unit = {}
) {
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 20.dp)
            .padding(bottom = 20.dp)
            .focusRequester(focusRequester)
            .focusable()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { focusRequester.requestFocus() }
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = state.title,
                onValueChange = onTitleChanged,
                textStyle = TextStyle(
                    fontFamily = GoogleSansFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    lineHeight = 28.sp,
                    letterSpacing = 0.sp,
                    color = state.selectedColor,
                    textAlign = TextAlign.Start
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusRequester.requestFocus() }
                ),
                maxLines = 2,
                modifier = Modifier.weight(1f)
            )

            Icon(
                painter = painterResource(R.drawable.ic_edit),
                contentDescription = "Редактировать",
                tint = state.selectedColor.copy(alpha = 0.5f),
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

/*        WorkoutFormItem(
            label = "Дата",
            onClick = onDateClick
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
                    text = if (state.date .isNotEmpty()) formatDate(state.date )
                    else "Выбрать дату",
                    style = TextStyle(
                        fontFamily = GoogleSansFontFamily,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 0.sp,
                        color = Black,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }*/

/*        WorkoutFormItem(
            label = "Цвет",
            onClick = onColorClick
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(state.selectedColor)
            )
        }*/

/*        WorkoutFormItem(
            label = "Вид тренировки",
            onClick = onWorkoutTypeClick
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(UnselectedAddWorkoutItem),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(state.workoutType.iconRes),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }
        }*/

        WorkoutFormItem(
            label = "Упражнения",
            onClick = onExercisesClick
        ) {
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .widthIn(min = 80.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(UnselectedAddWorkoutItem),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (state.exercises.isEmpty()) "Добавить упражнения"
                            else "${state.exercises.size}",
                    style = TextStyle(
                        fontFamily = GoogleSansFontFamily,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 0.sp,
                        color = Black
                    ),
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }

        SaveButton(
            enabled = state.exercises.isNotEmpty(),
            text = "Сохранить тренировку",
            onClick = onSave
        )
    }
}

@Preview
@Composable
fun AddWorkoutBottomSheetPreview() {
    val sampleExercises = listOf(
        WorkoutExerciseUi(id = "1", name = "Отжимания", sets = listOf(
            WorkoutSetUi(1, 20, 0),
            WorkoutSetUi(2, 15, 0)
        )),
        WorkoutExerciseUi(id = "2", name = "Приседания", sets = listOf(
            WorkoutSetUi(1, 30, 0),
            WorkoutSetUi(2, 25, 0),
            WorkoutSetUi(3, 20, 0)
        ))
    )
    GainlyTheme {
        AddWorkoutBottomSheetContent(
            state = WorkoutState(
                title = "Утренняя тренировка",
                date = "16112026",
                selectedColor = Color(0xFF505b92),
                workoutType = WorkoutType.Strength,
                exercises = sampleExercises,
            ),
            onTitleChanged = {},
            onDateClick = {},
            onColorClick = {},
            onWorkoutTypeClick = {},
            onExercisesClick = {},
            onSave = {}
        )
    }
}
