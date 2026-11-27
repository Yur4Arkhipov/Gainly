package com.jacqulin.gainly.feature.workout.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacqulin.gainly.core.domain.usecase.workout.CreateWorkoutUseCase
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.ErrorUiMapper
import com.jacqulin.gainly.feature.workout.presentation.model.CreateWorkoutEffect
import com.jacqulin.gainly.feature.workout.presentation.model.CreateWorkoutEvent
import com.jacqulin.gainly.feature.workout.presentation.model.CreateWorkoutUiState
import com.jacqulin.gainly.feature.workout.presentation.model.WorkoutExerciseUi
import com.jacqulin.gainly.feature.workout.presentation.model.WorkoutSetUi
import com.jacqulin.gainly.feature.workout.presentation.model.WorkoutType
import com.jacqulin.gainly.feature.workout.presentation.model.toDomain
import com.jacqulin.gainly.feature.workout.presentation.util.dateFormatter
import com.jacqulin.gainly.feature.workout.presentation.util.isValidTime
import com.jacqulin.gainly.feature.workout.presentation.util.isValidTimeInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneOffset
import java.util.UUID
import javax.inject.Inject
import kotlin.String

@HiltViewModel
class CreateWorkoutViewModel @Inject constructor(
    private val createWorkoutUseCase: CreateWorkoutUseCase,
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<CreateWorkoutUiState>(
            CreateWorkoutUiState.Content.initial()
        )

    val uiState = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<CreateWorkoutEffect>()
    val effects = _effects.asSharedFlow()

    fun onEvent(event: CreateWorkoutEvent) {
        when (event) {
            is CreateWorkoutEvent.OnWorkoutTitleChanged -> updateWorkoutTitle(event.title)
            is CreateWorkoutEvent.OnDateChanged -> updateDate(event.date)
            is CreateWorkoutEvent.OnTimeChanged -> updateTime(event.time)
            is CreateWorkoutEvent.OnWorkoutTypeChanged -> updateWorkoutType(event.type)

            CreateWorkoutEvent.OnCreateExercise -> createExercise()
            is CreateWorkoutEvent.OnToggleExerciseSelection -> toggleExerciseSelect(event.exerciseId)
            CreateWorkoutEvent.OnClearExerciseSelection -> clearExerciseSelection()
            CreateWorkoutEvent.OnDeleteSelectedExercises -> deleteSelectedExercises()

            is CreateWorkoutEvent.OnExerciseExpanded -> toggleExerciseExpanded(event.exerciseId)
            is CreateWorkoutEvent.OnExerciseTitleChanged -> updateExerciseTitle(event.exerciseId, event.title)
            is CreateWorkoutEvent.OnSetAdded -> addSet(event.exerciseId)
            is CreateWorkoutEvent.OnDeleteSelectedSets -> deleteSelectedSets(event.exerciseId)
            is CreateWorkoutEvent.OnBodyWeightChanged -> changeBodyWeight(event.exerciseId, event.isBodyWeight)
            is CreateWorkoutEvent.OnSetWeightChanged -> updateSetWeight(event.exerciseId, event.setId, event.weight)
            is CreateWorkoutEvent.OnSetRepetitionsChanged -> updateSetRepetitions(event.exerciseId, event.setId, event.repetitions)
            is CreateWorkoutEvent.OnToggleExercisesSetsSelection -> toggleSetSelection(event.exerciseId, event.setId)

            CreateWorkoutEvent.OnSaveWorkout -> saveWorkout()
        }
    }

    // Info page
    private fun updateWorkoutTitle(title: String) {
        updateContent {
            copy(
                title = title,
                invalidWorkoutTitle = title.isBlank() && invalidWorkoutTitle
            )
        }
    }

    private fun updateDate(dateMillis: Long) {
        val date = Instant.ofEpochMilli(dateMillis)
            .atZone(ZoneOffset.UTC)
            .toLocalDate()
            .format(dateFormatter)

        updateContent {
            copy(date = date)
        }
    }

    private fun updateTime(time: String) {
        val digits = time.filter(Char::isDigit)

        if (!digits.isValidTimeInput()) return

        val formattedTime = when {
            digits.length <= 2 -> digits
            else -> "${digits.take(2)}:${digits.drop(2)}"
        }

        updateContent {
            copy(
                time = formattedTime,
                invalidWorkoutTime = formattedTime.isValidTime().not() &&
                        invalidWorkoutTime
            )
        }
    }

    private fun updateWorkoutType(type: WorkoutType) {
        updateContent {
            copy(workoutType = type)
        }
    }

    // Exercises page
    private fun createExercise() {
        updateContent {
            val newExerciseId = UUID.randomUUID().toString()
            val newExercise = WorkoutExerciseUi(
                id = newExerciseId,
                title = "Упражнение ${exercises.size + 1}"
            )
            copy(
                exercises = exercises + newExercise
            )
        }
    }

    fun toggleExerciseSelect(exerciseId: String) {
        updateContent {
            val newSelected = if (exerciseId in selectedExerciseIds) {
                selectedExerciseIds - exerciseId
            } else {
                selectedExerciseIds + exerciseId
            }

            copy(selectedExerciseIds = newSelected)
        }
    }

    fun clearExerciseSelection() {
        updateContent {
            copy(selectedExerciseIds = emptySet())
        }
    }

    fun deleteSelectedExercises() {
        updateContent {
            copy(
                exercises = exercises.filter { !selectedExerciseIds.contains(it.id) },
                selectedExerciseIds = emptySet(),
            )
        }
    }

    private fun updateExerciseTitle(
        exerciseId: String,
        title: String
    ) {
        updateContent {
            copy(
                exercises = exercises.map { exercise ->
                    if (exercise.id == exerciseId) {
                        exercise.copy(title = title)
                    } else {
                        exercise
                    }
                },
                invalidExerciseTitleIds = if (title.isNotBlank()) {
                    invalidExerciseTitleIds - exerciseId
                } else {
                    invalidExerciseTitleIds
                }
            )
        }
    }

    fun toggleExerciseExpanded(exerciseId: String) {
        updateExercise(exerciseId) {
            copy(isExpanded = !isExpanded)
        }
    }

    private fun changeBodyWeight(
        exerciseId: String,
        isBodyWeight: Boolean
    ) {
        updateExercise(exerciseId) {
            copy(isBodyWeight = isBodyWeight)
        }
    }

    private fun addSet(exerciseId: String) {
        updateExercise(exerciseId) {
            val newSet = WorkoutSetUi(
                id = UUID.randomUUID().toString(),
                number = sets.size + 1
            )

            val updatedSets = sets + newSet

            Log.d(
                "CreateWorkout VM",
                "Exercise id: $exerciseId, Sets: $updatedSets"
            )

            copy(sets = updatedSets)
        }
    }

    private fun deleteSelectedSets(exerciseId: String) {
        updateExercise(exerciseId) {
            copy(
                sets = sets
                    .filterNot { it.id in selectedSetIds }
                    .mapIndexed { index, set ->
                        set.copy(number = index + 1)
                    },
                selectedSetIds = emptySet()
            )
        }
    }

    private fun updateSetWeight(
        exerciseId: String,
        setId: String,
        weight: String
    ) {
        val newWeight = weight.toIntOrNull() ?: 0

        updateExercise(exerciseId) {
            copy(
                sets = sets.map { set ->
                    if (set.id == setId) {
                        set.copy(weight = newWeight)
                    } else {
                        set
                    }
                }
            )
        }

        if (newWeight > 0) {
            updateContent {
                copy(
                    invalidWeightSetIds = invalidWeightSetIds - setId
                )
            }
        }
    }

    private fun updateSetRepetitions(
        exerciseId: String,
        setId: String,
        repetitions: String
    ) {
        val newReps = repetitions.toIntOrNull() ?: 0

        updateExercise(exerciseId) {
            copy(
                sets = sets.map { set ->
                    if (set.id == setId) {
                        set.copy(reps = newReps)
                    } else {
                        set
                    }
                }
            )
        }

        if (newReps > 0) {
            updateContent {
                copy(
                    invalidRepsSetIds = invalidRepsSetIds - setId
                )
            }
        }
    }

    private fun toggleSetSelection(
        exerciseId: String,
        setId: String
    ) {
        updateExercise(exerciseId) {
            copy(
                selectedSetIds = if (setId in selectedSetIds) {
                    selectedSetIds - setId
                } else {
                    selectedSetIds + setId
                }
            )
        }
    }

    // Saving
    private fun saveWorkout() {
        val content = _uiState.value as? CreateWorkoutUiState.Content ?: return

        val hasWorkoutInfoErrors = validateWorkoutInfo(content)
        val hasExerciseErrors = validateExercises(content.exercises)

        when {
            hasWorkoutInfoErrors -> {
                viewModelScope.launch {
                    _effects.emit(CreateWorkoutEffect.NavigateToPage(0))
                }
                return
            }

            hasExerciseErrors -> {
                viewModelScope.launch {
                    _effects.emit(CreateWorkoutEffect.NavigateToPage(1))
                }
                return
            }
        }

        val workout = content.toDomain()

        viewModelScope.launch {
            _uiState.value = CreateWorkoutUiState.Loading

            when (val result = createWorkoutUseCase(workout)) {
                is Result.Success -> {
                    Log.d("Workout", "Workout created: $result")
                    _uiState.value = CreateWorkoutUiState.Content.initial()

                    _effects.emit(CreateWorkoutEffect.NavigateToBack)
                }

                is Result.Error -> {
                    val message = ErrorUiMapper.toMessage(result.error)
                    Log.e("Workout", "Failed to create workout: $message")
                    _uiState.value = content
                    _effects.emit(CreateWorkoutEffect.ShowSnackbar(message))
                }
            }
        }
    }

    // Utils
    private inline fun updateContent(
        transform: CreateWorkoutUiState.Content.() -> CreateWorkoutUiState.Content
    ) {
        _uiState.update { state ->
            if (state is CreateWorkoutUiState.Content) {
                state.transform()
            } else {
                state
            }
        }
    }

    private inline fun updateExercise(
        exerciseId: String,
        crossinline transform: WorkoutExerciseUi.() -> WorkoutExerciseUi
    ) {
        updateContent {
            copy(
                exercises = exercises.map { exercise ->
                    if (exercise.id == exerciseId) {
                        exercise.transform()
                    } else {
                        exercise
                    }
                }
            )
        }
    }

    private fun validateWorkoutInfo(
        content: CreateWorkoutUiState.Content
    ): Boolean {
        val invalidTitle = content.title.isBlank()
        val invalidTime = !content.time.isValidTime()

        updateContent {
            copy(
                invalidWorkoutTitle = invalidTitle,
                invalidWorkoutTime = invalidTime
            )
        }

        return invalidTitle || invalidTime
    }

    private fun validateExercises(
        exercises: List<WorkoutExerciseUi>
    ): Boolean {
        val (invalidReps, invalidWeight) = validateSets(exercises)

        val invalidTitles = exercises
            .filter { it.title.isBlank() }
            .map { it.id }
            .toSet()

        updateContent {
            copy(
                invalidExerciseTitleIds = invalidTitles,
                invalidRepsSetIds = invalidReps,
                invalidWeightSetIds = invalidWeight,
                exercises = exercises.map { exercise ->
                    if (
                        exercise.sets.any { set ->
                            set.id in invalidReps || set.id in invalidWeight
                        }
                    ) {
                        exercise.copy(isExpanded = true)
                    } else {
                        exercise
                    }
                }
            )
        }

        return invalidTitles.isNotEmpty() ||
                invalidReps.isNotEmpty() ||
                invalidWeight.isNotEmpty()
    }

    private fun validateSets(
        exercises: List<WorkoutExerciseUi>
    ): Pair<Set<String>, Set<String>> {
        val invalidReps = mutableSetOf<String>()
        val invalidWeight = mutableSetOf<String>()

        exercises.forEach { exercise ->
            exercise.sets.forEach { set ->
                if (set.reps <= 0) {
                    invalidReps.add(set.id)
                }

                if (!exercise.isBodyWeight && set.weight <= 0) {
                    invalidWeight.add(set.id)
                }
            }
        }

        return invalidReps to invalidWeight
    }
}
