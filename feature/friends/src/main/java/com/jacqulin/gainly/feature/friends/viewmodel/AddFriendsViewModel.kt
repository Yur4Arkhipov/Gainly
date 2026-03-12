package com.jacqulin.gainly.feature.friends.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class AddFriendsViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(AddFriendsUiState())
    val uiState: StateFlow<AddFriendsUiState> = _uiState.asStateFlow()

    init {
        loadPendingRequests()
    }

    fun toggleExpansion() {
        _uiState.update { current ->
            current.copy(isExpanded = !current.isExpanded)
        }
    }

    private fun loadPendingRequests() {
        // Temporary stub data until repository integration is ready.
        val fakeRequests = listOf(
            FriendRequestUiModel(id = "1", username = "Софья К.", mutualFriends = 3),
            FriendRequestUiModel(id = "2", username = "Алиса П.", mutualFriends = 1),
            FriendRequestUiModel(id = "3", username = "Марина Т.", mutualFriends = 0),
            FriendRequestUiModel(id = "4", username = "Ярослав Ф.", mutualFriends = 2),
            FriendRequestUiModel(id = "5", username = "Иван Н.", mutualFriends = 4),
        )

        _uiState.update { current ->
            current.copy(pendingRequests = fakeRequests)
        }
    }

    companion object {
        const val PREVIEW_LIMIT = 3
    }
}

data class AddFriendsUiState(
    val pendingRequests: List<FriendRequestUiModel> = emptyList(),
    val isExpanded: Boolean = false,
)

data class FriendRequestUiModel(
    val id: String,
    val username: String,
    val mutualFriends: Int,
)