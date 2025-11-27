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
import com.jacqulin.gainly.core.domain.repository.FriendsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
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
            Log.d("FRIENDS", "Friend1: ${result.friends}")
        }
    }

    var searchQuery by mutableStateOf("")
        private set


    private val moviesFlow = flowOf(
        listOf(
            Movie(
                id = 1,
                name = "Inception",
                rating = 8.8
            ),
            Movie(
                id = 2,
                name = "The Prestige",
                rating = 8.5
            ),
            Movie(
                id = 3,
                name = "Interstellar",
                rating = 8.7
            )
        )
    )



    val searchResults: StateFlow<List<Movie>> =
        snapshotFlow { searchQuery }
            .combine(moviesFlow) { searchQuery, movies ->
                when {
                    searchQuery.isNotEmpty() -> movies.filter { movie ->
                        movie.name.contains(searchQuery, ignoreCase = true)
                    }
                    else -> movies
                }
            }.stateIn(
                scope = viewModelScope,
                initialValue = emptyList(),
                started = SharingStarted.WhileSubscribed(5_000)
            )

    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
    }

}

data class Movie(
    val id: Long,
    val name: String,
    val rating: Double
)