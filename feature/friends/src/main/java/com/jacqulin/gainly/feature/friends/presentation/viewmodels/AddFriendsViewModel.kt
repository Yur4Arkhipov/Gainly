package com.jacqulin.gainly.feature.friends.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacqulin.gainly.core.domain.usecase.friends.GetPendingFriendRequestsUseCase
import com.jacqulin.gainly.core.domain.usecase.friends.GetUsersUseCase
import com.jacqulin.gainly.core.domain.usecase.friends.RespondToFriendRequestUseCase
import com.jacqulin.gainly.core.domain.usecase.friends.SendFriendRequestUseCase
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.ErrorUiMapper
import com.jacqulin.gainly.feature.friends.presentation.model.AddFriendsEffect
import com.jacqulin.gainly.feature.friends.presentation.model.AddFriendsEvent
import com.jacqulin.gainly.feature.friends.presentation.model.AddFriendsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class AddFriendsViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val sendFriendRequestUseCase: SendFriendRequestUseCase,
    private val respondToFriendRequestUseCase: RespondToFriendRequestUseCase,
    private val getPendingFriendRequestsUseCase: GetPendingFriendRequestsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddFriendsUiState())
    val uiState = _uiState.asStateFlow()

    private val _effects = Channel<AddFriendsEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    init {
        observeSearchQuery()
        loadPendingRequests()
    }

    fun onEvent(event: AddFriendsEvent) {
        when (event) {
            is AddFriendsEvent.SearchQueryChanged -> {
                _uiState.update {
                    it.copy(searchQuery = event.query)
                }
            }

            is AddFriendsEvent.SendFriendRequest -> sendFriendRequest(event.username)

            is AddFriendsEvent.RespondFriendRequest ->
                respondToFriendRequest(
                    username = event.username,
                    friendshipId = event.friendshipId,
                    accept = event.accept
                )

            AddFriendsEvent.OnBackClicked -> navigateToFriends()
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun observeSearchQuery() {
        uiState
            .map { it.searchQuery }
            .debounce(500.milliseconds)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                flow {
                    if (query.isBlank() || query.length < 2) {
                        emit(emptyList())
                        return@flow
                    }

                    _uiState.update {
                        it.copy(isSearching = true)
                    }

                    when (val result = getUsersUseCase(query)) {
                        is Result.Success -> {
                            emit(result.data.users)
                            Log.d("AddFriendsViewModel", "Success: ${result.data}")
                        }

                        is Result.Error -> {
                            _uiState.update {
                                it.copy(
                                    searchResult = emptyList(),
                                    isSearching = false
                                )
                            }

                            Log.d("AddFriendsViewModel", "Error: ${result.error}")
                            val message = ErrorUiMapper.toMessage(result.error)
                            _effects.send(AddFriendsEffect.ShowSnackbar(message))
                        }
                    }
                }
            }
            .onEach { users ->
                _uiState.update {
                    it.copy(
                        searchResult = users,
                        isSearching = false
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun sendFriendRequest(username: String) {
        viewModelScope.launch {
            when (val result = sendFriendRequestUseCase(username)) {
                is Result.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            searchResult = state.searchResult.map { user ->
                                if (user.username == username) {
                                    user.copy(isRequestSent = true)
                                } else {
                                    user
                                }
                            }
                        )
                    }

                    _effects.send(AddFriendsEffect.ShowSnackbar("Заявка отправлена"))
                }

                is Result.Error -> {
                    val message = ErrorUiMapper.toMessage(result.error)
                    _effects.send(AddFriendsEffect.ShowSnackbar(message))
                }
            }
        }
    }

    private fun loadPendingRequests() {
        viewModelScope.launch {
            when (val result = getPendingFriendRequestsUseCase()) {
                is Result.Success -> {
                    val users = result.data

                    _uiState.update {
                        it.copy(pendingUsers = users)
                    }
                }

                is Result.Error -> {
                    val message = ErrorUiMapper.toMessage(result.error)
                    _effects.send(AddFriendsEffect.ShowSnackbar(message))
                }
            }
        }
    }

    private fun respondToFriendRequest(
        username: String,
        friendshipId: String,
        accept: Boolean
    ) {
        viewModelScope.launch {
            when (val result = respondToFriendRequestUseCase(friendshipId, accept)) {
                is Result.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            pendingUsers = state.pendingUsers.filter {
                                it.username != username
                            }
                        )
                    }

                    val message = if (accept) {
                        "Заявка от $username принята"
                    } else {
                        "Заявка от $username отклонена"
                    }

                    _effects.send(AddFriendsEffect.ShowSnackbar(message))
                }

                is Result.Error -> {
                    val message = ErrorUiMapper.toMessage(result.error)
                    _effects.send(AddFriendsEffect.ShowSnackbar(message))
                }
            }
        }
    }

    private fun navigateToFriends() {
        viewModelScope.launch {
            _effects.send(AddFriendsEffect.NavigateToFriends)
        }
    }
}