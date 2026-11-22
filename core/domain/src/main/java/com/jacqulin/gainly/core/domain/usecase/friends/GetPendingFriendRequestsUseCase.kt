package com.jacqulin.gainly.core.domain.usecase.friends

import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.model.friends.UserData
import com.jacqulin.gainly.core.domain.repository.FriendsRepository
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.AppError
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GetPendingFriendRequestsUseCase @Inject constructor(
    private val repository: FriendsRepository,
    private val tokenStorage: TokenStorage,
) {
    suspend operator fun invoke(): Result<List<UserData>, AppError> {
        val token = tokenStorage.tokens.firstOrNull()?.accessToken
            ?: return Result.Error(
                AppError.LocalError(AppError.Local.TOKEN_NOT_FOUND)
            )

        return when (val result = repository.getPendingUsers(token)) {
            is Result.Success -> {
                val users = result.data.pendingUsers.map { pendingUser ->
                    UserData(
                        userId = pendingUser.fromUserId,
                        username = pendingUser.fromUsername,
                        registrationDate = "",
                        isRequestSent = false,
                        friendshipId = pendingUser.friendshipId
                    )
                }

                Result.Success(users)
            }

            is Result.Error -> {
                Result.Error(result.error)
            }
        }
    }
}
