package com.jacqulin.gainly.core.data.remote.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class GoogleSignInRequestDto(
    val googleIdToken: String
)