package com.jacqulin.gainly.core.data.remote.service

import com.jacqulin.gainly.core.data.remote.dto.friends.FriendsResponseDto
import retrofit2.http.GET
import retrofit2.http.Header

interface FriendsApiService {

    @GET("api/friends/list")
    suspend fun getFriends(
        @Header("Authorization") accessToken: String
    ): FriendsResponseDto
}