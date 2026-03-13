package com.jacqulin.gainly.feature.workout.ui

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.jacqulin.gainly.core.designsystem.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class WorkoutState(
    val title: String = "Название упражнения",
    val selectedColor: Color = Color(0xFF505b92),
    val workoutType: WorkoutType = WorkoutType.Cardio,
    val date: String = "",
    val isColorPickerVisible: Boolean = false,
    val isTypePickerVisible: Boolean = false,
    val isSetsPickerVisible: Boolean = false,
    val isDatePickerVisible: Boolean = false,
    val isBodyWeight: Boolean = false,
    val workoutSets: List<WorkoutSet> = emptyList()
)

data class WorkoutSet(
    val number: Int,
    val reps: Int = 0,
    val weight: Int = 0
)

sealed class WorkoutType(val label: String, val iconRes: Int) {
    object Cardio : WorkoutType("Кардио тренировка", R.drawable.ic_cardio)
    object Strength : WorkoutType("Силовая тренировка", R.drawable.ic_strength)

    companion object {
        val entries by lazy { listOf(Cardio, Strength) }
    }
}

@HiltViewModel
class AddWorkoutViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(WorkoutState())
    val uiState: StateFlow<WorkoutState> = _uiState.asStateFlow()

    fun updateColor(color: Color) {
        _uiState.update { it.copy(selectedColor = color, isColorPickerVisible = false) }
    }

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    fun updateWorkoutType(type: WorkoutType) {
        _uiState.update { it.copy(workoutType = type, isTypePickerVisible = false) }
    }

    fun showColorPicker() {
        _uiState.update { it.copy(isColorPickerVisible = true) }
    }

    fun hideColorPicker() {
        _uiState.update { it.copy(isColorPickerVisible = false) }
    }

    fun showTypePicker() {
        _uiState.update { it.copy(isTypePickerVisible = true) }
    }

    fun hideTypePicker() {
        _uiState.update { it.copy(isTypePickerVisible = false) }
    }

    fun showSetsPicker() {
        _uiState.update { it.copy(isSetsPickerVisible = true) }
    }

    fun hideSetsPicker() {
        _uiState.update { it.copy(isSetsPickerVisible = false) }
    }

    fun showDatePicker() {
        _uiState.update { it.copy(isDatePickerVisible = true) }
    }

    fun hideDatePicker() {
        _uiState.update { it.copy(isDatePickerVisible = false) }
    }

    fun toggleBodyWeight(isBodyWeight: Boolean) {
        _uiState.update { it.copy(isBodyWeight = isBodyWeight) }
    }

    fun addSet() {
        _uiState.update { currentState ->
            val newSetNumber = currentState.workoutSets.size + 1
            currentState.copy(workoutSets = currentState.workoutSets + WorkoutSet(newSetNumber))
        }
    }

    fun updateSetReps(index: Int, reps: Int) {
        _uiState.update { currentState ->
            val updatedSets = currentState.workoutSets.toMutableList().apply {
                this[index] = this[index].copy(reps = reps)
            }
            currentState.copy(workoutSets = updatedSets)
        }
    }

    fun updateSetWeight(index: Int, weight: Int) {
        _uiState.update { currentState ->
            val updatedSets = currentState.workoutSets.toMutableList().apply {
                this[index] = this[index].copy(weight = weight)
            }
            currentState.copy(workoutSets = updatedSets)
        }
    }

    fun updateDate(newDigits: String) {
        if (newDigits.length <= 8) {
            _uiState.update { it.copy(date = newDigits) }
        }
    }
}
