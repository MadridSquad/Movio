package com.madrid.domain.usecase.series

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.entity.Genre
import com.madrid.domain.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetSeriesGenresUseCaseTest {
    private val seriesRepository: SeriesRepository = mockk(relaxed = true)
    private lateinit var useCase: GetSeriesGenresUseCase

    @Before
    fun setUp() {
        useCase = GetSeriesGenresUseCase(seriesRepository)
    }

    @Test
    fun `should call repository getSeriesGenres and return list`() = runTest {
        val expectedGenres = listOf(
            Genre(id = 1, name = "Action"),
            Genre(id = 2, name = "Comedy"),
            Genre(id = 3, name = "Drama")
        )
        coEvery { seriesRepository.getSeriesGenres() } returns expectedGenres

        val result = useCase.invoke()

        assertThat(result).isEqualTo(expectedGenres)
        coVerify(exactly = 1) { seriesRepository.getSeriesGenres() }
    }

    @Test
    fun `should return empty list when no genres found`() = runTest {
        val expectedGenres = emptyList<Genre>()
        coEvery { seriesRepository.getSeriesGenres() } returns expectedGenres

        val result = useCase.invoke()

        assertThat(result).isEmpty()
        coVerify(exactly = 1) { seriesRepository.getSeriesGenres() }
    }

    @Test
    fun `should return single genre`() = runTest {
        val expectedGenres = listOf(Genre(id = 1, name = "Thriller"))
        coEvery { seriesRepository.getSeriesGenres() } returns expectedGenres

        val result = useCase.invoke()

        assertThat(result).hasSize(1)
        assertThat(result.first().name).isEqualTo("Thriller")
        assertThat(result).isEqualTo(expectedGenres)
        coVerify(exactly = 1) { seriesRepository.getSeriesGenres() }
    }

    @Test
    fun `should return multiple genres`() = runTest {
        val expectedGenres = listOf(
            Genre(id = 1, name = "Horror"),
            Genre(id = 2, name = "Sci-Fi"),
            Genre(id = 3, name = "Romance"),
            Genre(id = 4, name = "Mystery"),
            Genre(id = 5, name = "Documentary")
        )
        coEvery { seriesRepository.getSeriesGenres() } returns expectedGenres

        val result = useCase.invoke()

        assertThat(result).hasSize(5)
        assertThat(result).containsExactlyElementsIn(expectedGenres)
        coVerify(exactly = 1) { seriesRepository.getSeriesGenres() }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        coEvery { seriesRepository.getSeriesGenres() } throws RuntimeException("Network error")

        useCase.invoke()
    }

    @Test
    fun `should return genres in correct order`() = runTest {
        val expectedGenres = listOf(
            Genre(id = 3, name = "Crime"),
            Genre(id = 1, name = "Adventure"),
            Genre(id = 2, name = "Biography")
        )
        coEvery { seriesRepository.getSeriesGenres() } returns expectedGenres

        val result = useCase.invoke()

        assertThat(result).containsExactlyElementsIn(expectedGenres).inOrder()
        coVerify(exactly = 1) { seriesRepository.getSeriesGenres() }
    }

    @Test
    fun `should handle large number of genres`() = runTest {
        val expectedGenres = (1..50).map { Genre(id = it, name = "Genre $it") }
        coEvery { seriesRepository.getSeriesGenres() } returns expectedGenres

        val result = useCase.invoke()

        assertThat(result).hasSize(50)
        assertThat(result).isEqualTo(expectedGenres)
        coVerify(exactly = 1) { seriesRepository.getSeriesGenres() }
    }
}