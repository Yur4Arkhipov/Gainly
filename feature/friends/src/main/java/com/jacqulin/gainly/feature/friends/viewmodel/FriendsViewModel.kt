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
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val repository: FriendsRepository,
    private val tokenStorage: TokenStorage,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

//    val friends: StateFlow<List<FriendData>> =
//        repository.observeFriends()
//            .stateIn(
//                viewModelScope,
//                SharingStarted.WhileSubscribed(5000),
//                emptyList()
//            )

    fun syncFriends() {
        viewModelScope.launch {
            val token = tokenStorage.tokens.firstOrNull()?.accessToken ?: return@launch
            try {
                val remoteFriends = repository.getFriends(token)
                repository.syncFriends(remoteFriends.friends)
                Log.d("Friends VM", "friends synced")
            } catch (e: Exception) {
                Log.e("Friends VM", "friends sync error: $e")
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val filteredFriends: StateFlow<List<FriendData>> =
        _searchQuery
            .debounce(300.milliseconds)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                repository.observeFriends(query)
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