package com.madrid.domain.usecase.series

import com.madrid.domain.repository.AuthenticationRepository
import com.madrid.domain.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SetSeriesFavoriteStatusUseCaseTest {
    private val seriesRepository: SeriesRepository = mockk(relaxed = true)
    private val authenticationRepository: AuthenticationRepository = mockk(relaxed = true)
    private lateinit var useCase: SetSeriesFavoriteStatusUseCase

    @Before
    fun setUp() {
        useCase = SetSeriesFavoriteStatusUseCase(seriesRepository, authenticationRepository)
    }

    @Test
    fun `should get sessionId and set series as favorite`() = runTest {
        val seriesId = 123
        val isFavorite = true
        val sessionId = "test_session_123"
        coEvery { authenticationRepository.getSessionId() } returns flowOf(sessionId)

        useCase.invoke(seriesId, isFavorite)

        coVerify(exactly = 1) {
            seriesRepository.setSeriesFavoriteStatus(
                seriesId = seriesId,
                sessionId = sessionId,
                isFavorite = isFavorite
            )
        }
    }

    @Test
    fun `should get sessionId and set series as not favorite`() = runTest {
        val seriesId = 456
        val isFavorite = false
        val sessionId = "test_session_456"
        coEvery { authenticationRepository.getSessionId() } returns flowOf(sessionId)

        useCase.invoke(seriesId, isFavorite)

        coVerify(exactly = 1) {
            seriesRepository.setSeriesFavoriteStatus(
                seriesId = seriesId,
                sessionId = sessionId,
                isFavorite = isFavorite
            )
        }
    }

    @Test
    fun `should handle different seriesId values`() = runTest {
        val seriesId = 0
        val isFavorite = true
        val sessionId = "zero_session"
        coEvery { authenticationRepository.getSessionId() } returns flowOf(sessionId)

        useCase.invoke(seriesId, isFavorite)

        coVerify(exactly = 1) {
            seriesRepository.setSeriesFavoriteStatus(
                seriesId = seriesId,
                sessionId = sessionId,
                isFavorite = isFavorite
            )
        }
    }

    @Test
    fun `should handle large seriesId values`() = runTest {
        val seriesId = 999999
        val isFavorite = false
        val sessionId = "large_session"
        coEvery { authenticationRepository.getSessionId() } returns flowOf(sessionId)

        useCase.invoke(seriesId, isFavorite)

        coVerify(exactly = 1) {
            seriesRepository.setSeriesFavoriteStatus(
                seriesId = seriesId,
                sessionId = sessionId,
                isFavorite = isFavorite
            )
        }
    }

    @Test
    fun `should handle different sessionId values`() = runTest {
        val seriesId = 123
        val isFavorite = true
        val sessionId = "different_session_789"
        coEvery { authenticationRepository.getSessionId() } returns flowOf(sessionId)

        useCase.invoke(seriesId, isFavorite)

        coVerify(exactly = 1) {
            seriesRepository.setSeriesFavoriteStatus(
                seriesId = seriesId,
                sessionId = sessionId,
                isFavorite = isFavorite
            )
        }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when authenticationRepository fails`() = runTest {
        val seriesId = 123
        val isFavorite = true
        coEvery { authenticationRepository.getSessionId() } throws RuntimeException("Auth error")

        useCase.invoke(seriesId, isFavorite)
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when seriesRepository fails`() = runTest {
        val seriesId = 123
        val isFavorite = true
        val sessionId = "failing_session"
        coEvery { authenticationRepository.getSessionId() } returns flowOf(sessionId)
        coEvery {
            seriesRepository.setSeriesFavoriteStatus(
                seriesId = seriesId,
                sessionId = sessionId,
                isFavorite = isFavorite
            )
        } throws RuntimeException("Network error")

        useCase.invoke(seriesId, isFavorite)
    }

    @Test
    fun `should call repository with correct parameters for adding favorite`() = runTest {
        val seriesId = 789
        val isFavorite = true
        val sessionId = "add_favorite_session"
        coEvery { authenticationRepository.getSessionId() } returns flowOf(sessionId)

        useCase.invoke(seriesId, isFavorite)

        coVerify(exactly = 1) {
            seriesRepository.setSeriesFavoriteStatus(
                seriesId = 789,
                sessionId = "add_favorite_session",
                isFavorite = true
            )
        }
    }

    @Test
    fun `should call repository with correct parameters for removing favorite`() = runTest {
        val seriesId = 321
        val isFavorite = false
        val sessionId = "remove_favorite_session"
        coEvery { authenticationRepository.getSessionId() } returns flowOf(sessionId)

        useCase.invoke(seriesId, isFavorite)

        coVerify(exactly = 1) {
            seriesRepository.setSeriesFavoriteStatus(
                seriesId = 321,
                sessionId = "remove_favorite_session",
                isFavorite = false
            )
        }
    }

    @Test
    fun `should handle multiple calls with different parameters`() = runTest {
        val sessionId = "multi_call_session"
        coEvery { authenticationRepository.getSessionId() } returns flowOf(sessionId)

        useCase.invoke(111, true)
        useCase.invoke(222, false)
        useCase.invoke(333, true)

        coVerify(exactly = 1) {
            seriesRepository.setSeriesFavoriteStatus(
                seriesId = 111,
                sessionId = sessionId,
                isFavorite = true
            )
        }
        coVerify(exactly = 1) {
            seriesRepository.setSeriesFavoriteStatus(
                seriesId = 222,
                sessionId = sessionId,
                isFavorite = false
            )
        }
        coVerify(exactly = 1) {
            seriesRepository.setSeriesFavoriteStatus(
                seriesId = 333,
                sessionId = sessionId,
                isFavorite = true
            )
        }
    }
}