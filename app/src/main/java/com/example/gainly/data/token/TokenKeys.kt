package com.example.gainly.data.token

import androidx.datastore.preferences.core.stringPreferencesKey

object TokenKeys {
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val ACCESS_TOKEN_IV = stringPreferencesKey("access_token_iv")
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    val REFRESH_TOKEN_IV = stringPreferencesKey("refresh_token_iv")
}
