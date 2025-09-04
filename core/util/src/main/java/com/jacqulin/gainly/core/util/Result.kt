package com.jacqulin.gainly.core.util

import com.jacqulin.gainly.core.util.errors.Error

typealias RootError = Error

sealed interface Result<out D, out E: RootError> {
    data class Success<out D>(val data: D): Result<D, Nothing>
    data class Error<out E: RootError>(val error: E): Result<Nothing, E>
}