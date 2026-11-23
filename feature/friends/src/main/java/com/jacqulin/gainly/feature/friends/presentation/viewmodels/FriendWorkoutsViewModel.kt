package com.jacqulin.gainly.feature.friends.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacqulin.gainly.core.domain.usecase.friends.GetFriendWorkoutsForDateUseCase
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.ErrorUiMapper
import com.jacqulin.gainly.feature.friends.presentation.model.FriendWorkoutsUiState
import com.jacqulin.gainly.feature.friends.presentation.model.WorkoutsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class FriendWorkoutsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getFriendWorkoutsForDateUseCase: GetFriendWorkoutsForDateUseCase,
) : ViewModel() {

    private val username: String = checkNotNull(savedStateHandle["username"])

    private val _state = MutableStateFlow(
        FriendWorkoutsUiState(username = username)
    )
    val uiState = _state.asStateFlow()

    init {
        // Load last 10 workouts
        loadWorkouts()
    }

    fun loadWorkouts() {
        viewModelScope.launch {
            _state.update {
                it.copy(workoutsState = WorkoutsUiState.Loading)
            }

            try {
                when (val result = getFriendWorkoutsForDateUseCase(username)) {
                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                workoutsState = WorkoutsUiState.Success(workouts = result.data)
                            )
                        }
                    }
                    is Result.Error -> {
                        val message = ErrorUiMapper.toMessage(result.error)
                        _state.update {
                            it.copy(
                                workoutsState = WorkoutsUiState.Error(message = message)
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
