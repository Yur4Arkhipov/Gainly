package com.jacqulin.gainly.core.data.remote.dto.avatar

import kotlinx.serialization.Serializable

@Serializable
data class AvatarsRequestDto(
    val usernames: List<String>
)
