package com.madrid.domain.usecase.series

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.entity.Series
import com.madrid.domain.repository.AuthenticationRepository
import com.madrid.domain.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetFavoriteSeriesUseCaseTest {
    private val authenticationRepository: AuthenticationRepository = mockk(relaxed = true)
    private val seriesRepository: SeriesRepository = mockk(relaxed = true)
    private lateinit var useCase: GetFavoriteSeriesUseCase

    @Before
    fun setUp() {
        useCase = GetFavoriteSeriesUseCase(authenticationRepository, seriesRepository)
    }

    @Test
    fun `should get sessionId and return favorite series`() = runTest {
        val sessionId = "test_session_123"
        val expectedSeries = listOf(
            Series(
                id = 1,
                title = "Favorite Series 1",
                imageUrl = "https://example.com/image1.jpg",
                rate = 8.5,
                airDate = "2023-01-01",
                seasons = emptyList(),
                description = "Test description 1",
                genre = emptyList()
            ),
            Series(
                id = 2,
                title = "Favorite Series 2",
                imageUrl = "https://example.com/image2.jpg",
                rate = 9.0,
                airDate = "2023-02-01",
                seasons = emptyList(),
                description = "Test description 2",
                genre = emptyList()
            )
        )
        coEvery { authenticationRepository.getSessionId() } returns flowOf(sessionId)
        coEvery { seriesRepository.getFavoriteSeries(sessionId) } returns expectedSeries

        val result = useCase.invoke()

        assertThat(result).isEqualTo(expectedSeries)
        coVerify(exactly = 1) { seriesRepository.getFavoriteSeries(sessionId) }
    }

    @Test
    fun `should return empty list when no favorite series found`() = runTest {
        val sessionId = "test_session_456"
        val expectedSeries = emptyList<Series>()
        coEvery { authenticationRepository.getSessionId() } returns flowOf(sessionId)
        coEvery { seriesRepository.getFavoriteSeries(sessionId) } returns expectedSeries

        val result = useCase.invoke()

        assertThat(result).isEmpty()
        coVerify(exactly = 1) { seriesRepository.getFavoriteSeries(sessionId) }
    }

    @Test
    fun `should handle different sessionId values`() = runTest {
        val sessionId = "different_session_789"
        val expectedSeries = listOf(
            Series(
                id = 5,
                title = "Only Favorite",
                imageUrl = "https://example.com/image5.jpg",
                rate = 8.0,
                airDate = "2023-05-01",
                seasons = emptyList(),
                description = "Test description 5",
                genre = emptyList()
            )
        )
        coEvery { authenticationRepository.getSessionId() } returns flowOf(sessionId)
        coEvery { seriesRepository.getFavoriteSeries(sessionId) } returns expectedSeries

        val result = useCase.invoke()

        assertThat(result).isEqualTo(expectedSeries)
        coVerify(exactly = 1) { seriesRepository.getFavoriteSeries(sessionId) }
    }
    
    @Test(expected = RuntimeException::class)
    fun `should throw exception when series repository fails`() = runTest {
        val sessionId = "error_session"
        coEvery { authenticationRepository.getSessionId() } returns flowOf(sessionId)
        coEvery { seriesRepository.getFavoriteSeries(sessionId) } throws RuntimeException("Database error")

        useCase.invoke()
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when authentication repository fails`() = runTest {
        coEvery { authenticationRepository.getSessionId() } throws RuntimeException("Auth error")

        useCase.invoke()
    }

    @Test
    fun `should return multiple favorite series`() = runTest {
        val sessionId = "multi_session"
        val expectedSeries = (1..10).map {
            Series(
                id = it,
                title = "Favorite $it",
                imageUrl = "https://example.com/image$it.jpg",
                rate = 7.0 + it * 0.1,
                airDate = "2023-0${it % 12 + 1}-01",
                seasons = emptyList(),
                description = "Test description $it",
                genre = emptyList()
            )
        }
        coEvery { authenticationRepository.getSessionId() } returns flowOf(sessionId)
        coEvery { seriesRepository.getFavoriteSeries(sessionId) } returns expectedSeries

        val result = useCase.invoke()

        assertThat(result).hasSize(10)
        assertThat(result).isEqualTo(expectedSeries)
        coVerify(exactly = 1) { seriesRepository.getFavoriteSeries(sessionId) }
    }
}