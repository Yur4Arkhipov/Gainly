package com.jacqulin.gainly.feature.history.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jacqulin.gainly.core.designsystem.theme.Black
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.core.designsystem.theme.GrayBackgroundMain
import com.jacqulin.gainly.core.designsystem.theme.White
import com.jacqulin.gainly.core.domain.model.workout.WorkoutListItem
import com.jacqulin.gainly.core.domain.model.workout.WorkoutById
import com.jacqulin.gainly.core.domain.model.workout.ExerciseData
import com.jacqulin.gainly.core.domain.model.workout.WorkoutSetData
import com.jacqulin.gainly.core.util.UiState
import com.jacqulin.gainly.feature.history.viewmodel.HistoryViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val expandedWorkoutId by viewModel.expandedWorkoutId.collectAsState()
    val workoutDetailsState by viewModel.workoutDetailsState.collectAsState()

    var startDate by remember { mutableStateOf(LocalDate.now().minusMonths(1)) }
    var endDate by remember { mutableStateOf(LocalDate.now()) }

    LaunchedEffect(startDate, endDate) {
        viewModel.loadWorkoutHistoryByDateRange(startDate, endDate)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = buildAnnotatedString {
                        append("История ")

                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                            )
                        ) {
                            append("тренировок")
                        }
                    },
                    style = TextStyle(
                        fontFamily = GoogleSansFontFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 24.sp,
                        color = Black
                    ),
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .padding(top = 12.dp)
                )
            }

            item {
                DateRangeFilter(
                    startDate = startDate,
                    endDate = endDate,
                    onStartDateChange = { startDate = it },
                    onEndDateChange = { endDate = it }
                )
            }

            when (uiState) {
                is UiState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                is UiState.Success -> {
                    val workouts = (uiState as UiState.Success<List<WorkoutListItem>>).data

                    if (workouts.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        color = GrayBackgroundMain,
                                        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                                    )
                            ) {
                                EmptyHistoryView()
                            }
                        }
                    } else {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = GrayBackgroundMain,
                                        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 12.dp)
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    workouts.forEach { workout ->
                                        ExpandableWorkoutCard(
                                            workout = workout,
                                            isExpanded = expandedWorkoutId == workout.workoutId,
                                            onToggleExpand = { viewModel.toggleWorkoutExpanded(workout.workoutId) },
                                            workoutDetails = workoutDetailsState,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                is UiState.Error -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = GrayBackgroundMain,
                                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                                )
                                .padding(16.dp)
                        ) {
                            ErrorView(message = (uiState as UiState.Error).message)
                        }
                    }
                }

                is UiState.Idle -> { }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateRangeFilter(
    startDate: LocalDate,
    endDate: LocalDate,
    onStartDateChange: (LocalDate) -> Unit,
    onEndDateChange: (LocalDate) -> Unit
) {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }
    var selectedPeriod by remember { mutableStateOf("Месяц") }

    if (showStartDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = startDate.toEpochDay() * 86400000
        )
        DatePickerDialog(
            onDismissRequest = { showStartDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val newDate = java.time.Instant.ofEpochMilli(millis)
                            .atZone(java.time.ZoneId.systemDefault())
                            .toLocalDate()
                        onStartDateChange(newDate)
                    }
                    showStartDatePicker = false
                }) {
                    Text("ОК")
                }
            },
            dismissButton = {
                TextButton(onClick = { showStartDatePicker = false }) {
                    Text("Отмена")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showEndDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = endDate.toEpochDay() * 86400000
        )
        DatePickerDialog(
            onDismissRequest = { showEndDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val newDate = java.time.Instant.ofEpochMilli(millis)
                            .atZone(java.time.ZoneId.systemDefault())
                            .toLocalDate()
                        onEndDateChange(newDate)
                    }
                    showEndDatePicker = false
                }) {
                    Text("ОК")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEndDatePicker = false }) {
                    Text("Отмена")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Период",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { showStartDatePicker = true }
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "С ${startDate.format(formatter)}",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { showEndDatePicker = true }
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "По ${endDate.format(formatter)}",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterButton(
                text = "Неделя",
                isSelected = selectedPeriod == "Неделя",
                onClick = {
                    selectedPeriod = "Неделя"
                    val now = LocalDate.now()
                    onStartDateChange(now.minusWeeks(1))
                    onEndDateChange(now)
                },
                modifier = Modifier.weight(1f)
            )

            FilterButton(
                text = "Месяц",
                isSelected = selectedPeriod == "Месяц",
                onClick = {
                    selectedPeriod = "Месяц"
                    val now = LocalDate.now()
                    onStartDateChange(now.minusMonths(1))
                    onEndDateChange(now)
                },
                modifier = Modifier.weight(1f)
            )

            FilterButton(
                text = "3 месяца",
                isSelected = selectedPeriod == "3 месяца",
                onClick = {
                    selectedPeriod = "3 месяца"
                    val now = LocalDate.now()
                    onStartDateChange(now.minusMonths(3))
                    onEndDateChange(now)
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun FilterButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(32.dp),
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFF1E88E5) else Color.White,
            contentColor = if (isSelected) Color.White else Color.Black
        )
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
        )
    }
}

@Composable
private fun ExpandableWorkoutCard(
    workout: WorkoutListItem,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    workoutDetails: UiState<WorkoutById>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFC2F1FC),
                    shape = RoundedCornerShape(20.dp)
                )
                .clickable { onToggleExpand() }
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = workout.title,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        )
                        Text(
                            text = workout.date,
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = Color.Gray
                            ),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = Color(0xFF1E88E5),
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    WorkoutStat(label = "Упражнений", value = workout.exerciseCount.toString())

                    workout.duration?.let {
                        WorkoutStat(label = "Время (мин)", value = it.toString())
                    }
                }
            }
        }

        if (isExpanded) {
            Spacer(modifier = Modifier.height(8.dp))
            
            when (workoutDetails) {
                is UiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    }
                }

                is UiState.Success -> {
                    val details = workoutDetails.data
                    WorkoutDetailsView(details, modifier = Modifier.fillMaxWidth())
                }

                is UiState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color(0xFFFFEBEE),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "Ошибка загрузки деталей",
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = Color.Red
                            )
                        )
                    }
                }

                else -> {}
            }
        }
    }
}

@Composable
private fun WorkoutStat(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = TextStyle(
                fontSize = 12.sp,
                color = Color.Gray
            )
        )
        Text(
            text = value,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            ),
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}

@Composable
private fun EmptyHistoryView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = Color.LightGray,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "Нет тренировок",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
            )
            Text(
                text = "Начните с создания первой тренировки",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Gray
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
private fun WorkoutDetailsView(
    workout: WorkoutById,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Упражнения",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                workout.exercises.forEach { exercise ->
                    ExerciseItem(exercise = exercise)
                }
            }
        }
    }
}

@Composable
private fun ExerciseItem(exercise: ExerciseData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFF5F5F5),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = exercise.name,
            style = TextStyle(
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            exercise.sets.forEachIndexed { index, set ->
                SetRow(setNumber = index + 1, set = set)
            }
        }
    }
}

@Composable
private fun SetRow(setNumber: Int, set: WorkoutSetData) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Подход $setNumber:",
            style = TextStyle(
                fontSize = 11.sp,
                color = Color.Gray
            )
        )

        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${set.reps} повторений",
                style = TextStyle(
                    fontSize = 11.sp,
                    color = Color.Black
                )
            )

            Spacer(modifier = Modifier.width(12.dp))

            if (!set.isOwnWeight) {
                Text(
                    text = "${set.weight} кг",
                    style = TextStyle(
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF1E88E5)
                    )
                )
            } else {
                Text(
                    text = "Собственный вес",
                    style = TextStyle(
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF4CAF50)
                    )
                )
            }
        }
    }
}

@Composable
private fun ErrorView(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Ошибка",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            )
            Text(
                text = message,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Gray
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}