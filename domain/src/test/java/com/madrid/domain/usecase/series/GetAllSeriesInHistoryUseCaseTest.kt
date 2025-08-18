package com.madrid.domain.usecase.series

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.entity.Series
import com.madrid.domain.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetAllSeriesInHistoryUseCaseTest {
    private val seriesRepository: SeriesRepository = mockk(relaxed = true)
    private lateinit var useCase: GetAllSeriesInHistoryUseCase

    @Before
    fun setUp() {
        useCase = GetAllSeriesInHistoryUseCase(seriesRepository)
    }

    @Test
    fun `should call repository getAllSeriesInHistory and return series list`() = runTest {
        val expectedSeries = listOf(
            Series(
                id = 1,
                title = "Series1",
                imageUrl = "https://example.com/series1.jpg",
                rate = 8.5,
                airDate = "2023-01-01",
                seasons = emptyList(),
                description = "Test description for Series1",
                genre = emptyList()
            ),
            Series(
                id = 2,
                title = "Series2",
                imageUrl = "https://example.com/series2.jpg",
                rate = 7.8,
                airDate = "2023-02-01",
                seasons = emptyList(),
                description = "Test description for Series2",
                genre = emptyList()
            )
        )
        coEvery { seriesRepository.getAllSeriesInHistory() } returns expectedSeries

        val result = useCase.invoke()

        assertThat(result).isEqualTo(expectedSeries)
        coVerify(exactly = 1) { seriesRepository.getAllSeriesInHistory() }
    }

    @Test
    fun `should return empty list when no series in history`() = runTest {
        val expectedSeries = emptyList<Series>()
        coEvery { seriesRepository.getAllSeriesInHistory() } returns expectedSeries

        val result = useCase.invoke()

        assertThat(result).isEmpty()
        coVerify(exactly = 1) { seriesRepository.getAllSeriesInHistory() }
    }

    @Test
    fun `should return single series when only one in history`() = runTest {
        val expectedSeries = listOf(
            Series(
                id = 1,
                title = "Single Series",
                imageUrl = "https://example.com/single.jpg",
                rate = 9.0,
                airDate = "2023-03-01",
                seasons = emptyList(),
                description = "Test description for single series",
                genre = emptyList()
            )
        )
        coEvery { seriesRepository.getAllSeriesInHistory() } returns expectedSeries

        val result = useCase.invoke()

        assertThat(result).hasSize(1)
        assertThat(result).isEqualTo(expectedSeries)
        coVerify(exactly = 1) { seriesRepository.getAllSeriesInHistory() }
    }

    @Test
    fun `should return multiple series when many in history`() = runTest {
        val expectedSeries = (1..10).map {
            Series(
                id = it,
                title = "Series$it",
                imageUrl = "https://example.com/series$it.jpg",
                rate = 8.0,
                airDate = "2023-0${it % 12 + 1}-01",
                seasons = emptyList(),
                description = "Test description for Series$it",
                genre = emptyList()
            )
        }
        coEvery { seriesRepository.getAllSeriesInHistory() } returns expectedSeries

        val result = useCase.invoke()

        assertThat(result).hasSize(10)
        assertThat(result).isEqualTo(expectedSeries)
        coVerify(exactly = 1) { seriesRepository.getAllSeriesInHistory() }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        coEvery { seriesRepository.getAllSeriesInHistory() } throws RuntimeException("Database error")

        useCase.invoke()
    }

    @Test
    fun `should call repository exactly once`() = runTest {
        val expectedSeries = listOf(
            Series(
                id = 1,
                title = "Test Series",
                imageUrl = "https://example.com/test.jpg",
                rate = 8.0,
                airDate = "2023-04-01",
                seasons = emptyList(),
                description = "Test description",
                genre = emptyList()
            )
        )
        coEvery { seriesRepository.getAllSeriesInHistory() } returns expectedSeries

        useCase.invoke()

        coVerify(exactly = 1) { seriesRepository.getAllSeriesInHistory() }
    }
}