package com.example.gainly.domain.usecase.auth

import com.example.gainly.data.remote.dto.LoginResponseDto
import com.example.gainly.data.remote.dto.RegisterResponseDto
import com.example.gainly.domain.repository.AuthRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class AuthUseCasesTest {

    private val fakeRepository = Mockito.mock<AuthRepository>()
    private val registerUseCase = RegisterUseCase(fakeRepository)
    private val loginUseCase = LoginUseCase(fakeRepository)

    @Test
    fun `RegisterUseCase's invoke should return RegisterResponseDto from repository`() {
        runBlocking {
            val email = "test@example.com"
            val password = "123123"
            val expectedResponse = RegisterResponseDto(
                accessToken = "abc",
                refreshToken = "zxc",
                userId = "1"
            )

            whenever(fakeRepository.register(email, password)).thenReturn(expectedResponse)

            val actualResponse = registerUseCase(email, password)

            Assert.assertEquals(expectedResponse, actualResponse)
            verify(fakeRepository).register(email, password)
        }
    }

    @Test
    fun `LoginUseCase's invoke should return LoginResponseDto from repository`() {
        runBlocking {
            val email = "test@example.com"
            val password = "123123"
            val expectedResponse = LoginResponseDto(
                accessToken = "abc",
                refreshToken = "zxc",
                userId = "1"
            )

            whenever(fakeRepository.login(email, password)).thenReturn(expectedResponse)

            val actualResponse = loginUseCase(email, password)

            Assert.assertEquals(expectedResponse, actualResponse)
            verify(fakeRepository).login(email, password)
        }
    }
}