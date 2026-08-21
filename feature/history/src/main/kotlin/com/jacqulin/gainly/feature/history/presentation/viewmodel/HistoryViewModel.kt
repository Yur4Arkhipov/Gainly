package com.jacqulin.gainly.feature.history.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.jacqulin.gainly.feature.history.data.usecase.GenerateWeekDaysUseCase
import com.jacqulin.gainly.feature.history.presentation.model.CalendarDay
import com.jacqulin.gainly.feature.history.presentation.model.TrainingCardModel
import com.jacqulin.gainly.feature.history.presentation.model.TrainingInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    generateWeekDaysUseCase: GenerateWeekDaysUseCase,
//    private val getDayDataUseCase: GetDayDataUseCase
) : ViewModel() {

    private val initialDate = LocalDate.now()

    private val _uiState = MutableStateFlow(
        value = HistoryUiState(
            selectedDate = initialDate,
            calendarDays = generateWeekDaysUseCase(),
            trainingCards = getMockTrainingCards(initialDate)
        )
    )
    val uiState = _uiState.asStateFlow()

    fun onDateSelected(date: LocalDate) {
        _uiState.update {
            it.copy(
                selectedDate = date,
                trainingCards = getMockTrainingCards(date)
            )
        }
    }

    private fun getMockTrainingCards(
        date: LocalDate
    ): List<TrainingCardModel> {
        return when (date.dayOfWeek) {

            DayOfWeek.MONDAY -> listOf(
                TrainingCardModel(
                    id = "1",
                    trainingInfoList = listOf(
                        TrainingInfo(
                            title = "Подтягивания",
                            text = "4 × 10"
                        ),
                        TrainingInfo(
                            title = "Отжимания",
                            text = "4 × 15"
                        ),
                        TrainingInfo(
                            title = "Приседания",
                            text = "smth"
                        ),
                        TrainingInfo(
                            title = "Жим",
                            text = "smth"
                        )
                    )
                ),
                TrainingCardModel(
                    id = "2",
                    trainingInfoList = listOf(
                        TrainingInfo(
                            title = "Приседания",
                            text = "3 × 20"
                        ),
                        TrainingInfo(
                            title = "Подтягивания",
                            text = "smth"
                        ),
                        TrainingInfo(
                            title = "Отжимания",
                            text = "smth"
                        ),
                        TrainingInfo(
                            title = "Тяга",
                            text = "smth"
                        )
                    )
                )
            )

            DayOfWeek.TUESDAY -> listOf(
                TrainingCardModel(
                    id = "3",
                    trainingInfoList = listOf(
                        TrainingInfo(
                            title = "Бег",
                            text = "5 км"
                        ),
                        TrainingInfo(
                            title = "Подтягивания",
                            text = "smth"
                        )
                    )
                )
            )
            DayOfWeek.WEDNESDAY -> listOf(
                TrainingCardModel(
                    id = "4",
                    trainingInfoList = listOf(
                        TrainingInfo(
                            title = "Жим лёжа",
                            text = "4 × 8"
                        ),
                        TrainingInfo(
                            title = "Тяга",
                            text = "4 × 10"
                        ),
                        TrainingInfo(
                            title = "Планка",
                            text = "3 × 60 сек"
                        ),
                        TrainingInfo(
                            title = "Бег",
                            text = "smth"
                        )
                    )
                )
            )

            else -> emptyList()
        }
    }
}

data class HistoryUiState(
    val selectedDate: LocalDate,
    val calendarDays: List<CalendarDay>,
    val trainingCards: List<TrainingCardModel>,
)

/*
@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getWorkoutHistoryUseCase: GetWorkoutHistoryUseCase,
    private val getWorkoutByIdUseCase: GetWorkoutByIdUseCase,
    private val tokenStorage: TokenStorage,
    private val tokenRefresher: TokenRefresher,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<WorkoutListItem>>>(UiState.Idle)
    val uiState: StateFlow<UiState<List<WorkoutListItem>>> = _uiState

    private val _workoutDetailsState = MutableStateFlow<UiState<WorkoutById>>(UiState.Idle)
    val workoutDetailsState: StateFlow<UiState<WorkoutById>> = _workoutDetailsState

    private val _expandedWorkoutId = MutableStateFlow<String?>(null)
    val expandedWorkoutId: StateFlow<String?> = _expandedWorkoutId

    @OptIn(ExperimentalTime::class)
    fun loadWorkoutHistoryByDateRange(startDate: LocalDate, endDate: LocalDate) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val formatter = DateTimeFormatter.ISO_INSTANT

                val from = startDate
                    .atStartOfDay(ZoneOffset.UTC)
                    .toInstant()
                    .let { formatter.format(it) }

                val to = endDate
                    .atTime(23, 59, 59)
                    .atOffset(ZoneOffset.UTC)
                    .toInstant()
                    .let { formatter.format(it) }

                val result = runWithRefreshOn401(
                    actionName = "getWorkoutHistory"
                ) {
                    getWorkoutHistoryUseCase(
                        from = from,
                        to = to,
                        last = 20
                    )
                }

                _uiState.value = when (result) {
                    is Result.Success -> {
                        Log.d("HistoryViewModel", "Loaded ${result.data.size} workouts from sync")
                        val formatted = result.data.map { workout ->
                            workout.copy(
                                date = formatWorkoutDate(workout.date)
                            )
                        }
                        UiState.Success(formatted)
                    }

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

    @OptIn(ExperimentalTime::class)
    fun formatWorkoutDate(dateString: String): String {
        return try {
            val instant = Instant.parse(dateString)

            val localDateTime = instant
                .atZone(ZoneId.systemDefault())

            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")

            formatter.format(localDateTime)

        } catch (e: Exception) {
            dateString
        }
    }

    fun loadWorkoutDetails(workoutId: String) {
        viewModelScope.launch {
            _workoutDetailsState.value = UiState.Loading
            try {
                val result = runWithRefreshOn401(
                    actionName = "getWorkoutById"
                ) {
                    getWorkoutByIdUseCase(workoutId)
                }

                _workoutDetailsState.value = when (result) {
                    is Result.Success -> {
                        Log.d("HistoryViewModel", "Loaded workout details for $workoutId")
                        UiState.Success(result.data)
                    }

                    is Result.Error -> {
                        Log.e("HistoryViewModel", "Error loading workout details: ${result.error}")
                        UiState.Error(ErrorUiMapper.toMessage(result.error))
                    }
                }
            } catch (e: Exception) {
                Log.e("HistoryViewModel", "Exception loading workout details", e)
                _workoutDetailsState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun toggleWorkoutExpanded(workoutId: String) {
        val currentId = _expandedWorkoutId.value
        _expandedWorkoutId.value = if (currentId == workoutId) null else workoutId

        // Загружаем детали при раскрытии
        if (_expandedWorkoutId.value == workoutId) {
            loadWorkoutDetails(workoutId)
        }
    }

    private suspend fun <T> runWithRefreshOn401(
        actionName: String,
        block: suspend () -> Result<T, WorkoutError>
    ): Result<T, WorkoutError> {
        val first = block()

        if (first is Result.Error && first.error is WorkoutError.HttpError &&
            (first.error as WorkoutError.HttpError).type == WorkoutError.Http.UNAUTHORIZED
        ) {
            Log.w("HistoryViewModel", "$actionName got 401 -> trying refresh token and retry")

            val refreshed = refreshTokenOnce()
            if (!refreshed) return first

            return block()
        }

        return first
    }

    private suspend fun refreshTokenOnce(): Boolean {
        val tokens = tokenStorage.tokens.firstOrNull() ?: return false

        return try {
            val newTokens = tokenRefresher.refreshToken(tokens.refreshToken)
            tokenStorage.saveTokens(newTokens)
            true
        } catch (t: Throwable) {
            Log.e("HistoryViewModel", "Refresh token failed", t)
            false
        }
    }
}*/
