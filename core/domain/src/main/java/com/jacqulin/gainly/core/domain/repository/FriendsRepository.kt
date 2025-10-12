package com.jacqulin.gainly.core.domain.repository

import com.jacqulin.gainly.core.domain.model.friends.FriendsData

interface FriendsRepository {
    suspend fun getFriends(accessToken: String): FriendsData
}