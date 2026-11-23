package com.jacqulin.gainly.core.domain.usecase.profile

import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.repository.UserProfileRepository
import com.jacqulin.gainly.core.model.UserProfile
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.AppError
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GetUserProfileInfoUseCase @Inject constructor(
    val repository: UserProfileRepository,
    val tokenStorage: TokenStorage
) {
    suspend operator fun invoke(): Result<UserProfile, AppError> {
        val accessToken = tokenStorage.tokens.firstOrNull()?.accessToken
            ?: return Result.Error(
                AppError.LocalError(AppError.Local.TOKEN_NOT_FOUND)
            )

        return repository.getUserInfo(accessToken = "Bearer $accessToken")
    }
}
