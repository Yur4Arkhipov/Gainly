package com.jacqulin.gainly.feature.workout.presentation

import androidx.lifecycle.ViewModel
import com.jacqulin.gainly.core.domain.usecase.workout.CreateWorkoutUseCase
import com.jacqulin.gainly.feature.workout.presentation.model.CreateWorkoutEvent
import com.jacqulin.gainly.feature.workout.presentation.model.CreateWorkoutUiState
import com.jacqulin.gainly.feature.workout.presentation.model.WorkoutType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
        }
    }

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

    fun updateWorkoutType(type: WorkoutType) {
        updateContent {
            copy(workoutType = type)
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
