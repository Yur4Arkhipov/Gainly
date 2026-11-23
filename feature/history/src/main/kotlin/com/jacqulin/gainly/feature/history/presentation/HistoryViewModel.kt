package com.jacqulin.gainly.feature.history.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacqulin.gainly.core.domain.usecase.history.DeleteWorkoutByIdUseCase
import com.jacqulin.gainly.core.domain.usecase.history.GenerateWeekDaysUseCase
import com.jacqulin.gainly.core.domain.usecase.history.GetWorkoutsForDateUseCase
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.ErrorUiMapper
import com.jacqulin.gainly.feature.history.presentation.model.HistoryEffect
import com.jacqulin.gainly.feature.history.presentation.model.HistoryEvent
import com.jacqulin.gainly.feature.history.presentation.model.HistoryViewState
import com.jacqulin.gainly.feature.history.presentation.model.WorkoutsUiState
import com.jacqulin.gainly.feature.history.presentation.model.toCardUi
import com.jacqulin.gainly.feature.history.presentation.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val generateWeekDaysUseCase: GenerateWeekDaysUseCase,
    private val getWorkoutsForDateUseCase: GetWorkoutsForDateUseCase,
    private val deleteWorkoutByIdUseCase: DeleteWorkoutByIdUseCase
) : ViewModel() {

    private val initialDate = LocalDate.now()

    private val _state = MutableStateFlow(
        HistoryViewState(
            selectedDate = initialDate,
            calendarDays = generateWeekDaysUseCase(initialDate)
                .map { it.toUi() }
        )
    )
    val state: StateFlow<HistoryViewState> = _state.asStateFlow()

    private val _effects = Channel<HistoryEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    init {
        fetchWorkoutsForDate(initialDate)
    }

    private var fetchJob: Job? = null

    fun handleEvent(event: HistoryEvent) {
        viewModelScope.launch {
            when (event) {
                is HistoryEvent.DateSelected -> {
                    selectDate(event.date)
                }

                is HistoryEvent.TodayClicked -> {
                    selectDate(LocalDate.now())
                }

                is HistoryEvent.RetryLoading -> {
                    fetchWorkoutsForDate(event.date)
                }

                is HistoryEvent.DeleteWorkout -> {
                    deleteWorkout(event.workoutId)
                }
            }
        }
    }

    private fun selectDate(date: LocalDate) {
        if (_state.value.selectedDate == date) return

        _state.update {
            it.copy(
                selectedDate = date,
                calendarDays = generateWeekDaysUseCase(date)
                    .map { day -> day.toUi() }
            )
        }

        fetchWorkoutsForDate(date)
    }

    private fun fetchWorkoutsForDate(date: LocalDate) {
        fetchJob?.cancel()

        fetchJob = viewModelScope.launch {
            _state.update {
                it.copy(workoutsState = WorkoutsUiState.Loading)
            }

            try {
                when (val result = getWorkoutsForDateUseCase(date)) {
                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                workoutsState = WorkoutsUiState.Success(
                                    workouts = result.data.map { workout ->
                                        workout.toCardUi()
                                    }
                                )
                            )
                        }
                    }

                    is Result.Error -> {
                        val message = ErrorUiMapper.toMessage(result.error)
                        _state.update {
                            it.copy(
                                workoutsState = WorkoutsUiState.Error(
                                    message = message
                                )
                            )
                        }
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        workoutsState = WorkoutsUiState.Error(
                            message = e.message ?: "Error fetching workouts"
                        )
                    )
                }
            }
        }
    }

    private fun deleteWorkout(workoutId: String) {
        viewModelScope.launch {
            try {
                when (val result = deleteWorkoutByIdUseCase(workoutId)) {
                    is Result.Success -> {
                        _state.update { currentState ->
                            val currentWorkoutsState = currentState.workoutsState

                            if (currentWorkoutsState is WorkoutsUiState.Success) {
                                currentState.copy(
                                    workoutsState = currentWorkoutsState.copy(
                                        workouts = currentWorkoutsState.workouts.filterNot {
                                            it.id == workoutId
                                        }
                                    )
                                )
                            } else {
                                currentState
                            }
                        }
                        _effects.send(HistoryEffect.ShowSnackbar("Тренировка удалена"))
                    }

                    is Result.Error -> {
                        val message = ErrorUiMapper.toMessage(result.error)
                        _effects.send(HistoryEffect.ShowSnackbar(message))
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        workoutsState = WorkoutsUiState.Error(
                            message = e.message ?: "Error fetching workouts"
                        )
                    )
                }
            }
        }
    }
}
