package com.madrid.domain.usecase.series

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.entity.Genre
import com.madrid.domain.entity.Series
import com.madrid.domain.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetSeriesDetailsUseCaseTest {
    private val seriesRepository: SeriesRepository = mockk(relaxed = true)
    private lateinit var useCase: GetSeriesDetailsUseCase

    @Before
    fun setUp() {
        useCase = GetSeriesDetailsUseCase(seriesRepository)
    }

    @Test
    fun `should get series details and increase genre interest points`() = runTest {
        val seriesId = 123
        val genres = listOf(
            Genre(id = 1, name = "Action"),
            Genre(id = 2, name = "Drama")
        )
        val expectedSeries = Series(
            id = seriesId,
            title = "Test Series",
            imageUrl = "test_image_url",
            rate = 8.5,
            airDate = "2023-01-01",
            seasons = emptyList(),
            description = "Test description",
            genre = genres
        )
        coEvery { seriesRepository.getSeriesDetailsById(seriesId) } returns expectedSeries

        val result = useCase.invoke(seriesId)

        assertThat(result).isEqualTo(expectedSeries)
        coVerify(exactly = 1) { seriesRepository.getSeriesDetailsById(seriesId) }
        coVerify(exactly = 1) { seriesRepository.increaseSeriesGenreInterestPoints("Action") }
        coVerify(exactly = 1) { seriesRepository.increaseSeriesGenreInterestPoints("Drama") }
    }

    @Test
    fun `should handle series with single genre`() = runTest {
        val seriesId = 456
        val genres = listOf(Genre(id = 1, name = "Comedy"))
        val expectedSeries = Series(
            id = seriesId,
            title = "Comedy Series",
            imageUrl = "test_image_url",
            rate = 7.0,
            airDate = "2023-01-01",
            seasons = emptyList(),
            description = "Test description",
            genre = genres
        )
        coEvery { seriesRepository.getSeriesDetailsById(seriesId) } returns expectedSeries

        val result = useCase.invoke(seriesId)

        assertThat(result).isEqualTo(expectedSeries)
        coVerify(exactly = 1) { seriesRepository.getSeriesDetailsById(seriesId) }
        coVerify(exactly = 1) { seriesRepository.increaseSeriesGenreInterestPoints("Comedy") }
    }

    @Test
    fun `should handle series with no genres`() = runTest {
        val seriesId = 789
        val genres = emptyList<Genre>()
        val expectedSeries = Series(
            id = seriesId,
            title = "No Genre Series",
            imageUrl = "test_image_url",
            rate = 6.0,
            airDate = "2023-01-01",
            seasons = emptyList(),
            description = "Test description",
            genre = genres
        )
        coEvery { seriesRepository.getSeriesDetailsById(seriesId) } returns expectedSeries

        val result = useCase.invoke(seriesId)

        assertThat(result).isEqualTo(expectedSeries)
        coVerify(exactly = 1) { seriesRepository.getSeriesDetailsById(seriesId) }
        coVerify(exactly = 0) { seriesRepository.increaseSeriesGenreInterestPoints(any()) }
    }

    @Test
    fun `should handle series with multiple genres`() = runTest {
        val seriesId = 111
        val genres = listOf(
            Genre(id = 1, name = "Thriller"),
            Genre(id = 2, name = "Mystery"),
            Genre(id = 3, name = "Crime"),
            Genre(id = 4, name = "Drama")
        )
        val expectedSeries = Series(
            id = seriesId,
            title = "Multi Genre Series",
            imageUrl = "test_image_url",
            rate = 8.0,
            airDate = "2023-01-01",
            seasons = emptyList(),
            description = "Test description",
            genre = genres
        )
        coEvery { seriesRepository.getSeriesDetailsById(seriesId) } returns expectedSeries

        val result = useCase.invoke(seriesId)

        assertThat(result).isEqualTo(expectedSeries)
        coVerify(exactly = 1) { seriesRepository.getSeriesDetailsById(seriesId) }
        coVerify(exactly = 1) { seriesRepository.increaseSeriesGenreInterestPoints("Thriller") }
        coVerify(exactly = 1) { seriesRepository.increaseSeriesGenreInterestPoints("Mystery") }
        coVerify(exactly = 1) { seriesRepository.increaseSeriesGenreInterestPoints("Crime") }
        coVerify(exactly = 1) { seriesRepository.increaseSeriesGenreInterestPoints("Drama") }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when getSeriesDetailsById fails`() = runTest {
        val seriesId = 999
        coEvery { seriesRepository.getSeriesDetailsById(seriesId) } throws RuntimeException("Series not found")

        useCase.invoke(seriesId)
    }
    
    @Test
    fun `should handle different seriesId values`() = runTest {
        val seriesId = 0
        val genres = listOf(Genre(id = 1, name = "Documentary"))
        val expectedSeries = Series(
            id = seriesId,
            title = "Documentary Series",
            imageUrl = "test_image_url",
            rate = 8.5,
            airDate = "2023-01-01",
            seasons = emptyList(),
            description = "Test description",
            genre = genres
        )
        coEvery { seriesRepository.getSeriesDetailsById(seriesId) } returns expectedSeries

        val result = useCase.invoke(seriesId)

        assertThat(result).isEqualTo(expectedSeries)
        coVerify(exactly = 1) { seriesRepository.getSeriesDetailsById(seriesId) }
        coVerify(exactly = 1) { seriesRepository.increaseSeriesGenreInterestPoints("Documentary") }
    }
}