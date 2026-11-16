package com.jacqulin.gainly.feature.workout.presentation

import androidx.lifecycle.ViewModel
import com.jacqulin.gainly.core.domain.usecase.workout.CreateWorkoutUseCase
import com.jacqulin.gainly.feature.workout.presentation.model.CreateWorkoutEvent
import com.jacqulin.gainly.feature.workout.presentation.model.CreateWorkoutUiState
import com.jacqulin.gainly.feature.workout.presentation.model.WorkoutExerciseUi
import com.jacqulin.gainly.feature.workout.presentation.model.WorkoutType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID
import javax.inject.Inject
import kotlin.String

@HiltViewModel
class CreateWorkoutViewModel @Inject constructor(
    private val createWorkoutUseCase: CreateWorkoutUseCase,
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<CreateWorkoutUiState>(
            CreateWorkoutUiState.Content()
        )

    val uiState = _uiState.asStateFlow()

    fun onEvent(event: CreateWorkoutEvent) {
        when (event) {
            is CreateWorkoutEvent.OnTitleChanged -> updateTitle(event.title)
            is CreateWorkoutEvent.OnDateChanged -> updateDate(event.date)
            is CreateWorkoutEvent.OnTimeChanged -> updateTime(event.time)
            is CreateWorkoutEvent.OnWorkoutTypeChanged -> updateWorkoutType(event.type)

            CreateWorkoutEvent.OnCreateExercise -> createExercise()
            is CreateWorkoutEvent.OnToggleExerciseSelection -> toggleExerciseSelect(event.exerciseId)
            CreateWorkoutEvent.OnClearExerciseSelection -> clearExerciseSelection()
            CreateWorkoutEvent.OnDeleteSelectedExercises -> deleteSelectedExercises()
        }
    }

    // Info page
    private fun updateTitle(title: String) {
        updateContent {
            copy(title = title)
        }
    }

    private fun updateDate(date: String) {
        updateContent {
            copy(date = date)
        }
    }

    private fun updateTime(time: String) {
        updateContent {
            copy(time = time)
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
                name = "Упражнение ${exercises.size + 1}"
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
}
