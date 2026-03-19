package com.jacqulin.gainly.core.data.remote.service

import com.jacqulin.gainly.core.data.remote.dto.friends.FriendsResponseDto
import com.jacqulin.gainly.core.data.remote.dto.friends.PendingUsersResponseDto
import com.jacqulin.gainly.core.data.remote.dto.friends.UsersResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FriendsApiService {

    @GET("auth/api/friends/list")
    suspend fun getFriends(
        @Header("Authorization") accessToken: String
    ): FriendsResponseDto

    @GET("auth/api/friends/get-users")
    suspend fun getUsers(
        @Header("Authorization") accessToken: String,
        @Query("nickname") nickname: String
    ): UsersResponseDto


    @POST("auth/api/friends/send-request-by-username/{friendUsername}")
    suspend fun sendFriendshipRequest(
        @Header("Authorization") accessToken: String,
        @Path("friendUsername") nickname: String
    )

    @GET("auth/api/friends/pending")
    suspend fun getPendingUsers(
        @Header("Authorization") accessToken: String
    ): PendingUsersResponseDto

    @POST("auth/api/friends/respond/{friendshipId}")
    suspend fun respondForPendingUser(
        @Header("Authorization") accessToken: String,
        @Path("friendshipId") friendshipId: String,
        @Query("accept") accept: Boolean
    )
}