package com.jacqulin.gainly.feature.history.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacqulin.gainly.core.domain.model.workout.WorkoutListItem
import com.jacqulin.gainly.core.domain.usecase.workout.GetWorkoutHistoryUseCase
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.UiState
import com.jacqulin.gainly.core.util.errors.ErrorUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getWorkoutHistoryUseCase: GetWorkoutHistoryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<WorkoutListItem>>>(UiState.Idle)
    val uiState: StateFlow<UiState<List<WorkoutListItem>>> = _uiState

    init {
        loadWorkoutHistory()
    }

    fun loadWorkoutHistory() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                // Calculate date range: last 30 days
                val today = LocalDate.now()
                val thirtyDaysAgo = today.minusDays(30)
                val formatter = DateTimeFormatter.ISO_DATE

                val result = getWorkoutHistoryUseCase(
                    from = thirtyDaysAgo.format(formatter),
                    to = today.format(formatter),
                    last = 10
                )

                _uiState.value = when (result) {
                    is Result.Success -> UiState.Success(result.data)
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
}
