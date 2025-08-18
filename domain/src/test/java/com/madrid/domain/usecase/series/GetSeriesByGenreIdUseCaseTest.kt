package com.madrid.domain.usecase.series

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.entity.Series
import com.madrid.domain.entity.SortType
import com.madrid.domain.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetSeriesByGenreIdUseCaseTest {
    private val seriesRepository: SeriesRepository = mockk(relaxed = true)
    private lateinit var useCase: GetSeriesByGenreIdUseCase

    @Before
    fun setUp() {
        useCase = GetSeriesByGenreIdUseCase(seriesRepository)
    }

    @Test
    fun `should call repository getSeriesByGenreId with page genreId and sortBy`() =
        runTest {
            val page = 1
            val genreId = 123
            val sortBy = SortType.POPULARITY
            val expectedSeries = listOf(
                Series(
                    id = 1,
                    title = "Series 1",
                    imageUrl = "",
                    rate = 0.0,
                    airDate = "",
                    seasons = emptyList(),
                    description = "",
                    genre = emptyList()
                ),
                Series(
                    id = 2,
                    title = "Series 2",
                    imageUrl = "",
                    rate = 0.0,
                    airDate = "",
                    seasons = emptyList(),
                    description = "",
                    genre = emptyList()
                )
            )
            coEvery {
                seriesRepository.getSeriesByGenreId(
                    page,
                    genreId,
                    sortBy
                )
            } returns expectedSeries

            val result = useCase.invoke(page, genreId, sortBy)

            assertThat(result).isEqualTo(expectedSeries)
            coVerify(exactly = 1) { seriesRepository.getSeriesByGenreId(page, genreId, sortBy) }
        }

    @Test
    fun `should handle null genreId`() = runTest {
        val page = 1
        val genreId = null
        val sortBy = SortType.ALL
        val expectedSeries = listOf(
            Series(
                id = 1,
                title = "All Genres Series",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            )
        )
        coEvery {
            seriesRepository.getSeriesByGenreId(
                page,
                genreId,
                sortBy
            )
        } returns expectedSeries

        val result = useCase.invoke(page, genreId, sortBy)

        assertThat(result).isEqualTo(expectedSeries)
        coVerify(exactly = 1) { seriesRepository.getSeriesByGenreId(page, genreId, sortBy) }
    }

    @Test
    fun `should return empty list when no series found`() = runTest {
        val page = 1
        val genreId = 456
        val sortBy = SortType.POPULARITY
        val expectedSeries = emptyList<Series>()
        coEvery {
            seriesRepository.getSeriesByGenreId(
                page,
                genreId,
                sortBy
            )
        } returns expectedSeries

        val result = useCase.invoke(page, genreId, sortBy)

        assertThat(result).isEmpty()
        coVerify(exactly = 1) { seriesRepository.getSeriesByGenreId(page, genreId, sortBy) }
    }

    @Test
    fun `should handle different sort types`() = runTest {
        val page = 2
        val genreId = 789
        val sortBy = SortType.LATEST
        val expectedSeries = listOf(
            Series(
                id = 3,
                title = "Sorted Series",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            )
        )
        coEvery {
            seriesRepository.getSeriesByGenreId(
                page,
                genreId,
                sortBy
            )
        } returns expectedSeries

        val result = useCase.invoke(page, genreId, sortBy)

        assertThat(result).isEqualTo(expectedSeries)
        coVerify(exactly = 1) { seriesRepository.getSeriesByGenreId(page, genreId, sortBy) }
    }

    @Test
    fun `should handle large page numbers`() = runTest {
        val page = 999
        val genreId = 111
        val sortBy = SortType.POPULARITY
        val expectedSeries = listOf(
            Series(
                id = 4,
                title = "Last Page Series",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            )
        )
        coEvery {
            seriesRepository.getSeriesByGenreId(
                page,
                genreId,
                sortBy
            )
        } returns expectedSeries

        val result = useCase.invoke(page, genreId, sortBy)

        assertThat(result).isEqualTo(expectedSeries)
        coVerify(exactly = 1) { seriesRepository.getSeriesByGenreId(page, genreId, sortBy) }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        val page = 1
        val genreId = 123
        val sortBy = SortType.POPULARITY
        coEvery {
            seriesRepository.getSeriesByGenreId(
                page,
                genreId,
                sortBy
            )
        } throws RuntimeException("Network error")

        useCase.invoke(page, genreId, sortBy)
    }

    @Test
    fun `should return multiple series for genre`() = runTest {
        val page = 3
        val genreId = 555
        val sortBy = SortType.POPULARITY
        val expectedSeries = (1..15).map {
            Series(
                id = it,
                title = "Genre Series $it",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            )
        }
        coEvery {
            seriesRepository.getSeriesByGenreId(
                page,
                genreId,
                sortBy
            )
        } returns expectedSeries

        val result = useCase.invoke(page, genreId, sortBy)

        assertThat(result).hasSize(15)
        assertThat(result).isEqualTo(expectedSeries)
        coVerify(exactly = 1) { seriesRepository.getSeriesByGenreId(page, genreId, sortBy) }
    }
}