package com.jacqulin.gainly.feature.auth.signup.otp

data class OtpState(
    val code: List<Int?> = (1..5).map { null },
    val focusedIndex: Int? = null,
    val isValid: Boolean? = null
)