package com.jacqulin.gainly.core.domain.usecase.friends

import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.repository.FriendsRepository
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.AppError
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class SendFriendRequestUseCase @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val repository: FriendsRepository
) {
    suspend operator fun invoke(username: String): Result<Unit, AppError> {
        val token = tokenStorage.tokens.firstOrNull()?.accessToken
            ?: return Result.Error(
                AppError.LocalError(AppError.Local.TOKEN_NOT_FOUND)
            )

        return repository.sendFriendship(token, username)
    }
}
