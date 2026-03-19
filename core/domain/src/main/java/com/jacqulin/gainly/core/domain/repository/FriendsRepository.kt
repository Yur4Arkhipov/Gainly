package com.jacqulin.gainly.core.domain.repository

import com.jacqulin.gainly.core.domain.model.friends.FriendData
import com.jacqulin.gainly.core.domain.model.friends.FriendsData
import com.jacqulin.gainly.core.domain.model.friends.PendingUsersData
import com.jacqulin.gainly.core.domain.model.friends.UsersData
import kotlinx.coroutines.flow.Flow

interface FriendsRepository {
    suspend fun getFriends(accessToken: String): FriendsData
    suspend fun getUsers(accessToken: String, nickname: String): UsersData
    suspend fun sendFriendship(accessToken: String, nickname: String)
    fun searchFriendsLocal(query: String): Flow<List<FriendData>>
    suspend fun saveFriendsLocal(friends: List<FriendData>)
    fun observeFriendsLocal(): Flow<List<FriendData>>
    suspend fun syncFriends(remote: List<FriendData>)
    suspend fun getPendingUsers(accessToken: String): PendingUsersData
}