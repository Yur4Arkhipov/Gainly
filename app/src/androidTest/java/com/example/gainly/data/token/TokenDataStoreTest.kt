package com.example.gainly.data.token

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class TokenDataStoreTest {

    private lateinit var context: Context
    private lateinit var dataStore: TokenDataStore

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext<Context>()
        dataStore = TokenDataStore(context)
    }

    @Test
    fun saveAndReadTokensTest() = runBlocking {
        val accessToken = "access_token"
        val refreshToken = "refresh_token"

        dataStore.saveTokens(accessToken, refreshToken)

        val savedAccessToken = dataStore.accessTokenFlow.first()
        val savedRefreshToken = dataStore.refreshTokenFlow.first()

        assertEquals(accessToken, savedAccessToken)
        assertEquals(refreshToken, savedRefreshToken)
    }

    @Test
    fun clearTokensTest() = runBlocking {
        dataStore.saveTokens("a", "b")

        dataStore.clearTokens()

        val accessToken = dataStore.accessTokenFlow.first()
        val refreshToken = dataStore.refreshTokenFlow.first()

        assertNull(accessToken)
        assertNull(refreshToken)
    }
}