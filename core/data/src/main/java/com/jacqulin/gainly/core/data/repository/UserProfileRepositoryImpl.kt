package com.jacqulin.gainly.core.data.repository

import com.jacqulin.gainly.core.data.remote.dto.user.toDomain
import com.jacqulin.gainly.core.data.remote.service.UserApiService
import com.jacqulin.gainly.core.domain.repository.UserProfileRepository
import com.jacqulin.gainly.core.model.UserProfile
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.AppError
import com.jacqulin.gainly.core.util.errors.ErrorHandler

class UserProfileRepositoryImpl(
    private val userApiService: UserApiService,
): UserProfileRepository {
    override suspend fun getUserInfo(
        accessToken: String
    ): Result<UserProfile, AppError> {
        return try {
            val response = userApiService.getAboutUserInfo(accessToken)
            Result.Success(response.toDomain())
        } catch (e: Exception) {
            Result.Error(ErrorHandler.mapAppError(e))
        }
    }
}
