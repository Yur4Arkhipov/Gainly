package com.jacqulin.gainly.core.domain.repository

import com.jacqulin.gainly.core.domain.model.friends.FriendData
import com.jacqulin.gainly.core.domain.model.friends.FriendsData
import com.jacqulin.gainly.core.domain.model.friends.PendingUsersData
import com.jacqulin.gainly.core.domain.model.friends.UsersData
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.AppError
import kotlinx.coroutines.flow.Flow

interface FriendsRepository {
    suspend fun getFriends(accessToken: String): Result<FriendsData, AppError>

    suspend fun getUsers(
        accessToken: String,
        nickname: String
    ): Result<UsersData, AppError>

    suspend fun sendFriendship(
        accessToken: String,
        nickname: String
    ): Result<Unit, AppError>

    suspend fun saveFriendsLocal(friends: List<FriendData>)

    suspend fun getPendingUsers(
        accessToken: String
    ): Result<PendingUsersData, AppError>

    suspend fun respondForPendingUser(
        accessToken: String,
        friendshipId: String,
        accept: Boolean
    ): Result<Unit, AppError>

    // Offline-first methods
    fun observeFriends(query: String): Flow<List<FriendData>>
    suspend fun syncFriends(remote: List<FriendData>)
}