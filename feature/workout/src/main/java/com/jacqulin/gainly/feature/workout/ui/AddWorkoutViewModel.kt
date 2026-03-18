package com.jacqulin.gainly.feature.workout.ui

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.domain.model.workout.ExerciseData
import com.jacqulin.gainly.core.domain.model.workout.WorkoutData
import com.jacqulin.gainly.core.domain.model.workout.WorkoutSetData
import com.jacqulin.gainly.core.domain.usecase.workout.CreateWorkoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    val editingExerciseId: String? = null
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
    private val createWorkoutUseCase: CreateWorkoutUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(WorkoutState())
    val uiState: StateFlow<WorkoutState> = _uiState.asStateFlow()

//    fun updateColor(color: Color) {
//        _uiState.update { it.copy(selectedColor = color, isColorPickerVisible = false) }
//    }

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

//    fun updateWorkoutType(type: WorkoutType) {
//        _uiState.update { it.copy(workoutType = type, isTypePickerVisible = false) }
//    }

//    fun showColorPicker() {
//        _uiState.update { it.copy(isColorPickerVisible = true) }
//    }

//    fun hideColorPicker() {
//        _uiState.update { it.copy(isColorPickerVisible = false) }
//    }

//    fun showTypePicker() {
//        _uiState.update { it.copy(isTypePickerVisible = true) }
//    }

//    fun hideTypePicker() {
//        _uiState.update { it.copy(isTypePickerVisible = false) }
//    }

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

//    fun showDatePicker() {
//        _uiState.update { it.copy(isDatePickerVisible = true) }
//    }

//    fun hideDatePicker() {
//        _uiState.update { it.copy(isDatePickerVisible = false) }
//    }

//    fun updateDate(newDigits: String) {
//        if (newDigits.length <= 8) {
//            _uiState.update { it.copy(date = newDigits) }
//        }
//    }

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
            try {
                createWorkoutUseCase(workoutData)
//                 TODO: Handle success (e.g., navigate back, show success message)
                println("Workout created successfully")
            } catch (e: Exception) {
                // TODO: Handle error
                println("Error creating workout: ${e.message}")
            }
        }
    }
}