package com.jacqulin.gainly.core.util

sealed interface Response<out T> {
    data class Success<out T>(val data: T): Response<T>
    data class Error(val message: String, val cause: Throwable? = null): Response<Nothing>
    object Loading: Response<Nothing>
}