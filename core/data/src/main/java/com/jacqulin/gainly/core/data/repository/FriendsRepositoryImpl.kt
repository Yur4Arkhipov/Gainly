package com.jacqulin.gainly.core.data.repository

import com.jacqulin.gainly.core.data.mappers.toFriendsData
import com.jacqulin.gainly.core.data.mappers.toUsersData
import com.jacqulin.gainly.core.data.remote.service.FriendsApiService
import com.jacqulin.gainly.core.domain.model.friends.FriendsData
import com.jacqulin.gainly.core.domain.model.friends.UsersData
import com.jacqulin.gainly.core.domain.repository.FriendsRepository

class FriendsRepositoryImpl(
    private val api: FriendsApiService
) : FriendsRepository {

    override suspend fun getFriends(accessToken: String): FriendsData {
        val response = api.getFriends("Bearer $accessToken")
        return response.toFriendsData()
    }

    override suspend fun getUsers(accessToken: String, nickname: String): UsersData {
        val response = api.getUsers(
            accessToken = "Bearer $accessToken",
            nickname = nickname
        )
        return response.toUsersData()
    }

    override suspend fun sendFriendship(accessToken: String, nickname: String) {
        val response = api.sendFriendshipRequest(
            accessToken = "Bearer $accessToken",
            nickname = nickname
        )
    }
}