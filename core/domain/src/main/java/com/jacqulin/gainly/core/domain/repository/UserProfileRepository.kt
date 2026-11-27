package com.jacqulin.gainly.core.domain.repository

import com.jacqulin.gainly.core.model.UserProfile
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.AppError

interface UserProfileRepository {
    suspend fun getUserInfo(
        accessToken: String,
    ): Result<UserProfile, AppError>
}
