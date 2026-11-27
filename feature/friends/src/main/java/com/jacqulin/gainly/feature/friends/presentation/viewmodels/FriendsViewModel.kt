package com.jacqulin.gainly.feature.friends.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacqulin.gainly.core.domain.usecase.friends.GetFriendsUseCase
import com.jacqulin.gainly.core.domain.usecase.friends.ObserveFriendsUseCase
import com.jacqulin.gainly.feature.friends.presentation.model.FriendsEffect
import com.jacqulin.gainly.feature.friends.presentation.model.FriendsEvent
import com.jacqulin.gainly.feature.friends.presentation.model.FriendsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds
import com.jacqulin.gainly.core.util.Result

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val observeFriendsUseCase: ObserveFriendsUseCase,
    private val getFriendsUseCase: GetFriendsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FriendsUiState())
    val uiState = _uiState.asStateFlow()

    private val _effects = Channel<FriendsEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    init {
//        observeFriends()
        loadFriends()
    }

    fun onEvent(event: FriendsEvent) {
        when (event) {
            is FriendsEvent.SearchQueryChanged -> {
                _uiState.update {
                    it.copy(searchQuery = event.query)
                }
            }

            FriendsEvent.AddFriendsClicked -> navigateToAddFriends()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun observeFriends() {
        _uiState
            .map { it.searchQuery }
            .debounce(300.milliseconds)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                observeFriendsUseCase(query)
            }
            .onEach { friends ->
                _uiState.update {
                    it.copy(
                        friends = friends,
                        isLoading = false
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun loadFriends() {
        viewModelScope.launch {
            when (val result = getFriendsUseCase()) {
                is Result.Success -> {
                    _uiState.update { it.copy(friends = result.data.friends) }
                }
                is Result.Error -> {
                    // Error loading friends
                }
            }
        }
    }

    private fun navigateToAddFriends() {
        viewModelScope.launch {
            _effects.send(FriendsEffect.NavigateToAddFriends)
        }
    }
}
