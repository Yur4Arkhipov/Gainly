package com.jacqulin.gainly.feature.history.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacqulin.gainly.core.domain.model.workout.WorkoutListItem
import com.jacqulin.gainly.core.domain.model.workout.WorkoutById
import com.jacqulin.gainly.core.domain.usecase.workout.GetWorkoutByIdUseCase
import com.jacqulin.gainly.core.domain.usecase.workout.GetWorkoutHistoryUseCase
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.UiState
import com.jacqulin.gainly.core.util.errors.ErrorUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getWorkoutHistoryUseCase: GetWorkoutHistoryUseCase,
    private val getWorkoutByIdUseCase: GetWorkoutByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<WorkoutListItem>>>(UiState.Idle)
    val uiState: StateFlow<UiState<List<WorkoutListItem>>> = _uiState

    private val _workoutDetailsState = MutableStateFlow<UiState<WorkoutById>>(UiState.Idle)
    val workoutDetailsState: StateFlow<UiState<WorkoutById>> = _workoutDetailsState

    private val _expandedWorkoutId = MutableStateFlow<String?>(null)
    val expandedWorkoutId: StateFlow<String?> = _expandedWorkoutId

    @OptIn(ExperimentalTime::class)
    fun loadWorkoutHistoryByDateRange(startDate: LocalDate, endDate: LocalDate) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val formatter = DateTimeFormatter.ISO_INSTANT

                val from = startDate
                    .atStartOfDay(ZoneOffset.UTC)
                    .toInstant()
                    .let { formatter.format(it) }

                val to = endDate
                    .atTime(23, 59, 59)
                    .atOffset(ZoneOffset.UTC)
                    .toInstant()
                    .let { formatter.format(it) }

                val result = getWorkoutHistoryUseCase(
                    from = from,
                    to = to,
                    last = 20
                )

                _uiState.value = when (result) {
                    is Result.Success -> {
                        Log.d("HistoryViewModel", "Loaded ${result.data.size} workouts from sync")
                        UiState.Success(result.data)
                    }
                    is Result.Error -> {
                        Log.e("HistoryViewModel", "Error loading workouts: ${result.error}")
                        UiState.Error(ErrorUiMapper.toMessage(result.error))
                    }
                }
            } catch (e: Exception) {
                Log.e("HistoryViewModel", "Exception loading workouts", e)
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun loadWorkoutDetails(workoutId: String) {
        viewModelScope.launch {
            _workoutDetailsState.value = UiState.Loading
            try {
                val result = getWorkoutByIdUseCase(workoutId)

                _workoutDetailsState.value = when (result) {
                    is Result.Success -> {
                        Log.d("HistoryViewModel", "Loaded workout details for $workoutId")
                        UiState.Success(result.data)
                    }
                    is Result.Error -> {
                        Log.e("HistoryViewModel", "Error loading workout details: ${result.error}")
                        UiState.Error(ErrorUiMapper.toMessage(result.error))
                    }
                }
            } catch (e: Exception) {
                Log.e("HistoryViewModel", "Exception loading workout details", e)
                _workoutDetailsState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun toggleWorkoutExpanded(workoutId: String) {
        val currentId = _expandedWorkoutId.value
        _expandedWorkoutId.value = if (currentId == workoutId) null else workoutId

        // Загружаем детали при раскрытии
        if (_expandedWorkoutId.value == workoutId) {
            loadWorkoutDetails(workoutId)
        }
    }
}