package com.madrid.domain.usecase.authentication

import com.madrid.domain.repository.AuthenticationRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class LogoutUseCaseTest {
    private val authenticationRepository: AuthenticationRepository = mockk(relaxed = true)
    private lateinit var useCase: LogoutUseCase

    @Before
    fun setUp() {
        useCase = LogoutUseCase(authenticationRepository)
    }

    @Test
    fun `execute should call repository logout`() = runTest {
        useCase.execute()

        coVerify(exactly = 1) { authenticationRepository.logout() }
    }

    @Test(expected = RuntimeException::class)
    fun `execute should throw exception when repository fails`() = runTest {
        coEvery { authenticationRepository.logout() } throws RuntimeException("Logout error")

        useCase.execute()
    }

    @Test
    fun `execute should complete successfully when repository succeeds`() = runTest {
        coEvery { authenticationRepository.logout() } returns Unit

        useCase.execute()

        coVerify(exactly = 1) { authenticationRepository.logout() }
    }
}