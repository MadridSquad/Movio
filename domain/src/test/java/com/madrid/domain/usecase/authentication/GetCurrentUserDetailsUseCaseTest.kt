@file:Suppress("UnusedFlow")

package com.madrid.domain.usecase.authentication

import com.google.common.truth.Truth
import com.madrid.domain.entity.User
import com.madrid.domain.repository.AuthenticationRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetCurrentUserDetailsUseCaseTest {
    private val authenticationRepository: AuthenticationRepository = mockk(relaxed = true)
    private lateinit var useCase: GetCurrentUserDetailsUseCase

    @Before
    fun setUp() {
        useCase = GetCurrentUserDetailsUseCase(authenticationRepository)
    }

    @Test
    fun `invoke SHOULD return user when repository returns user`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("test_session_id")
        coEvery { authenticationRepository.getCurrentUser("test_session_id") } returns testUser

        val result = useCase.invoke()

        Truth.assertThat(result).isEqualTo(testUser)
        coVerify(exactly = 1) { authenticationRepository.getSessionId() }
        coVerify(exactly = 1) { authenticationRepository.getCurrentUser("test_session_id") }
    }

    @Test
    fun `invoke SHOULD return null when repository returns null`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("test_session_id")
        coEvery { authenticationRepository.getCurrentUser("test_session_id") } returns null

        val result = useCase.invoke()

        Truth.assertThat(result).isNull()
        coVerify(exactly = 1) { authenticationRepository.getSessionId() }
        coVerify(exactly = 1) { authenticationRepository.getCurrentUser("test_session_id") }
    }

    @Test
    fun `invoke SHOULD use custom session id from authentication repository`() = runTest {
        val customSessionId = "custom_session_123"
        coEvery { authenticationRepository.getSessionId() } returns flowOf(customSessionId)
        coEvery { authenticationRepository.getCurrentUser(customSessionId) } returns testUser

        val result = useCase.invoke()

        Truth.assertThat(result).isEqualTo(testUser)
        coVerify(exactly = 1) { authenticationRepository.getSessionId() }
        coVerify(exactly = 1) { authenticationRepository.getCurrentUser(customSessionId) }
    }

    @Test(expected = RuntimeException::class)
    fun `invoke SHOULD throw exception when getSessionId fails`() = runTest {
        coEvery { authenticationRepository.getSessionId() } throws RuntimeException("Session error")

        useCase.invoke()
    }

    @Test(expected = RuntimeException::class)
    fun `invoke SHOULD throw exception when getCurrentUser fails`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("test_session_id")
        coEvery { authenticationRepository.getCurrentUser("test_session_id") } throws RuntimeException(
            "User error"
        )

        useCase.invoke()
    }

    @Test(expected = IllegalStateException::class)
    fun `invoke SHOULD throw exception when session id is empty`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("")
        coEvery { authenticationRepository.getCurrentUser("") } throws IllegalStateException("Empty session ID")

        useCase.invoke()
    }

    @Test
    fun `invoke SHOULD call repository methods in correct order`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("test_session_id")
        coEvery { authenticationRepository.getCurrentUser("test_session_id") } returns testUser

        useCase.invoke()

        coVerify(exactly = 1) { authenticationRepository.getSessionId() }
        coVerify(exactly = 1) { authenticationRepository.getCurrentUser("test_session_id") }
    }

    private companion object {
        val testUser = User(
            id = "1",
            username = "Test User",
            profilePicUrl = "/test_avatar.jpg"
        )
    }
}