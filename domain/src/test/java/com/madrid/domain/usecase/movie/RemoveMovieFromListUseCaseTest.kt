@file:Suppress("UnusedFlow")

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

class RemoveMovieFromListUseCaseTest {
    private val movieRepository: MovieRepository = mockk(relaxed = true)
    private val authenticationRepository: AuthenticationRepository = mockk(relaxed = true)
    private lateinit var useCase: RemoveMovieFromListUseCase

    @Before
    fun setUp() {
        useCase = RemoveMovieFromListUseCase(movieRepository, authenticationRepository)
    }

    @Test
    fun `invoke SHOULD remove movie from list with correct parameters`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("test_session_id")

        useCase.invoke(mediaId = 123, listId = 456)

        coVerify(exactly = 1) { authenticationRepository.getSessionId() }
        coVerify(exactly = 1) {
            movieRepository.removeMovieFromList(
                mediaId = 123,
                listId = 456,
                sessionId = "test_session_id"
            )
        }
    }

    @Test
    fun `invoke SHOULD use custom session id from authentication repository`() = runTest {
        val customSessionId = "custom_session_123"
        coEvery { authenticationRepository.getSessionId() } returns flowOf(customSessionId)

        useCase.invoke(mediaId = 789, listId = 101)

        coVerify(exactly = 1) { authenticationRepository.getSessionId() }
        coVerify(exactly = 1) {
            movieRepository.removeMovieFromList(
                mediaId = 789,
                listId = 101,
                sessionId = customSessionId
            )
        }
    }

    @Test
    fun `invoke SHOULD handle different media and list ids`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("test_session_id")

        useCase.invoke(mediaId = 1, listId = 1)

        coVerify(exactly = 1) { authenticationRepository.getSessionId() }
        coVerify(exactly = 1) {
            movieRepository.removeMovieFromList(
                mediaId = 1,
                listId = 1,
                sessionId = "test_session_id"
            )
        }
    }

    @Test(expected = RuntimeException::class)
    fun `invoke SHOULD throw exception when authentication repository fails`() = runTest {
        coEvery { authenticationRepository.getSessionId() } throws RuntimeException("Authentication error")

        useCase.invoke(mediaId = 123, listId = 456)
    }

    @Test(expected = RuntimeException::class)
    fun `invoke SHOULD throw exception when movie repository fails`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("test_session_id")
        coEvery {
            movieRepository.removeMovieFromList(
                mediaId = 123,
                listId = 456,
                sessionId = "test_session_id"
            )
        } throws RuntimeException("Network error")

        useCase.invoke(mediaId = 123, listId = 456)
    }

    @Test(expected = IllegalStateException::class)
    fun `invoke SHOULD throw exception when session id is empty`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("")
        coEvery {
            movieRepository.removeMovieFromList(
                mediaId = 123,
                listId = 456,
                sessionId = ""
            )
        } throws IllegalStateException("Empty session ID")

        useCase.invoke(mediaId = 123, listId = 456)
    }

    @Test
    fun `invoke SHOULD call repositories in correct order`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("test_session_id")

        useCase.invoke(mediaId = 123, listId = 456)

        coVerify(exactly = 1) { authenticationRepository.getSessionId() }
        coVerify(exactly = 1) {
            movieRepository.removeMovieFromList(
                mediaId = 123,
                listId = 456,
                sessionId = "test_session_id"
            )
        }
    }
}