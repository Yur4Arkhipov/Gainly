package com.example.gainly.data.token

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.gainly.data.token.TokenKeys.ACCESS_TOKEN
import com.example.gainly.data.token.TokenKeys.ACCESS_TOKEN_IV
import com.example.gainly.data.token.TokenKeys.REFRESH_TOKEN
import com.example.gainly.data.token.TokenKeys.REFRESH_TOKEN_IV
import com.example.gainly.util.Security
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class TokenDataStore @Inject constructor(
    @ApplicationContext private val context: Context,
    private val security: Security
) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")
    }

    @OptIn(ExperimentalEncodingApi::class)
    suspend fun saveTokens(accessToken: String, refreshToken: String) {
        try {
            val (accessIv, accessEncrypted) = security.encryptData("access_token_key", accessToken)
            val (refreshIv, refreshEncrypted) = security.encryptData("refresh_token_key", refreshToken)

            context.dataStore.edit { preferences ->
                preferences[ACCESS_TOKEN] = Base64.encode(accessEncrypted)
                preferences[ACCESS_TOKEN_IV] = Base64.encode(accessIv)
                preferences[REFRESH_TOKEN] = Base64.encode(refreshEncrypted)
                preferences[REFRESH_TOKEN_IV] = Base64.encode(refreshIv)
            }
            Log.d("TokenDataStore", "Tokens saved successfully")
        } catch (e: Exception) {
            Log.e("TokenDataStore", "Error saving tokens: ${e.message}", e)
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    val accessTokenFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            val encrypted = preferences[ACCESS_TOKEN]
            val iv = preferences[ACCESS_TOKEN_IV]
            if (encrypted != null && iv != null) {
                runCatching {
                    val decrypted = security.decryptData(
                        keyAlias = "access_token_key",
                        iv = Base64.decode(iv),
                        encryptedData = Base64.decode(encrypted)
                    )
                    Log.d("TokenDataStore", "Access token decrypted successfully")
                    decrypted
                }.getOrElse {
                    Log.e("TokenDataStore", "Error decrypting access token: ${it.message}", it)
                    null
                }
            } else  {
                Log.w("TokenDataStore", "Access token or IV is null")
                null
            }
        }
        .shareIn(scope = CoroutineScope(Dispatchers.IO), started = SharingStarted.WhileSubscribed(), replay = 1)

    @OptIn(ExperimentalEncodingApi::class)
    val refreshTokenFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            val encrypted = preferences[REFRESH_TOKEN]
            val iv = preferences[REFRESH_TOKEN_IV]
            if (encrypted != null && iv != null) {
                runCatching {
                    val decrypted = security.decryptData(
                        keyAlias = "refresh_token_key",
                        iv = Base64.decode(iv),
                        encryptedData = Base64.decode(encrypted)
                    )
                    Log.d("TokenDataStore", "Refresh token decrypted successfully")
                    decrypted
                }.getOrElse {
                    Log.e("TokenDataStore", "Error decrypting refresh token: ${it.message}", it)
                    null
                }
            } else  {
                Log.w("TokenDataStore", "Refresh token or IV is null")
                null
            }
        }

    suspend fun clearTokens() {
        try {
            context.dataStore.edit { prefs ->
                prefs.clear()
            }
            Log.d("TokenDataStore", "Tokens cleared successfully")
        } catch (e: Exception) {
            Log.e("TokenDataStore", "Error clearing tokens: ${e.message}", e)
        }
    }
}
