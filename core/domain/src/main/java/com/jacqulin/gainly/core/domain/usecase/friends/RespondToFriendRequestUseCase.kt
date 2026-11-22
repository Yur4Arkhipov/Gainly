package com.jacqulin.gainly.core.domain.usecase.friends

import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.repository.FriendsRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class RespondToFriendRequestUseCase @Inject constructor(
    private val repository: FriendsRepository,
    private val tokenStorage: TokenStorage,
) {
    suspend operator fun invoke(
        friendshipId: String,
        accept: Boolean
    ) {
        val token = tokenStorage.tokens.firstOrNull()?.accessToken ?: ""

        repository.respondForPendingUser(
            accessToken = token,
            friendshipId = friendshipId,
            accept = accept
        )
    }
}
