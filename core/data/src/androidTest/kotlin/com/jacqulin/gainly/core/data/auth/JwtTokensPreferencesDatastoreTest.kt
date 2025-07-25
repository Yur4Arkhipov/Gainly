package com.jacqulin.gainly.core.data.auth

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.jacqulin.gainly.core.domain.model.AuthData
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class JwtTokensPreferencesDatastoreTest {

    private lateinit var dataStore: TokenDataStore

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        dataStore = TokenDataStore(context)
    }

    @Test
    fun testSaveTokens() = runTest {
        val authData = AuthData(
            accessToken = "accesstoken1",
            refreshToken = "refreshtoken1",
            id = "1"
        )
        dataStore.saveTokens(authData)

        val accessToken = dataStore.accessTokenFlow.first()
        val refreshToken = dataStore.refreshTokenFlow.first()
        val userId = dataStore.userIdFlow.first()

        assertEquals("accesstoken1", accessToken)
        assertEquals("refreshtoken1", refreshToken)
        assertEquals("1", userId)
    }

    @Test
    fun testClearTokens() = runTest {
        val authData = AuthData("a", "b", "c")
        dataStore.saveTokens(authData)
        dataStore.clearTokens()

        val access = dataStore.accessTokenFlow.first()
        val refresh = dataStore.refreshTokenFlow.first()
        val userId = dataStore.userIdFlow.first()

        assertNull(access)
        assertNull(refresh)
        assertNull(userId)
    }

    @Test
    fun accessTokenFlow_EmitsOnlyOnce_WhenValueIsUnchanged() = runTest {
        val authData = AuthData("tokenA", "tokenB", "tokenC")
        dataStore.saveTokens(authData)

        val emissions = dataStore.accessTokenFlow.take(1).toList()

        assertEquals(1, emissions.size)
        assertEquals("tokenA", emissions.first())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun accessTokenFlow_EmitsNewValue_WhenTokenIsUpdated() = runTest {
        val initial = AuthData("tokenA", "refreshA", "userA")
        val updated = AuthData("tokenB", "refreshB", "userB")

        dataStore.saveTokens(initial)

        val collected = mutableListOf<String?>()
        val job = launch {
            dataStore.accessTokenFlow
                .onEach { collected.add(it) }
                .take(2)
                .collect()
        }

        advanceUntilIdle()

        dataStore.saveTokens(updated)

        job.join()

        assertEquals(listOf("tokenA", "tokenB"), collected)
    }
}