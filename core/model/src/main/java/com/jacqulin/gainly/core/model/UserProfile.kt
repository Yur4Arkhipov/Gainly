package com.jacqulin.gainly.core.model

data class UserProfile(
    val nickname: String,
    val email: String,
    val tgUsername: String?,
    val registrationDate: String
)
