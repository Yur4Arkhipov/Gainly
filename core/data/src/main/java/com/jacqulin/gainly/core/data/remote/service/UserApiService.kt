package com.jacqulin.gainly.core.data.remote.service

import com.jacqulin.gainly.core.data.remote.dto.user.UserProfileDto
import retrofit2.http.GET
import retrofit2.http.Header

interface UserApiService {

    /**
     * Получение информации о пользователе
     */
    @GET("auth/api/user/me")
    suspend fun getAboutUserInfo(
        @Header("Authorization") accessToken: String,
    ): UserProfileDto
}
