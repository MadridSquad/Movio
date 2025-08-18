package com.madrid.domain.usecase.series

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.entity.Genre
import com.madrid.domain.entity.Series
import org.junit.Before
import org.junit.Test

class FilterSeriesByCategoryUseCaseTest {
    private lateinit var useCase: FilterSeriesByCategoryUseCase

    @Before
    fun setUp() {
        useCase = FilterSeriesByCategoryUseCase()
    }

    @Test
    fun `should return series that match the category`() {
        val category = 1
        val matchingGenre = Genre(id = 1, name = "Drama", interestPoints = 0)
        val nonMatchingGenre = Genre(id = 2, name = "Comedy", interestPoints = 0)
        val series = listOf(
            Series(
                id = 1,
                title = "Series1",
                imageUrl = "https://example.com/image1.jpg",
                rate = 8.5,
                airDate = "2023-01-01",
                seasons = emptyList(),
                description = "Test series 1",
                genre = listOf(matchingGenre)
            ),
            Series(
                id = 2,
                title = "Series2",
                imageUrl = "https://example.com/image2.jpg",
                rate = 7.0,
                airDate = "2023-02-01",
                seasons = emptyList(),
                description = "Test series 2",
                genre = listOf(nonMatchingGenre)
            ),
            Series(
                id = 3,
                title = "Series3",
                imageUrl = "https://example.com/image3.jpg",
                rate = 9.0,
                airDate = "2023-03-01",
                seasons = emptyList(),
                description = "Test series 3",
                genre = listOf(matchingGenre, nonMatchingGenre)
            )
        )

        val result = useCase.invoke(series, category)

        assertThat(result).containsExactly(series[0], series[2])
    }

    @Test
    fun `should return empty list when no series match category`() {
        val category = 3
        val genre1 = Genre(id = 1, name = "Drama", interestPoints = 0)
        val genre2 = Genre(id = 2, name = "Comedy", interestPoints = 0)
        val series = listOf(
            Series(
                id = 1,
                title = "Series1",
                imageUrl = "https://example.com/image1.jpg",
                rate = 7.5,
                airDate = "2023-01-01",
                seasons = emptyList(),
                description = "Test series 1",
                genre = listOf(genre1)
            ),
            Series(
                id = 2,
                title = "Series2",
                imageUrl = "https://example.com/image2.jpg",
                rate = 6.0,
                airDate = "2023-02-01",
                seasons = emptyList(),
                description = "Test series 2",
                genre = listOf(genre2)
            )
        )

        val result = useCase.invoke(series, category)

        assertThat(result).isEmpty()
    }

    @Test
    fun `should return empty list when series list is empty`() {
        val category = 1
        val series = emptyList<Series>()

        val result = useCase.invoke(series, category)

        assertThat(result).isEmpty()
    }

    @Test
    fun `should return empty list when series have no genres`() {
        val category = 1
        val series = listOf(
            Series(
                id = 1,
                title = "Series1",
                imageUrl = "https://example.com/image1.jpg",
                rate = 7.0,
                airDate = "2023-01-01",
                seasons = emptyList(),
                description = "Test series 1",
                genre = emptyList()
            ),
            Series(
                id = 2,
                title = "Series2",
                imageUrl = "https://example.com/image2.jpg",
                rate = 6.5,
                airDate = "2023-02-01",
                seasons = emptyList(),
                description = "Test series 2",
                genre = emptyList()
            )
        )

        val result = useCase.invoke(series, category)

        assertThat(result).isEmpty()
    }

    @Test
    fun `should return all series when all match the category`() {
        val category = 1
        val matchingGenre = Genre(id = 1, name = "Drama", interestPoints = 0)
        val series = listOf(
            Series(
                id = 1,
                title = "Series1",
                imageUrl = "https://example.com/image1.jpg",
                rate = 8.0,
                airDate = "2023-01-01",
                seasons = emptyList(),
                description = "Test series 1",
                genre = listOf(matchingGenre)
            ),
            Series(
                id = 2,
                title = "Series2",
                imageUrl = "https://example.com/image2.jpg",
                rate = 7.5,
                airDate = "2023-02-01",
                seasons = emptyList(),
                description = "Test series 2",
                genre = listOf(matchingGenre)
            )
        )

        val result = useCase.invoke(series, category)

        assertThat(result).containsExactly(series[0], series[1])
    }

    @Test
    fun `should return series with multiple genres when one matches category`() {
        val category = 2
        val genre1 = Genre(id = 1, name = "Drama", interestPoints = 0)
        val genre2 = Genre(id = 2, name = "Comedy", interestPoints = 0)
        val genre3 = Genre(id = 3, name = "Action", interestPoints = 0)
        val series = listOf(
            Series(
                id = 1,
                title = "Series1",
                imageUrl = "https://example.com/image1.jpg",
                rate = 8.5,
                airDate = "2023-01-01",
                seasons = emptyList(),
                description = "Test series 1",
                genre = listOf(genre1, genre2, genre3)
            )
        )

        val result = useCase.invoke(series, category)

        assertThat(result).containsExactly(series[0])
    }
}