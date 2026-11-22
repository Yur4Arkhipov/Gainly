package com.jacqulin.gainly.core.domain.usecase.friends

import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.repository.FriendsRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class SendFriendRequestUseCase @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val repository: FriendsRepository
) {
    suspend operator fun invoke(username: String) {
//        val token = tokenStorage.tokens.firstOrNull()?.accessToken
//            ?: return Result.Error(
//                WorkoutError.LocalError(WorkoutError.Local.TOKEN_NOT_FOUND)
//            )
        val token = tokenStorage.tokens.firstOrNull()?.accessToken ?: ""

        repository.sendFriendship(token, username)
    }
}
