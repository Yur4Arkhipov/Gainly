package com.jacqulin.gainly.core.data.remote.dto.avatar

import kotlinx.serialization.Serializable

@Serializable
data class AvatarsResponse(
    val avatars: Map<String, String>
)
