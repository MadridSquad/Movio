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

class GetSimilarSeriesUseCaseTest {
    private val seriesRepository: SeriesRepository = mockk(relaxed = true)
    private lateinit var useCase: GetSimilarSeriesUseCase

    @Before
    fun setUp() {
        useCase = GetSimilarSeriesUseCase(seriesRepository)
    }

    @Test
    fun `should call repository getSimilarSeriesById and return list`() = runTest {
        val seriesId = 123
        val expectedSeries = listOf(
            Series(
                id = 1,
                title = "Similar Series 1",
                imageUrl = "https://example.com/image1.jpg",
                rate = 8.5,
                airDate = "2023-01-01",
                seasons = emptyList(),
                description = "Description for Similar Series 1",
                genre = emptyList()
            ),
            Series(
                id = 2,
                title = "Similar Series 2",
                imageUrl = "https://example.com/image2.jpg",
                rate = 7.8,
                airDate = "2023-02-01",
                seasons = emptyList(),
                description = "Description for Similar Series 2",
                genre = emptyList()
            )
        )
        coEvery { seriesRepository.getSimilarSeriesById(seriesId) } returns expectedSeries

        val result = useCase.invoke(seriesId)

        assertThat(result).isEqualTo(expectedSeries)
        coVerify(exactly = 1) { seriesRepository.getSimilarSeriesById(seriesId) }
    }

    @Test
    fun `should return empty list when no similar series found`() = runTest {
        val seriesId = 456
        val expectedSeries = emptyList<Series>()
        coEvery { seriesRepository.getSimilarSeriesById(seriesId) } returns expectedSeries

        val result = useCase.invoke(seriesId)

        assertThat(result).isEmpty()
        coVerify(exactly = 1) { seriesRepository.getSimilarSeriesById(seriesId) }
    }

    @Test
    fun `should return single similar series`() = runTest {
        val seriesId = 789
        val expectedSeries = listOf(
            Series(
                id = 1,
                title = "Single Similar Series",
                imageUrl = "https://example.com/single.jpg",
                rate = 9.0,
                airDate = "2023-03-01",
                seasons = emptyList(),
                description = "Description for Single Similar Series",
                genre = emptyList()
            )
        )
        coEvery { seriesRepository.getSimilarSeriesById(seriesId) } returns expectedSeries

        val result = useCase.invoke(seriesId)

        assertThat(result).hasSize(1)
        assertThat(result.first().title).isEqualTo("Single Similar Series")
        assertThat(result).isEqualTo(expectedSeries)
        coVerify(exactly = 1) { seriesRepository.getSimilarSeriesById(seriesId) }
    }

    @Test
    fun `should return multiple similar series`() = runTest {
        val seriesId = 111
        val expectedSeries = (1..20).map {
            Series(
                id = it,
                title = "Similar Series $it",
                imageUrl = "https://example.com/image$it.jpg",
                rate = 8.0,
                airDate = "2023-04-01",
                seasons = emptyList(),
                description = "Description for Similar Series $it",
                genre = emptyList()
            )
        }
        coEvery { seriesRepository.getSimilarSeriesById(seriesId) } returns expectedSeries

        val result = useCase.invoke(seriesId)

        assertThat(result).hasSize(20)
        assertThat(result).containsExactlyElementsIn(expectedSeries)
        coVerify(exactly = 1) { seriesRepository.getSimilarSeriesById(seriesId) }
    }

    @Test
    fun `should handle different seriesId values`() = runTest {
        val seriesId = 0
        val expectedSeries = listOf(
            Series(
                id = 1,
                title = "Zero Series Similar",
                imageUrl = "https://example.com/zero.jpg",
                rate = 6.5,
                airDate = "2023-05-01",
                seasons = emptyList(),
                description = "Description for Zero Series Similar",
                genre = emptyList()
            )
        )
        coEvery { seriesRepository.getSimilarSeriesById(seriesId) } returns expectedSeries

        val result = useCase.invoke(seriesId)

        assertThat(result).isEqualTo(expectedSeries)
        coVerify(exactly = 1) { seriesRepository.getSimilarSeriesById(seriesId) }
    }

    @Test
    fun `should handle large seriesId values`() = runTest {
        val seriesId = 999999
        val expectedSeries = listOf(
            Series(
                id = 1,
                title = "Large ID Similar Series",
                imageUrl = "https://example.com/large.jpg",
                rate = 7.2,
                airDate = "2023-06-01",
                seasons = emptyList(),
                description = "Description for Large ID Similar Series",
                genre = emptyList()
            )
        )
        coEvery { seriesRepository.getSimilarSeriesById(seriesId) } returns expectedSeries

        val result = useCase.invoke(seriesId)

        assertThat(result).isEqualTo(expectedSeries)
        coVerify(exactly = 1) { seriesRepository.getSimilarSeriesById(seriesId) }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        val seriesId = 123
        coEvery { seriesRepository.getSimilarSeriesById(seriesId) } throws RuntimeException("Network error")

        useCase.invoke(seriesId)
    }

    @Test
    fun `should return similar series in correct order`() = runTest {
        val seriesId = 222
        val expectedSeries = listOf(
            Series(
                id = 3,
                title = "Third Similar",
                imageUrl = "https://example.com/third.jpg",
                rate = 8.8,
                airDate = "2023-07-01",
                seasons = emptyList(),
                description = "Description for Third Similar",
                genre = emptyList()
            ),
            Series(
                id = 1,
                title = "First Similar",
                imageUrl = "https://example.com/first.jpg",
                rate = 9.2,
                airDate = "2023-08-01",
                seasons = emptyList(),
                description = "Description for First Similar",
                genre = emptyList()
            ),
            Series(
                id = 2,
                title = "Second Similar",
                imageUrl = "https://example.com/second.jpg",
                rate = 8.1,
                airDate = "2023-09-01",
                seasons = emptyList(),
                description = "Description for Second Similar",
                genre = emptyList()
            )
        )
        coEvery { seriesRepository.getSimilarSeriesById(seriesId) } returns expectedSeries

        val result = useCase.invoke(seriesId)

        assertThat(result).containsExactlyElementsIn(expectedSeries).inOrder()
        coVerify(exactly = 1) { seriesRepository.getSimilarSeriesById(seriesId) }
    }
}