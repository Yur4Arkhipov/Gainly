package com.jacqulin.gainly.feature.workout.ui

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.model.workout.ExerciseData
import com.jacqulin.gainly.core.domain.model.workout.WorkoutData
import com.jacqulin.gainly.core.domain.model.workout.WorkoutSetData
import com.jacqulin.gainly.core.domain.usecase.workout.CreateWorkoutUseCase
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.ErrorUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WorkoutState(
    val title: String = "Название тренировки",
    val selectedColor: Color = Color(0xFF505b92),
    val workoutType: WorkoutType = WorkoutType.Cardio,
    val date: String = "",
    val isColorPickerVisible: Boolean = false,
    val isTypePickerVisible: Boolean = false,
    val isExerciseDetailVisible: Boolean = false,
    val isExercisesListVisible: Boolean = false,
    val isDatePickerVisible: Boolean = false,
    val exercises: List<Exercise> = emptyList(),
    val editingExerciseId: String? = null,
    val selectedExerciseIds: Set<String> = emptySet(),
    val selectedSetIndices: Set<Int> = emptySet()
)

data class Exercise(
    val id: String,
    val name: String = "",
    val sets: List<WorkoutSet> = emptyList(),
    val isBodyWeight: Boolean = false
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
    private val createWorkoutUseCase: CreateWorkoutUseCase,
    private val tokenStorage: TokenStorage
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

    fun showExercisesList() {
        _uiState.update { it.copy(isExercisesListVisible = true) }
    }

    fun hideExercisesList() {
        _uiState.update { it.copy(isExercisesListVisible = false) }
    }

    fun addExercise() {
        val newExerciseId = java.util.UUID.randomUUID().toString()
        val newExercise = Exercise(id = newExerciseId, name = "Упражнение ${_uiState.value.exercises.size + 1}")
        _uiState.update {
            it.copy(
                exercises = it.exercises + newExercise,
                editingExerciseId = newExerciseId,
                isExerciseDetailVisible = true
            )
        }
    }

    fun editExercise(exerciseId: String) {
        _uiState.update {
            it.copy(
                editingExerciseId = exerciseId,
                isExerciseDetailVisible = true
            )
        }
    }

    fun hideExerciseDetail() {
        _uiState.update {
            it.copy(
                isExerciseDetailVisible = false,
                editingExerciseId = null
            )
        }
    }

    fun updateCurrentExerciseName(name: String) {
        val exerciseId = _uiState.value.editingExerciseId ?: return
        _uiState.update { state ->
            val updatedExercises = state.exercises.map { exercise ->
                if (exercise.id == exerciseId) {
                    exercise.copy(name = name)
                } else {
                    exercise
                }
            }
            state.copy(exercises = updatedExercises)
        }
    }

    fun toggleCurrentExerciseBodyWeight(isBodyWeight: Boolean) {
        val exerciseId = _uiState.value.editingExerciseId ?: return
        _uiState.update { state ->
            val updatedExercises = state.exercises.map { exercise ->
                if (exercise.id == exerciseId) {
                    exercise.copy(isBodyWeight = isBodyWeight)
                } else {
                    exercise
                }
            }
            state.copy(exercises = updatedExercises)
        }
    }

    fun addSetToCurrentExercise() {
        val exerciseId = _uiState.value.editingExerciseId ?: return
        _uiState.update { state ->
            val updatedExercises = state.exercises.map { exercise ->
                if (exercise.id == exerciseId) {
                    val newSetNumber = exercise.sets.size + 1
                    exercise.copy(sets = exercise.sets + WorkoutSet(newSetNumber))
                } else {
                    exercise
                }
            }
            state.copy(exercises = updatedExercises)
        }
    }

    fun updateSetRepsInCurrentExercise(setIndex: Int, reps: Int) {
        val exerciseId = _uiState.value.editingExerciseId ?: return
        _uiState.update { state ->
            val updatedExercises = state.exercises.map { exercise ->
                if (exercise.id == exerciseId) {
                    val updatedSets = exercise.sets.toMutableList().apply {
                        this[setIndex] = this[setIndex].copy(reps = reps)
                    }
                    exercise.copy(sets = updatedSets)
                } else {
                    exercise
                }
            }
            state.copy(exercises = updatedExercises)
        }
    }

    fun updateSetWeightInCurrentExercise(setIndex: Int, weight: Int) {
        val exerciseId = _uiState.value.editingExerciseId ?: return
        _uiState.update { state ->
            val updatedExercises = state.exercises.map { exercise ->
                if (exercise.id == exerciseId) {
                    val updatedSets = exercise.sets.toMutableList().apply {
                        this[setIndex] = this[setIndex].copy(weight = weight)
                    }
                    exercise.copy(sets = updatedSets)
                } else {
                    exercise
                }
            }
            state.copy(exercises = updatedExercises)
        }
    }

    fun showDatePicker() {
        _uiState.update { it.copy(isDatePickerVisible = true) }
    }

    fun hideDatePicker() {
        _uiState.update { it.copy(isDatePickerVisible = false) }
    }

    fun updateDate(newDigits: String) {
        if (newDigits.length <= 8) {
            _uiState.update { it.copy(date = newDigits) }
        }
    }

    fun saveWorkout() {
        val state = _uiState.value
        val workoutData = WorkoutData(
            title = state.title,
            exercises = state.exercises.map { exercise ->
                ExerciseData(
                    name = exercise.name,
                    sets = exercise.sets.map { set ->
                        WorkoutSetData(
                            reps = set.reps,
                            weight = set.weight,
                            isOwnWeight = exercise.isBodyWeight
                        )
                    }
                )
            }
        )

        viewModelScope.launch {
            val authData = tokenStorage.tokens.firstOrNull()
            val token = authData?.accessToken

            if (token == null) {
                Log.d("TOKEN_WORKOUT", "Access token not found")
                return@launch
            }

            when (val result = createWorkoutUseCase(token, workoutData)) {
                is Result.Success -> {
                    Log.d("Workout","Id: $result")
                    println("Workout created successfully")
                    resetWorkoutState()
                }
                is Result.Error -> {
                    val message = ErrorUiMapper.toMessage(result.error)
//                    _uiState.value = UiState.Error(message)
                }
            }
        }
    }

    fun resetWorkoutState() {
        _uiState.value = WorkoutState()
    }

    fun toggleExerciseSelection(exerciseId: String) {
        _uiState.update { state ->
            val newSelected = state.selectedExerciseIds.toMutableSet()
            if (newSelected.contains(exerciseId)) {
                newSelected.remove(exerciseId)
            } else {
                newSelected.add(exerciseId)
            }
            state.copy(selectedExerciseIds = newSelected)
        }
    }

    fun clearExerciseSelection() {
        _uiState.update { it.copy(selectedExerciseIds = emptySet()) }
    }

    fun deleteSelectedExercises() {
        _uiState.update { state ->
            state.copy(
                exercises = state.exercises.filter { !state.selectedExerciseIds.contains(it.id) },
                selectedExerciseIds = emptySet(),
                editingExerciseId = null
            )
        }
    }

    fun toggleSetSelection(setIndex: Int) {
        _uiState.update { state ->
            val newSelected = state.selectedSetIndices.toMutableSet()
            if (newSelected.contains(setIndex)) {
                newSelected.remove(setIndex)
            } else {
                newSelected.add(setIndex)
            }
            state.copy(selectedSetIndices = newSelected)
        }
    }

    fun clearSetSelection() {
        _uiState.update { it.copy(selectedSetIndices = emptySet()) }
    }

    fun deleteSelectedSets() {
        val exerciseId = _uiState.value.editingExerciseId ?: return
        _uiState.update { state ->
            val updatedExercises = state.exercises.map { exercise ->
                if (exercise.id == exerciseId) {
                    val updatedSets = exercise.sets.filterIndexed { index, _ -> !state.selectedSetIndices.contains(index) }
                        .mapIndexed { index, set -> set.copy(number = index + 1) }
                    exercise.copy(sets = updatedSets)
                } else {
                    exercise
                }
            }
            state.copy(
                exercises = updatedExercises,
                selectedSetIndices = emptySet()
            )
        }
    }
}