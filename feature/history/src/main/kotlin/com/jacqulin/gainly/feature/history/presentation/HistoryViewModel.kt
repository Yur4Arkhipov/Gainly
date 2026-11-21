package com.jacqulin.gainly.feature.history.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacqulin.gainly.core.domain.usecase.history.GenerateWeekDaysUseCase
import com.jacqulin.gainly.core.domain.usecase.history.GetWorkoutsForDateUseCase
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.feature.history.presentation.model.HistoryEvent
import com.jacqulin.gainly.feature.history.presentation.model.HistoryViewState
import com.jacqulin.gainly.feature.history.presentation.model.toCardUi
import com.jacqulin.gainly.feature.history.presentation.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
        value = HistoryViewState(
            calendarDays = generateWeekDaysUseCase(initialDate).map { it.toUi() },
            selectedDate = initialDate,
            isLoading = true
        )
    )
    val state: StateFlow<HistoryViewState> = _state

    init {
        fetchWorkoutsForDate(initialDate)
    }

    private var fetchJob: Job? = null

    fun handleEvent(event: HistoryEvent) {
        viewModelScope.launch {
            when (event) {
                is HistoryEvent.LoadWorkoutsForDate -> {
                    fetchWorkoutsForDate(event.date)
                }
                is HistoryEvent.DateSelected -> {
                    selectDate(event.date)
                }
                is HistoryEvent.MonthSelected -> {
//                    selectDate(event.date)
                }
                is HistoryEvent.TodayClicked -> {
                    selectDate(LocalDate.now())
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
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            try {
                when (val result = getWorkoutsForDateUseCase(date)) {
                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                workouts = result.data.map { workout ->
                                    workout.toCardUi()
                                },
                                errorMessage = null
                            )
                        }
                    }

                    is Result.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = result.error.toString()
                            )
                        }
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error fetching workouts"
                    )
                }
            }
        }
    }
}
