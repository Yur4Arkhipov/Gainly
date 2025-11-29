package com.jacqulin.gainly.feature.friends.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.model.friends.FriendData
import com.jacqulin.gainly.core.domain.model.friends.UserData
import com.jacqulin.gainly.core.domain.repository.FriendsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val repository: FriendsRepository,
    private val tokenStorage: TokenStorage,
) : ViewModel() {

    private val _friends = MutableStateFlow<List<FriendData>>(emptyList())
    val friends: StateFlow<List<FriendData>> = _friends

    var searchQuery by mutableStateOf("")
        private set

    private val _searchResults = MutableStateFlow<List<UserData>>(emptyList())
    val searchResults: StateFlow<List<UserData>> = _searchResults

    fun getUsers() {
        viewModelScope.launch {
            val authData = tokenStorage.tokens.firstOrNull()
            val token = authData?.accessToken

            if (token == null) {
                Log.d("TOKEN_FRIENDS", "Access token not found")
                return@launch
            }

            val result = repository.getFriends(token)
            _friends.value = result.friends
            Log.d("FRIENDS", "Friend: ${result.friends}")
        }
    }

    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
    }

    init {
        observeSearchQuery()
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            snapshotFlow { searchQuery }
                .debounce(500)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isBlank()) {
                        _searchResults.value = emptyList()
                        return@collect
                    }
                    if (query.length < 2) {
                        _searchResults.value = emptyList()
                        return@collect
                    }
                    searchUsers(query)
                }
        }
    }

    private suspend fun searchUsers(query: String) {
        val authData = tokenStorage.tokens.firstOrNull()
        val token = authData?.accessToken ?: return

        try {
            val result = repository.getUsers(token, query)
            _searchResults.value = result.users
        } catch (e: Exception) {
            Log.e("SEARCH", "Error: $e")
        }
    }

    fun sendFriendRequest(nickname: String) {
        viewModelScope.launch {
            val authData = tokenStorage.tokens.firstOrNull()
            val token = authData?.accessToken

            if (token == null) {
                Log.d("TOKEN_FRIENDS", "Access token not found")
                return@launch
            }

            try {
                val result = repository.sendFriendship(token, nickname)
                Log.d("FRIENDS", "Friend request sent to $nickname")
                _searchResults.update { list ->
                    list.map { user ->
                        if (user.username == nickname) user.copy(isRequestSent = true)
                        else user
                    }
                }

            } catch (e: Exception) {
                Log.e("FRIENDS", "Error sending friend request: $e")
            }
        }
    }
}