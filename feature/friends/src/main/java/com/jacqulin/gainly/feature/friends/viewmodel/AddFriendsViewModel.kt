package com.jacqulin.gainly.feature.friends.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.model.friends.FriendData
import com.jacqulin.gainly.core.domain.model.friends.UserData
import com.jacqulin.gainly.core.domain.repository.FriendsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class AddFriendsViewModel @Inject constructor(
    private val repository: FriendsRepository,
    private val tokenStorage: TokenStorage,
) : ViewModel() {

    private val _friends = MutableStateFlow<List<FriendData>>(emptyList())
    val friends: StateFlow<List<FriendData>> = _friends

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _pendingUsers = MutableStateFlow<List<UserData>>(emptyList())
    val pendingUsers: StateFlow<List<UserData>> = _pendingUsers.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchResults: StateFlow<List<UserData>> =
        _searchQuery
            .debounce(500)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                if (query.isBlank() || query.length < 2) {
                    flowOf(emptyList())
                } else {
                    flow {
                        val authData = tokenStorage.tokens.firstOrNull()
                        val token = authData?.accessToken ?: return@flow
                        val result = repository.getUsers(token, query)
                        emit(result.users)
                    }
                }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun sendFriendRequest(username: String) {
        viewModelScope.launch {
            val authData = tokenStorage.tokens.firstOrNull()
            val token = authData?.accessToken ?: return@launch

            try {
                repository.sendFriendship(token, username)
                Log.d("FRIEND_REQUEST", "Friend request sent to $username")
            } catch (e: Exception) {
                Log.e("FRIEND_REQUEST", "Error sending friend request: $e")
            }
        }
    }

    fun loadPendingRequests() {
        viewModelScope.launch {
            val authData = tokenStorage.tokens.firstOrNull()
            val token = authData?.accessToken ?: return@launch

            try {
                val result = repository.getPendingUsers(token)
                val userDataList = result.pendingUsers.map { pendingUser ->
                    UserData(
                        userId = pendingUser.fromUserId,
                        username = pendingUser.fromUsername,
                        registrationDate = "",
                        isRequestSent = false,
                        friendshipId = pendingUser.friendshipId
                    )
                }
                _pendingUsers.value = userDataList
                Log.d("PENDING_REQUESTS", "Loaded pending requests: ${userDataList.size}")
            } catch (e: Exception) {
                Log.e("PENDING_REQUESTS", "Error loading pending requests: $e")
            }
        }
    }

    fun acceptPendingUser(username: String, friendshipId: String) {
        viewModelScope.launch {
            val authData = tokenStorage.tokens.firstOrNull()
            val token = authData?.accessToken ?: return@launch

            try {
                repository.respondForPendingUser(token, friendshipId, accept = true)
                _pendingUsers.value = _pendingUsers.value.filter { it.username != username }
                Log.d("PENDING_REQUESTS", "Accepted friend request from $username")
            } catch (e: Exception) {
                Log.e("PENDING_REQUESTS", "Error accepting friend request: $e")
            }
        }
    }

    fun rejectPendingUser(username: String, friendshipId: String) {
        viewModelScope.launch {
            val authData = tokenStorage.tokens.firstOrNull()
            val token = authData?.accessToken ?: return@launch

            try {
                repository.respondForPendingUser(token, friendshipId, accept = false)
                _pendingUsers.value = _pendingUsers.value.filter { it.username != username }
                Log.d("PENDING_REQUESTS", "Rejected friend request from $username")
            } catch (e: Exception) {
                Log.e("PENDING_REQUESTS", "Error rejecting friend request: $e")
            }
        }
    }
}