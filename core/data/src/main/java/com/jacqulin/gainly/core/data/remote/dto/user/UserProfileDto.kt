package com.jacqulin.gainly.core.data.remote.dto.user

import com.google.gson.annotations.SerializedName
import com.jacqulin.gainly.core.model.UserProfile
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileDto(
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("tgUsername")
    val tgUsername: String?,
    @SerializedName("registrationDate")
    val registrationDate: String
)

fun UserProfileDto.toDomain(): UserProfile {
    return UserProfile(
        nickname = nickname,
        email = email,
        tgUsername = tgUsername,
        registrationDate = registrationDate
    )
}
