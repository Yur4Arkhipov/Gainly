package com.jacqulin.gainly.core.data.remote.dto.auth

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TelegramAuthRequestDto(
    @SerialName("id")
    @SerializedName("id")
    val id: Long,

    @SerializedName("first_name")
    @SerialName("first_name")
    val firstName: String,

    @SerializedName("last_name")
    @SerialName("last_name")
    val lastName: String? = null,

    @SerializedName("username")
    @SerialName("username")
    val username: String? = null,

    @SerializedName("photo_url")
    @SerialName("photo_url")
    val photoUrl: String? = null,

    @SerializedName("auth_date")
    @SerialName("auth_date")
    val authDate: Long,

    @SerializedName("hash")
    @SerialName("hash")
    val hash: String
)