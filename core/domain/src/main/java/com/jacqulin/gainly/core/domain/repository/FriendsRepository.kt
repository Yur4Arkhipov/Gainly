package com.jacqulin.gainly.core.domain.repository

import com.jacqulin.gainly.core.domain.model.friends.FriendsData
import com.jacqulin.gainly.core.domain.model.friends.UsersData

interface FriendsRepository {
    suspend fun getFriends(accessToken: String): FriendsData
    suspend fun getUsers(accessToken: String, nickname: String): UsersData
    suspend fun sendFriendship(accessToken: String, nickname: String)
}