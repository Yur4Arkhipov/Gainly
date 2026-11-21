package com.jacqulin.gainly.core.data.repository

import com.jacqulin.gainly.core.data.remote.dto.avatar.AvatarsRequestDto
import com.jacqulin.gainly.core.data.remote.service.AvatarApiService
import com.jacqulin.gainly.core.domain.repository.AvatarRepository
import javax.inject.Inject
import kotlin.collections.emptyMap

class AvatarRepositoryImpl @Inject constructor(
    val api: AvatarApiService
): AvatarRepository {

    override suspend fun getAvatar(accessToken: String, username: String): String {
        val response = api.getAvatar("Bearer $accessToken", username)
        return response.url
    }

    override suspend fun getAvatars(usernames: List<String>): Map<String, String> {
        if (usernames.isEmpty()) return emptyMap()
        return try {
            api.getAvatars(AvatarsRequestDto(usernames))
        } catch (_: Exception) {
            emptyMap()
        }
    }
}