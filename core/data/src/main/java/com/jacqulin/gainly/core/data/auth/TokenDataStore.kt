package com.jacqulin.gainly.core.data.auth

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.jacqulin.gainly.core.domain.model.AuthData
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TokenDataStore @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_preferences")
    }

    suspend fun saveTokens(authData: AuthData) {
        try {
            context.dataStore.edit { preferences ->
                preferences[TokenKeys.ACCESS_TOKEN] = authData.accessToken
                preferences[TokenKeys.REFRESH_TOKEN] = authData.refreshToken
                preferences[TokenKeys.USER_ID] = authData.id
            }
            Log.d("TokenDataStore", "Tokens saved successfully")
        } catch (e: Exception) {
            Log.e("TokenDataStore", "Error saving tokens: ${e.message}", e)
        }
    }

    val accessTokenFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[TokenKeys.ACCESS_TOKEN]
        }

    val refreshTokenFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[TokenKeys.REFRESH_TOKEN]
        }

    val userIdFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[TokenKeys.USER_ID]
        }

    suspend fun clearTokens() {
        try {
            context.dataStore.edit { preferences ->
                preferences.clear()
            }
            Log.d("TokenDataStore", "Tokens cleared successfully")
        } catch (e: Exception) {
            Log.e("TokenDataStore", "Error clearing tokens: ${e.message}", e)
        }
    }
}
