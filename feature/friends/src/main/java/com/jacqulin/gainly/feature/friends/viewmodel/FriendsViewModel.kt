package com.jacqulin.gainly.feature.friends.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.model.friends.FriendData
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
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val repository: FriendsRepository,
    private val tokenStorage: TokenStorage,
) : ViewModel() {

    val friends: StateFlow<List<FriendData>> =
        repository.observeFriendsLocal()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    fun syncFriends() {
        viewModelScope.launch {
            val token = tokenStorage.tokens.firstOrNull()?.accessToken ?: return@launch
            try {
                val remoteFriends = repository.getFriends(token)
                repository.syncFriends(remoteFriends.friends)
            } catch (e: Exception) {
                Log.e("SYNC", "Error: $e")
            }
        }
    }

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val filteredFriends: StateFlow<List<FriendData>> =
        _searchQuery
            .debounce(300)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                if (query.isBlank()) {
                    repository.observeFriendsLocal()
                } else {
                    repository.searchFriendsLocal(query)
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
}