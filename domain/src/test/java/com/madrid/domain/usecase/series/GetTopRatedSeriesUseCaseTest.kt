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

class GetTopRatedSeriesUseCaseTest {
    private val seriesRepository: SeriesRepository = mockk(relaxed = true)
    private lateinit var useCase: GetTopRatedSeriesUseCase

    @Before
    fun setUp() {
        useCase = GetTopRatedSeriesUseCase(seriesRepository)
    }

    @Test
    fun `should call repository getTopRatedSeries and return list`() = runTest {
        val page = 1
        val expectedSeries = listOf(
            Series(
                id = 1,
                title = "Top Rated Series 1",
                imageUrl = "",
                rate = 8.5,
                airDate = "2023-01-01",
                seasons = emptyList(),
                description = "Test description 1",
                genre = emptyList()
            ),
            Series(
                id = 2,
                title = "Top Rated Series 2",
                imageUrl = "",
                rate = 8.0,
                airDate = "2023-01-02",
                seasons = emptyList(),
                description = "Test description 2",
                genre = emptyList()
            )
        )
        coEvery { seriesRepository.getTopRatedSeries(page = page) } returns expectedSeries

        val result = useCase.invoke(page)

        assertThat(result).isEqualTo(expectedSeries)
        coVerify(exactly = 1) { seriesRepository.getTopRatedSeries(page = page) }
    }

    @Test
    fun `should return empty list when no top rated series found`() = runTest {
        val page = 1
        val expectedSeries = emptyList<Series>()
        coEvery { seriesRepository.getTopRatedSeries(page = page) } returns expectedSeries

        val result = useCase.invoke(page)

        assertThat(result).isEmpty()
        coVerify(exactly = 1) { seriesRepository.getTopRatedSeries(page = page) }
    }

    @Test
    fun `should return single top rated series`() = runTest {
        val page = 1
        val expectedSeries = listOf(
            Series(
                id = 1,
                title = "Single Top Rated",
                imageUrl = "",
                rate = 9.0,
                airDate = "2023-01-01",
                seasons = emptyList(),
                description = "Test description",
                genre = emptyList()
            )
        )
        coEvery { seriesRepository.getTopRatedSeries(page = page) } returns expectedSeries

        val result = useCase.invoke(page)

        assertThat(result).hasSize(1)
        assertThat(result.first().title).isEqualTo("Single Top Rated")
        assertThat(result).isEqualTo(expectedSeries)
        coVerify(exactly = 1) { seriesRepository.getTopRatedSeries(page = page) }
    }

    @Test
    fun `should return multiple top rated series`() = runTest {
        val page = 1
        val expectedSeries = (1..20).map {
            Series(
                id = it,
                title = "Top Rated Series $it",
                imageUrl = "",
                rate = 8.0,
                airDate = "2023-01-01",
                seasons = emptyList(),
                description = "Test description $it",
                genre = emptyList()
            )
        }
        coEvery { seriesRepository.getTopRatedSeries(page = page) } returns expectedSeries

        val result = useCase.invoke(page)

        assertThat(result).hasSize(20)
        assertThat(result).containsExactlyElementsIn(expectedSeries)
        coVerify(exactly = 1) { seriesRepository.getTopRatedSeries(page = page) }
    }

    @Test
    fun `should handle different page values`() = runTest {
        val page = 5
        val expectedSeries = listOf(
            Series(
                id = 1,
                title = "Page 5 Series",
                imageUrl = "",
                rate = 7.5,
                airDate = "2023-01-01",
                seasons = emptyList(),
                description = "Test description",
                genre = emptyList()
            )
        )
        coEvery { seriesRepository.getTopRatedSeries(page = page) } returns expectedSeries

        val result = useCase.invoke(page)

        assertThat(result).isEqualTo(expectedSeries)
        coVerify(exactly = 1) { seriesRepository.getTopRatedSeries(page = page) }
    }

    @Test
    fun `should handle first page`() = runTest {
        val page = 1
        val expectedSeries = listOf(
            Series(
                id = 1,
                title = "First Page Series",
                imageUrl = "",
                rate = 8.5,
                airDate = "2023-01-01",
                seasons = emptyList(),
                description = "Test description",
                genre = emptyList()
            )
        )
        coEvery { seriesRepository.getTopRatedSeries(page = page) } returns expectedSeries

        val result = useCase.invoke(page)

        assertThat(result).isEqualTo(expectedSeries)
        coVerify(exactly = 1) { seriesRepository.getTopRatedSeries(page = page) }
    }

    @Test
    fun `should handle large page values`() = runTest {
        val page = 999
        val expectedSeries = listOf(
            Series(
                id = 1,
                title = "Large Page Series",
                imageUrl = "",
                rate = 7.0,
                airDate = "2023-01-01",
                seasons = emptyList(),
                description = "Test description",
                genre = emptyList()
            )
        )
        coEvery { seriesRepository.getTopRatedSeries(page = page) } returns expectedSeries

        val result = useCase.invoke(page)

        assertThat(result).isEqualTo(expectedSeries)
        coVerify(exactly = 1) { seriesRepository.getTopRatedSeries(page = page) }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        val page = 1
        coEvery { seriesRepository.getTopRatedSeries(page = page) } throws RuntimeException("Network error")

        useCase.invoke(page)
    }

    @Test
    fun `should return top rated series in correct order`() = runTest {
        val page = 1
        val expectedSeries = listOf(
            Series(
                id = 3,
                title = "Third Top Rated",
                imageUrl = "",
                rate = 9.0,
                airDate = "2023-01-03",
                seasons = emptyList(),
                description = "Test description 3",
                genre = emptyList()
            ),
            Series(
                id = 1,
                title = "First Top Rated",
                imageUrl = "",
                rate = 9.5,
                airDate = "2023-01-01",
                seasons = emptyList(),
                description = "Test description 1",
                genre = emptyList()
            ),
            Series(
                id = 2,
                title = "Second Top Rated",
                imageUrl = "",
                rate = 9.2,
                airDate = "2023-01-02",
                seasons = emptyList(),
                description = "Test description 2",
                genre = emptyList()
            )
        )
        coEvery { seriesRepository.getTopRatedSeries(page = page) } returns expectedSeries

        val result = useCase.invoke(page)

        assertThat(result).containsExactlyElementsIn(expectedSeries).inOrder()
        coVerify(exactly = 1) { seriesRepository.getTopRatedSeries(page = page) }
    }
}