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

class GetSeriesByGenresUseCaseTest {
    private val seriesRepository: SeriesRepository = mockk(relaxed = true)
    private lateinit var useCase: GetSeriesByGenresUseCase

    @Before
    fun setUp() {
        useCase = GetSeriesByGenresUseCase(seriesRepository)
    }

    @Test
    fun `should call repository getSeriesByGenres and return map`() = runTest {
        val expectedMap = mapOf(
            "Action" to listOf(
                Series(
                    id = 1,
                    title = "Action Series 1",
                    imageUrl = "https://example.com/action1.jpg",
                    rate = 8.5,
                    airDate = "2023-01-01",
                    seasons = emptyList(),
                    description = "An action-packed series",
                    genre = emptyList()
                )
            ),
            "Comedy" to listOf(
                Series(
                    id = 2,
                    title = "Comedy Series 1",
                    imageUrl = "https://example.com/comedy1.jpg",
                    rate = 7.8,
                    airDate = "2023-02-01",
                    seasons = emptyList(),
                    description = "A hilarious comedy series",
                    genre = emptyList()
                )
            )
        )
        coEvery { seriesRepository.getSeriesByGenres() } returns expectedMap

        val result = useCase.invoke()

        assertThat(result).isEqualTo(expectedMap)
        coVerify(exactly = 1) { seriesRepository.getSeriesByGenres() }
    }

    @Test
    fun `should return empty map when no series found`() = runTest {
        val expectedMap = emptyMap<String, List<Series>>()
        coEvery { seriesRepository.getSeriesByGenres() } returns expectedMap

        val result = useCase.invoke()

        assertThat(result).isEmpty()
        coVerify(exactly = 1) { seriesRepository.getSeriesByGenres() }
    }

    @Test
    fun `should return single genre with multiple series`() = runTest {
        val expectedMap = mapOf(
            "Drama" to listOf(
                Series(
                    id = 1,
                    title = "Drama Series 1",
                    imageUrl = "https://example.com/drama1.jpg",
                    rate = 8.0,
                    airDate = "2023-01-01",
                    seasons = emptyList(),
                    description = "A drama series",
                    genre = emptyList()
                ),
                Series(
                    id = 2,
                    title = "Drama Series 2",
                    imageUrl = "https://example.com/drama2.jpg",
                    rate = 7.5,
                    airDate = "2023-02-01",
                    seasons = emptyList(),
                    description = "Another drama series",
                    genre = emptyList()
                ),
                Series(
                    id = 3,
                    title = "Drama Series 3",
                    imageUrl = "https://example.com/drama3.jpg",
                    rate = 8.2,
                    airDate = "2023-03-01",
                    seasons = emptyList(),
                    description = "Third drama series",
                    genre = emptyList()
                )
            )
        )
        coEvery { seriesRepository.getSeriesByGenres() } returns expectedMap

        val result = useCase.invoke()

        assertThat(result).hasSize(1)
        assertThat(result["Drama"]).hasSize(3)
        assertThat(result).isEqualTo(expectedMap)
        coVerify(exactly = 1) { seriesRepository.getSeriesByGenres() }
    }

    @Test
    fun `should return multiple genres with series`() = runTest {
        val expectedMap = mapOf(
            "Thriller" to listOf(
                Series(
                    id = 1,
                    title = "Thriller 1",
                    imageUrl = "https://example.com/thriller1.jpg",
                    rate = 8.0,
                    airDate = "2023-01-01",
                    seasons = emptyList(),
                    description = "A thriller series",
                    genre = emptyList()
                )
            ),
            "Horror" to listOf(
                Series(
                    id = 2,
                    title = "Horror 1",
                    imageUrl = "https://example.com/horror1.jpg",
                    rate = 7.5,
                    airDate = "2023-02-01",
                    seasons = emptyList(),
                    description = "A horror series",
                    genre = emptyList()
                )
            ),
            "Sci-Fi" to listOf(
                Series(
                    id = 3,
                    title = "Sci-Fi 1",
                    imageUrl = "https://example.com/scifi1.jpg",
                    rate = 8.5,
                    airDate = "2023-03-01",
                    seasons = emptyList(),
                    description = "A sci-fi series",
                    genre = emptyList()
                )
            ),
            "Romance" to listOf(
                Series(
                    id = 4,
                    title = "Romance 1",
                    imageUrl = "https://example.com/romance1.jpg",
                    rate = 7.8,
                    airDate = "2023-04-01",
                    seasons = emptyList(),
                    description = "A romance series",
                    genre = emptyList()
                )
            )
        )
        coEvery { seriesRepository.getSeriesByGenres() } returns expectedMap

        val result = useCase.invoke()

        assertThat(result).hasSize(4)
        assertThat(result).containsKey("Thriller")
        assertThat(result).containsKey("Horror")
        assertThat(result).containsKey("Sci-Fi")
        assertThat(result).containsKey("Romance")
        assertThat(result).isEqualTo(expectedMap)
        coVerify(exactly = 1) { seriesRepository.getSeriesByGenres() }
    }

    @Test
    fun `should handle genres with empty series lists`() = runTest {
        val expectedMap = mapOf(
            "Documentary" to emptyList(),
            "Animation" to listOf(
                Series(
                    id = 1,
                    title = "Animated Series",
                    imageUrl = "https://example.com/animated.jpg",
                    rate = 8.0,
                    airDate = "2023-01-01",
                    seasons = emptyList(),
                    description = "An animated series",
                    genre = emptyList()
                )
            )
        )
        coEvery { seriesRepository.getSeriesByGenres() } returns expectedMap

        val result = useCase.invoke()

        assertThat(result).hasSize(2)
        assertThat(result["Documentary"]).isEmpty()
        assertThat(result["Animation"]).hasSize(1)
        assertThat(result).isEqualTo(expectedMap)
        coVerify(exactly = 1) { seriesRepository.getSeriesByGenres() }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        coEvery { seriesRepository.getSeriesByGenres() } throws RuntimeException("Network error")

        useCase.invoke()
    }

    @Test
    fun `should return large map with many genres and series`() = runTest {
        val expectedMap = (1..10).associate { genreIndex ->
            "Genre$genreIndex" to (1..5).map { seriesIndex ->
                Series(
                    id = genreIndex * 10 + seriesIndex,
                    title = "Series $seriesIndex in Genre $genreIndex",
                    imageUrl = "https://example.com/series$seriesIndex.jpg",
                    rate = 7.0 + (seriesIndex * 0.2),
                    airDate = "2023-0$seriesIndex-01",
                    seasons = emptyList(),
                    description = "Description for series $seriesIndex",
                    genre = emptyList()
                )
            }
        }
        coEvery { seriesRepository.getSeriesByGenres() } returns expectedMap

        val result = useCase.invoke()

        assertThat(result).hasSize(10)
        assertThat(result).isEqualTo(expectedMap)
        coVerify(exactly = 1) { seriesRepository.getSeriesByGenres() }
    }
}