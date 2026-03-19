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
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val repository: FriendsRepository,
    private val tokenStorage: TokenStorage,
) : ViewModel() {

    private val _friends = MutableStateFlow<List<FriendData>>(emptyList())
    val friends: StateFlow<List<FriendData>> = _friends

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

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
        _searchQuery.value = newQuery
    }
}