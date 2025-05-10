package com.example.gainly.data.token

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TokenDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")
    }

    suspend fun saveTokens(accessToken: String, refreshToken: String) {
        context.dataStore.edit { prefs ->
            prefs[TokenKeys.ACCESS_TOKEN] = accessToken
            prefs[TokenKeys.REFRESH_TOKEN] = refreshToken
        }
    }

    val accessTokenFlow: Flow<String?> = context.dataStore.data
        .map { it[TokenKeys.ACCESS_TOKEN] }

    val refreshTokenFlow: Flow<String?> = context.dataStore.data
        .map { it[TokenKeys.REFRESH_TOKEN] }

    suspend fun clearTokens() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
