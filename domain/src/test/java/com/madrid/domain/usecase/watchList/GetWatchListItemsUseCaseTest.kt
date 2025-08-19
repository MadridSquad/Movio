package com.madrid.domain.usecase.watchList

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Series
import com.madrid.domain.repository.AuthenticationRepository
import com.madrid.domain.repository.ListRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@Suppress("UnusedFlow")
class GetWatchListItemsUseCaseTest {
    private val authenticationRepository: AuthenticationRepository = mockk(relaxed = true)
    private val listRepository: ListRepository = mockk(relaxed = true)
    private lateinit var useCase: GetWatchListItemsUseCase

    @Before
    fun setUp() {
        useCase = GetWatchListItemsUseCase(authenticationRepository, listRepository)
    }

    @Test
    fun `should return watch list items when repository succeeds`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("test_session_id")
        coEvery {
            listRepository.getListItemsById(
                123,
                "test_session_id"
            )
        } returns testWatchListItems

        val result = useCase.invoke(123)

        assertThat(result).isEqualTo(testWatchListItems)
        coVerify(exactly = 1) { authenticationRepository.getSessionId() }
        coVerify(exactly = 1) { listRepository.getListItemsById(123, "test_session_id") }
    }

    @Test
    fun `should return empty watch list when repository returns empty lists`() = runTest {
        val emptyWatchListItems = GetWatchListItemsUseCase.WatchListItems(
            movies = emptyList(),
            series = emptyList()
        )
        coEvery { authenticationRepository.getSessionId() } returns flowOf("test_session_id")
        coEvery {
            listRepository.getListItemsById(
                123,
                "test_session_id"
            )
        } returns emptyWatchListItems

        val result = useCase.invoke(123)

        assertThat(result).isEqualTo(emptyWatchListItems)
        assertThat(result.movies).isEmpty()
        assertThat(result.series).isEmpty()
        coVerify(exactly = 1) { authenticationRepository.getSessionId() }
        coVerify(exactly = 1) { listRepository.getListItemsById(123, "test_session_id") }
    }

    @Test
    fun `should use custom session id from authentication repository`() = runTest {
        val customSessionId = "custom_session_456"
        coEvery { authenticationRepository.getSessionId() } returns flowOf(customSessionId)
        coEvery { listRepository.getListItemsById(123, customSessionId) } returns testWatchListItems

        val result = useCase.invoke(123)

        assertThat(result).isEqualTo(testWatchListItems)
        coVerify(exactly = 1) { authenticationRepository.getSessionId() }
        coVerify(exactly = 1) { listRepository.getListItemsById(123, customSessionId) }
    }

    @Test
    fun `should handle different list ids`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("test_session_id")
        coEvery {
            listRepository.getListItemsById(
                999,
                "test_session_id"
            )
        } returns testWatchListItems

        val result = useCase.invoke(999)

        assertThat(result).isEqualTo(testWatchListItems)
        coVerify(exactly = 1) { authenticationRepository.getSessionId() }
        coVerify(exactly = 1) { listRepository.getListItemsById(999, "test_session_id") }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when authentication repository fails`() = runTest {
        coEvery { authenticationRepository.getSessionId() } throws RuntimeException("Authentication error")

        useCase.invoke(123)
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when list repository fails`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("test_session_id")
        coEvery { listRepository.getListItemsById(123, "test_session_id") } throws RuntimeException(
            "List error"
        )

        useCase.invoke(123)
    }

    @Test(expected = IllegalStateException::class)
    fun `should throw exception when session id is empty`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("")
        coEvery {
            listRepository.getListItemsById(
                123,
                ""
            )
        } throws IllegalStateException("Empty session ID")

        useCase.invoke(123)
    }

    @Test
    fun `should call repositories in correct order`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("test_session_id")
        coEvery {
            listRepository.getListItemsById(
                123,
                "test_session_id"
            )
        } returns testWatchListItems

        useCase.invoke(123)

        coVerify(exactly = 1) { authenticationRepository.getSessionId() }
        coVerify(exactly = 1) { listRepository.getListItemsById(123, "test_session_id") }
    }

    private companion object {
        val testMovies = listOf(
            Movie(
                id = 1,
                title = "Test Movie 1",
                imageUrl = "/test_poster1.jpg",
                rate = 7.5,
                releaseDate = "2023-01-01",
                movieDuration = "120 min",
                description = "Test overview",
                genre = emptyList()
            ),
            Movie(
                id = 2,
                title = "Test Movie 2",
                imageUrl = "/test_poster2.jpg",
                rate = 8.0,
                releaseDate = "2023-02-01",
                movieDuration = "135 min",
                description = "Test overview 2",
                genre = emptyList()
            )
        )

        val testSeries = listOf(
            Series(
                id = 1,
                title = "Test Series 1",
                imageUrl = "/test_series_poster1.jpg",
                rate = 8.5,
                airDate = "2023-01-01",
                seasons = emptyList(),
                description = "Test series overview",
                genre = emptyList()
            )
        )

        val testWatchListItems = GetWatchListItemsUseCase.WatchListItems(
            movies = testMovies,
            series = testSeries
        )
    }
}