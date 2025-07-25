package com.jacqulin.gainly.core.data.usecase.auth

import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.model.AuthData
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class SaveTokensUseCaseImplTest {

    private val tokenStorage = mock<TokenStorage>()
    private val useCase = SaveTokensUseCaseImpl(tokenStorage)

    @Test
    fun `invoke saves token to storage`() = runTest {
        val authData = AuthData(
            accessToken = "access",
            refreshToken = "refresh",
            id = "id"
        )

        useCase.invoke(authData)

        // this check call tokenStorage's saveToken method with authData argument
        verify(tokenStorage).saveToken(authData)
    }
}