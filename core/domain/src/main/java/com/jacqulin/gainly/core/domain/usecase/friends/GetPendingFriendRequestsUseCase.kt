package com.jacqulin.gainly.core.domain.usecase.friends

import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.model.friends.UserData
import com.jacqulin.gainly.core.domain.repository.FriendsRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GetPendingFriendRequestsUseCase @Inject constructor(
    private val repository: FriendsRepository,
    private val tokenStorage: TokenStorage,
) {
    suspend operator fun invoke(): List<UserData> {
        val token = tokenStorage.tokens.firstOrNull()?.accessToken ?: ""

        val result = repository.getPendingUsers(token)

        return result.pendingUsers.map { pendingUser ->
            UserData(
                userId = pendingUser.fromUserId,
                username = pendingUser.fromUsername,
                registrationDate = "",
                isRequestSent = false,
                friendshipId = pendingUser.friendshipId
            )
        }
    }
}
