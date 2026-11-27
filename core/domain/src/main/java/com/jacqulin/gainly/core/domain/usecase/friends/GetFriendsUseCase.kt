package com.jacqulin.gainly.core.domain.usecase.friends

import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.model.friends.FriendsData
import com.jacqulin.gainly.core.domain.repository.FriendsRepository
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.AppError
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GetFriendsUseCase @Inject constructor(
    private val friendsRepository: FriendsRepository,
    val tokenStorage: TokenStorage
) {
    suspend operator fun invoke(): Result<FriendsData, AppError> {
        val accessToken = tokenStorage.tokens.firstOrNull()?.accessToken
            ?: return Result.Error(
                AppError.LocalError(AppError.Local.TOKEN_NOT_FOUND)
            )

        return friendsRepository.getFriends(accessToken)
    }
}
