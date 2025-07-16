package com.jacqulin.gainly.core.domain.repository

import com.jacqulin.gainly.core.domain.model.AuthData

/**
 * TODO: На данный момент не обрабатываются никакие возможные ошибки
 */
interface AuthRepository {

    suspend fun signIn(email: String, password: String): AuthData
    suspend fun signUp(email: String, password: String): AuthData
}