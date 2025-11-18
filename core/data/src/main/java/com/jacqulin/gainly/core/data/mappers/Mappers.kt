package com.jacqulin.gainly.core.data.mappers

import com.jacqulin.gainly.core.data.remote.dto.friends.FriendDto
import com.jacqulin.gainly.core.data.remote.dto.friends.FriendsResponseDto
import com.jacqulin.gainly.core.domain.model.friends.FriendData
import com.jacqulin.gainly.core.domain.model.friends.FriendsData

fun FriendsResponseDto.toFriendsData(): FriendsData {
    return FriendsData(
        friends = this.map { it.toDomain() }
    )
}

private fun FriendDto.toDomain(): FriendData {
    return FriendData(
        userId = this.id,
        username = this.username,
        registrationDate = this.registrationDate
    )
}