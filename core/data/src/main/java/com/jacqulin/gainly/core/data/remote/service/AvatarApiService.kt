package com.jacqulin.gainly.core.data.remote.service

import com.jacqulin.gainly.core.data.remote.dto.avatar.AvatarResponseDto
import com.jacqulin.gainly.core.data.remote.dto.avatar.AvatarsRequestDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface AvatarApiService {

    @GET("api/avatar")
    suspend fun getAvatar(
        @Header("Authorization") accessToken: String,
        @Query("username") username: String
    ): AvatarResponseDto

    @POST("api/avatars")
    suspend fun getAvatars(
        @Body request: AvatarsRequestDto
    ): Map<String, String>
}

