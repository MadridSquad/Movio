package com.madrid.domain.usecase.series

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.entity.Series
import com.madrid.domain.repository.AuthenticationRepository
import com.madrid.domain.repository.SeriesRepository
import com.madrid.domain.usecase.series.GetUserRatedSeriesUseCase.RatedSeries
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetUserRatedSeriesUseCaseTest {
    private val seriesRepository: SeriesRepository = mockk(relaxed = true)
    private val authenticationRepository: AuthenticationRepository = mockk(relaxed = true)
    private lateinit var useCase: GetUserRatedSeriesUseCase

    @Before
    fun setUp() {
        useCase = GetUserRatedSeriesUseCase(seriesRepository, authenticationRepository)
    }

    @Test
    fun `should get sessionId and return user rated series`() = runTest {
        val sessionId = "test_session_123"
        val expectedRatedSeries = listOf(
            RatedSeries(
                rate = 8.5, series = Series(
                    id = 1,
                    title = "Series 1",
                    imageUrl = "https://example.com/image1.jpg",
                    rate = 8.5,
                    airDate = "2023-01-01",
                    seasons = emptyList(),
                    description = "Test series 1",
                    genre = emptyList()
                )
            ),
            RatedSeries(
                rate = 7.0, series = Series(
                    id = 2,
                    title = "Series 2",
                    imageUrl = "https://example.com/image2.jpg",
                    rate = 7.0,
                    airDate = "2023-02-01",
                    seasons = emptyList(),
                    description = "Test series 2",
                    genre = emptyList()
                )
            )
        )
        coEvery { authenticationRepository.getSessionId() } returns flowOf(sessionId)
        coEvery { seriesRepository.getUserSeriesRate(sessionId) } returns expectedRatedSeries

        val result = useCase.invoke()

        assertThat(result).isEqualTo(expectedRatedSeries)
        coVerify(exactly = 1) { seriesRepository.getUserSeriesRate(sessionId) }
    }

    @Test
    fun `should return empty list when no user rated series found`() = runTest {
        val sessionId = "empty_session"
        val expectedRatedSeries = emptyList<RatedSeries>()
        coEvery { authenticationRepository.getSessionId() } returns flowOf(sessionId)
        coEvery { seriesRepository.getUserSeriesRate(sessionId) } returns expectedRatedSeries

        val result = useCase.invoke()

        assertThat(result).isEmpty()
        coVerify(exactly = 1) { seriesRepository.getUserSeriesRate(sessionId) }
    }

    @Test
    fun `should return single rated series`() = runTest {
        val sessionId = "single_session"
        val expectedRatedSeries = listOf(
            RatedSeries(
                rate = 9.0, series = Series(
                    id = 1,
                    title = "Single Series",
                    imageUrl = "https://example.com/single.jpg",
                    rate = 9.0,
                    airDate = "2023-03-01",
                    seasons = emptyList(),
                    description = "Single test series",
                    genre = emptyList()
                )
            )
        )
        coEvery { authenticationRepository.getSessionId() } returns flowOf(sessionId)
        coEvery { seriesRepository.getUserSeriesRate(sessionId) } returns expectedRatedSeries

        val result = useCase.invoke()

        assertThat(result).hasSize(1)
        assertThat(result.first().rate).isEqualTo(9.0)
        assertThat(result.first().series.title).isEqualTo("Single Series")
        assertThat(result).isEqualTo(expectedRatedSeries)
        coVerify(exactly = 1) { seriesRepository.getUserSeriesRate(sessionId) }
    }

    @Test
    fun `should return multiple rated series`() = runTest {
        val sessionId = "multiple_session"
        val expectedRatedSeries = (1..10).map {
            RatedSeries(
                rate = it.toDouble(), series = Series(
                    id = it,
                    title = "Series $it",
                    imageUrl = "https://example.com/image$it.jpg",
                    rate = it.toDouble(),
                    airDate = "2023-0$it-01",
                    seasons = emptyList(),
                    description = "Test series $it",
                    genre = emptyList()
                )
            )
        }
        coEvery { authenticationRepository.getSessionId() } returns flowOf(sessionId)
        coEvery { seriesRepository.getUserSeriesRate(sessionId) } returns expectedRatedSeries

        val result = useCase.invoke()

        assertThat(result).hasSize(10)
        assertThat(result).containsExactlyElementsIn(expectedRatedSeries)
        coVerify(exactly = 1) { seriesRepository.getUserSeriesRate(sessionId) }
    }

    @Test
    fun `should handle different sessionId values`() = runTest {
        val sessionId = "different_session_456"
        val expectedRatedSeries = listOf(
            RatedSeries(
                rate = 6.5, series = Series(
                    id = 1,
                    title = "Different Session Series",
                    imageUrl = "https://example.com/different.jpg",
                    rate = 6.5,
                    airDate = "2023-04-01",
                    seasons = emptyList(),
                    description = "Different session series",
                    genre = emptyList()
                )
            )
        )
        coEvery { authenticationRepository.getSessionId() } returns flowOf(sessionId)
        coEvery { seriesRepository.getUserSeriesRate(sessionId) } returns expectedRatedSeries

        val result = useCase.invoke()

        assertThat(result).isEqualTo(expectedRatedSeries)
        coVerify(exactly = 1) { seriesRepository.getUserSeriesRate(sessionId) }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when authenticationRepository fails`() = runTest {
        coEvery { authenticationRepository.getSessionId() } throws RuntimeException("Auth error")

        useCase.invoke()
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when seriesRepository fails`() = runTest {
        val sessionId = "failing_session"
        coEvery { authenticationRepository.getSessionId() } returns flowOf(sessionId)
        coEvery { seriesRepository.getUserSeriesRate(sessionId) } throws RuntimeException("Network error")

        useCase.invoke()
    }

    @Test
    fun `should return rated series in correct order`() = runTest {
        val sessionId = "ordered_session"
        val expectedRatedSeries = listOf(
            RatedSeries(
                rate = 8.0, series = Series(
                    id = 3,
                    title = "Third Series",
                    imageUrl = "https://example.com/third.jpg",
                    rate = 8.0,
                    airDate = "2023-05-01",
                    seasons = emptyList(),
                    description = "Third series",
                    genre = emptyList()
                )
            ),
            RatedSeries(
                rate = 9.5, series = Series(
                    id = 1,
                    title = "First Series",
                    imageUrl = "https://example.com/first.jpg",
                    rate = 9.5,
                    airDate = "2023-06-01",
                    seasons = emptyList(),
                    description = "First series",
                    genre = emptyList()
                )
            ),
            RatedSeries(
                rate = 7.5, series = Series(
                    id = 2,
                    title = "Second Series",
                    imageUrl = "https://example.com/second.jpg",
                    rate = 7.5,
                    airDate = "2023-07-01",
                    seasons = emptyList(),
                    description = "Second series",
                    genre = emptyList()
                )
            )
        )
        coEvery { authenticationRepository.getSessionId() } returns flowOf(sessionId)
        coEvery { seriesRepository.getUserSeriesRate(sessionId) } returns expectedRatedSeries

        val result = useCase.invoke()

        assertThat(result).containsExactlyElementsIn(expectedRatedSeries).inOrder()
        coVerify(exactly = 1) { seriesRepository.getUserSeriesRate(sessionId) }
    }

    @Test
    fun `should handle different rating values`() = runTest {
        val sessionId = "rating_session"
        val expectedRatedSeries = listOf(
            RatedSeries(
                rate = 0.0, series = Series(
                    id = 1,
                    title = "Zero Rated",
                    imageUrl = "https://example.com/zero.jpg",
                    rate = 0.0,
                    airDate = "2023-08-01",
                    seasons = emptyList(),
                    description = "Zero rated series",
                    genre = emptyList()
                )
            ),
            RatedSeries(
                rate = 5.5, series = Series(
                    id = 2,
                    title = "Half Rated",
                    imageUrl = "https://example.com/half.jpg",
                    rate = 5.5,
                    airDate = "2023-09-01",
                    seasons = emptyList(),
                    description = "Half rated series",
                    genre = emptyList()
                )
            ),
            RatedSeries(
                rate = 10.0, series = Series(
                    id = 3,
                    title = "Perfect Rated",
                    imageUrl = "https://example.com/perfect.jpg",
                    rate = 10.0,
                    airDate = "2023-10-01",
                    seasons = emptyList(),
                    description = "Perfect rated series",
                    genre = emptyList()
                )
            )
        )
        coEvery { authenticationRepository.getSessionId() } returns flowOf(sessionId)
        coEvery { seriesRepository.getUserSeriesRate(sessionId) } returns expectedRatedSeries

        val result = useCase.invoke()

        assertThat(result[0].rate).isEqualTo(0.0)
        assertThat(result[1].rate).isEqualTo(5.5)
        assertThat(result[2].rate).isEqualTo(10.0)
        assertThat(result).isEqualTo(expectedRatedSeries)
        coVerify(exactly = 1) { seriesRepository.getUserSeriesRate(sessionId) }
    }
}