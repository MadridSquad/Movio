package com.madrid.domain.usecase.search

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.entity.Genre
import com.madrid.domain.entity.Series
import com.madrid.domain.repository.SearchRepository
import com.madrid.domain.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetSeriesByQueryUseCaseTest {
    private val seriesRepository: SeriesRepository = mockk(relaxed = true)
    private val searchRepository: SearchRepository = mockk(relaxed = true)
    private lateinit var useCase: GetSeriesByQueryUseCase

    @Before
    fun setUp() {
        useCase = GetSeriesByQueryUseCase(seriesRepository, searchRepository)
    }

    @Test
    fun `should call repositories and return sorted series by interest points`() = runTest {
        val query = "Breaking"
        val genres = listOf(
            Genre(id = 1, name = "Drama", interestPoints = 100),
            Genre(id = 2, name = "Comedy", interestPoints = 50)
        )
        val series = listOf(
            Series(
                id = 1,
                title = "Series1",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = listOf(Genre(id = 1, name = "Comedy", interestPoints = 0))
            ),
            Series(
                id = 2,
                title = "Series2",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = listOf(Genre(id = 2, name = "Drama", interestPoints = 0))
            )
        )
        coEvery { seriesRepository.getSeriesGenres() } returns genres
        coEvery { searchRepository.getSeriesByQuery(query, 1) } returns series

        val result = useCase.invoke(query)

        assertThat(result).isEqualTo(listOf(series[1], series[0]))
        coVerify(exactly = 1) { seriesRepository.getSeriesGenres() }
        coVerify(exactly = 1) { searchRepository.getSeriesByQuery(query, 1) }
    }

    @Test
    fun `should call repositories with specific page number`() = runTest {
        val query = "Game of Thrones"
        val page = 3
        val genres = listOf(Genre(id = 1, name = "Fantasy", interestPoints = 75))
        val series = listOf(
            Series(
                id = 1,
                title = "Series1",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            )
        )
        coEvery { seriesRepository.getSeriesGenres() } returns genres
        coEvery { searchRepository.getSeriesByQuery(query, page) } returns series

        val result = useCase.invoke(query, page)

        assertThat(result).isEqualTo(series)
        coVerify(exactly = 1) { seriesRepository.getSeriesGenres() }
        coVerify(exactly = 1) { searchRepository.getSeriesByQuery(query, page) }
    }

    @Test
    fun `should handle empty series list`() = runTest {
        val query = "Unknown"
        val genres = listOf(Genre(id = 1, name = "Thriller", interestPoints = 80))
        coEvery { seriesRepository.getSeriesGenres() } returns genres
        coEvery { searchRepository.getSeriesByQuery(query, 1) } returns emptyList()

        val result = useCase.invoke(query)

        assertThat(result).isEmpty()
        coVerify(exactly = 1) { seriesRepository.getSeriesGenres() }
        coVerify(exactly = 1) { searchRepository.getSeriesByQuery(query, 1) }
    }

    @Test
    fun `should handle series with unknown genres`() = runTest {
        val query = "Horror"
        val genres = listOf(Genre(id = 1, name = "Drama", interestPoints = 90))
        val series = listOf(
            Series(
                id = 1,
                title = "Series1",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = listOf(Genre(id = 1, name = "Unknown", interestPoints = 0))
            )
        )
        coEvery { seriesRepository.getSeriesGenres() } returns genres
        coEvery { searchRepository.getSeriesByQuery(query, 1) } returns series

        val result = useCase.invoke(query)

        assertThat(result).isEqualTo(series)
        coVerify(exactly = 1) { seriesRepository.getSeriesGenres() }
        coVerify(exactly = 1) { searchRepository.getSeriesByQuery(query, 1) }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when seriesRepository fails`() = runTest {
        val query = "Test"
        coEvery { seriesRepository.getSeriesGenres() } throws RuntimeException("Repository error")

        useCase.invoke(query)
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when searchRepository fails`() = runTest {
        val query = "Test"
        val genres = listOf(Genre(id = 1, name = "Action", interestPoints = 100))
        coEvery { seriesRepository.getSeriesGenres() } returns genres
        coEvery {
            searchRepository.getSeriesByQuery(
                query,
                1
            )
        } throws RuntimeException("Search error")

        useCase.invoke(query)
    }
}