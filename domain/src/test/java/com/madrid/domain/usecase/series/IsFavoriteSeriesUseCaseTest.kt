package com.madrid.domain.usecase.series

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.entity.Series
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class IsFavoriteSeriesUseCaseTest {
    private val getFavoriteSeriesUseCase: GetFavoriteSeriesUseCase = mockk(relaxed = true)
    private lateinit var useCase: IsFavoriteSeriesUseCase

    @Before
    fun setUp() {
        useCase = IsFavoriteSeriesUseCase(getFavoriteSeriesUseCase)
    }

    @Test
    fun `should return true when series is in favorites`() = runTest {
        val seriesId = 123
        val favoriteSeries = listOf(
            Series(
                id = 123,
                title = "Favorite Series 1",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            ),
            Series(
                id = 456,
                title = "Favorite Series 2",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            )
        )
        coEvery { getFavoriteSeriesUseCase() } returns favoriteSeries

        val result = useCase.invoke(seriesId)

        assertThat(result).isTrue()
        coVerify(exactly = 1) { getFavoriteSeriesUseCase() }
    }

    @Test
    fun `should return false when series is not in favorites`() = runTest {
        val seriesId = 999
        val favoriteSeries = listOf(
            Series(
                id = 123,
                title = "Favorite Series 1",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            ),
            Series(
                id = 456,
                title = "Favorite Series 2",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            )
        )
        coEvery { getFavoriteSeriesUseCase() } returns favoriteSeries

        val result = useCase.invoke(seriesId)

        assertThat(result).isFalse()
        coVerify(exactly = 1) { getFavoriteSeriesUseCase() }
    }

    @Test
    fun `should return false when favorites list is empty`() = runTest {
        val seriesId = 123
        val favoriteSeries = emptyList<Series>()
        coEvery { getFavoriteSeriesUseCase() } returns favoriteSeries

        val result = useCase.invoke(seriesId)

        assertThat(result).isFalse()
        coVerify(exactly = 1) { getFavoriteSeriesUseCase() }
    }

    @Test
    fun `should return true when series is the only favorite`() = runTest {
        val seriesId = 123
        val favoriteSeries = listOf(
            Series(
                id = 123,
                title = "Only Favorite",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            )
        )
        coEvery { getFavoriteSeriesUseCase() } returns favoriteSeries

        val result = useCase.invoke(seriesId)

        assertThat(result).isTrue()
        coVerify(exactly = 1) { getFavoriteSeriesUseCase() }
    }

    @Test
    fun `should return true when series is first in favorites list`() = runTest {
        val seriesId = 123
        val favoriteSeries = listOf(
            Series(
                id = 123,
                title = "First Favorite",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            ),
            Series(
                id = 456,
                title = "Second Favorite",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            ),
            Series(
                id = 789,
                title = "Third Favorite",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            )
        )
        coEvery { getFavoriteSeriesUseCase() } returns favoriteSeries

        val result = useCase.invoke(seriesId)

        assertThat(result).isTrue()
        coVerify(exactly = 1) { getFavoriteSeriesUseCase() }
    }

    @Test
    fun `should return true when series is last in favorites list`() = runTest {
        val seriesId = 789
        val favoriteSeries = listOf(
            Series(
                id = 123,
                title = "First Favorite",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            ),
            Series(
                id = 456,
                title = "Second Favorite",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            ),
            Series(
                id = 789,
                title = "Last Favorite",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            )
        )
        coEvery { getFavoriteSeriesUseCase() } returns favoriteSeries

        val result = useCase.invoke(seriesId)

        assertThat(result).isTrue()
        coVerify(exactly = 1) { getFavoriteSeriesUseCase() }
    }

    @Test
    fun `should handle different seriesId values`() = runTest {
        val seriesId = 0
        val favoriteSeries = listOf(
            Series(
                id = 0,
                title = "Zero ID Series",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            )
        )
        coEvery { getFavoriteSeriesUseCase() } returns favoriteSeries

        val result = useCase.invoke(seriesId)

        assertThat(result).isTrue()
        coVerify(exactly = 1) { getFavoriteSeriesUseCase() }
    }

    @Test
    fun `should handle large seriesId values`() = runTest {
        val seriesId = 999999
        val favoriteSeries = listOf(
            Series(
                id = 999999,
                title = "Large ID Series",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            )
        )
        coEvery { getFavoriteSeriesUseCase() } returns favoriteSeries

        val result = useCase.invoke(seriesId)

        assertThat(result).isTrue()
        coVerify(exactly = 1) { getFavoriteSeriesUseCase() }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when getFavoriteSeriesUseCase fails`() = runTest {
        val seriesId = 123
        coEvery { getFavoriteSeriesUseCase() } throws RuntimeException("Network error")

        useCase.invoke(seriesId)
    }

    @Test
    fun `should return true when series appears multiple times in favorites`() = runTest {
        val seriesId = 123
        val favoriteSeries = listOf(
            Series(
                id = 123,
                title = "Duplicate Series 1",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            ),
            Series(
                id = 456,
                title = "Other Series",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            ),
            Series(
                id = 123,
                title = "Duplicate Series 2",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            )
        )
        coEvery { getFavoriteSeriesUseCase() } returns favoriteSeries

        val result = useCase.invoke(seriesId)

        assertThat(result).isTrue()
        coVerify(exactly = 1) { getFavoriteSeriesUseCase() }
    }
}