package com.jacqulin.gainly.core.domain.repository

interface AvatarRepository {
    suspend fun getAvatar(accessToken: String, username: String): String
    suspend fun getAvatars(usernames: List<String>): Map<String, String>
}