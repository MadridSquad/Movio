package com.madrid.domain.usecase.movie

import com.madrid.domain.repository.AuthenticationRepository
import com.madrid.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@Suppress("UnusedFlow")
class SetMovieFavoriteStatusUseCaseTest {
    private val movieRepository: MovieRepository = mockk(relaxed = true)
    private val authenticationRepository: AuthenticationRepository = mockk(relaxed = true)
    private lateinit var useCase: SetMovieFavoriteStatusUseCase

    @Before
    fun setUp() {
        useCase = SetMovieFavoriteStatusUseCase(movieRepository, authenticationRepository)
    }

    @Test
    fun `should set movie as favorite with correct parameters`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("test_session_id")

        useCase.invoke(movieId = 123, isFavorite = true)

        coVerify(exactly = 1) { authenticationRepository.getSessionId() }
        coVerify(exactly = 1) {
            movieRepository.setMovieFavoriteStatus(
                movieId = 123,
                sessionId = "test_session_id",
                isFavorite = true
            )
        }
    }

    @Test
    fun `should remove movie from favorites with correct parameters`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("test_session_id")

        useCase.invoke(movieId = 123, isFavorite = false)

        coVerify(exactly = 1) { authenticationRepository.getSessionId() }
        coVerify(exactly = 1) {
            movieRepository.setMovieFavoriteStatus(
                movieId = 123,
                sessionId = "test_session_id",
                isFavorite = false
            )
        }
    }

    @Test
    fun `should use custom session id from authentication repository`() = runTest {
        val customSessionId = "custom_session_123"
        coEvery { authenticationRepository.getSessionId() } returns flowOf(customSessionId)

        useCase.invoke(movieId = 456, isFavorite = true)

        coVerify(exactly = 1) { authenticationRepository.getSessionId() }
        coVerify(exactly = 1) {
            movieRepository.setMovieFavoriteStatus(
                movieId = 456,
                sessionId = customSessionId,
                isFavorite = true
            )
        }
    }

    @Test
    fun `should handle different movie ids`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("test_session_id")

        useCase.invoke(movieId = 999, isFavorite = false)

        coVerify(exactly = 1) { authenticationRepository.getSessionId() }
        coVerify(exactly = 1) {
            movieRepository.setMovieFavoriteStatus(
                movieId = 999,
                sessionId = "test_session_id",
                isFavorite = false
            )
        }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when authentication repository fails`() = runTest {
        coEvery { authenticationRepository.getSessionId() } throws RuntimeException("Authentication error")

        useCase.invoke(movieId = 123, isFavorite = true)
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when movie repository fails`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("test_session_id")
        coEvery {
            movieRepository.setMovieFavoriteStatus(
                movieId = 123,
                sessionId = "test_session_id",
                isFavorite = true
            )
        } throws RuntimeException("Network error")

        useCase.invoke(movieId = 123, isFavorite = true)
    }

    @Test(expected = IllegalStateException::class)
    fun `should throw exception when session id is empty`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("")
        coEvery {
            movieRepository.setMovieFavoriteStatus(
                movieId = 123,
                sessionId = "",
                isFavorite = true
            )
        } throws IllegalStateException("Empty session ID")

        useCase.invoke(movieId = 123, isFavorite = true)
    }

    @Test
    fun `should call repositories in correct order`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("test_session_id")

        useCase.invoke(movieId = 123, isFavorite = true)

        coVerify(exactly = 1) { authenticationRepository.getSessionId() }
        coVerify(exactly = 1) {
            movieRepository.setMovieFavoriteStatus(
                movieId = 123,
                sessionId = "test_session_id",
                isFavorite = true
            )
        }
    }
}