package com.jacqulin.gainly.core.data.repository

import com.jacqulin.gainly.core.data.mappers.toUsersData
import com.jacqulin.gainly.core.data.local.dao.FriendDao
import com.jacqulin.gainly.core.data.local.entity.FriendEntity
import com.jacqulin.gainly.core.data.mappers.toFriendsData
import com.jacqulin.gainly.core.data.mappers.toDomain
import com.jacqulin.gainly.core.data.mappers.toEntity
import com.jacqulin.gainly.core.data.remote.service.FriendsApiService
import com.jacqulin.gainly.core.domain.model.friends.FriendData
import com.jacqulin.gainly.core.domain.model.friends.FriendsData
import com.jacqulin.gainly.core.domain.model.friends.UsersData
import com.jacqulin.gainly.core.domain.repository.FriendsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FriendsRepositoryImpl(
    private val api: FriendsApiService,
    private val friendDao: FriendDao
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
        api.sendFriendshipRequest(
            accessToken = "Bearer $accessToken",
            nickname = nickname
        )
    }

    override fun searchFriendsLocal(query: String): Flow<List<FriendData>> {
        return friendDao.searchFriends(query).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun saveFriendsLocal(friends: List<FriendData>) {
        val entities = friends.map { friend ->
            FriendEntity(
                userId = friend.userId,
                username = friend.username,
                registrationDate = friend.registrationDate
            )
        }
        friendDao.insertFriends(entities)
    }

    override fun observeFriendsLocal(): Flow<List<FriendData>> {
        return friendDao.observeFriends().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun syncFriends(remote: List<FriendData>) {
        val local = friendDao.getAllFriendsOnce()

        val localMap = local.associateBy { it.userId }
        val remoteMap = remote.associateBy { it.userId }

        val toUpsert = remote.filter { remoteFriend ->
            val localFriend = localMap[remoteFriend.userId]
            localFriend == null || localFriend != remoteFriend.toEntity()
        }

        val toDeleteIds = local
            .filter { it.userId !in remoteMap.keys }
            .map { it.userId }

        if (toUpsert.isNotEmpty()) {
            friendDao.insertFriends(toUpsert.map { it.toEntity() })
        }

        if (toDeleteIds.isNotEmpty()) {
            friendDao.deleteFriendsByIds(toDeleteIds)
        }
    }
}