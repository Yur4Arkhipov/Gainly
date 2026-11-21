package com.jacqulin.gainly.feature.history.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacqulin.gainly.core.domain.usecase.history.GenerateWeekDaysUseCase
import com.jacqulin.gainly.core.domain.usecase.history.GetWorkoutsForDateUseCase
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.ErrorUiMapper
import com.jacqulin.gainly.feature.history.presentation.model.HistoryEvent
import com.jacqulin.gainly.feature.history.presentation.model.HistoryViewState
import com.jacqulin.gainly.feature.history.presentation.model.WorkoutsUiState
import com.jacqulin.gainly.feature.history.presentation.model.toCardUi
import com.jacqulin.gainly.feature.history.presentation.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val generateWeekDaysUseCase: GenerateWeekDaysUseCase,
    private val getWorkoutsForDateUseCase: GetWorkoutsForDateUseCase
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
}
