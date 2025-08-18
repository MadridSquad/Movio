package com.madrid.domain.usecase.movie

import com.google.common.truth.Truth
import com.madrid.domain.entity.Movie
import com.madrid.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetAllMoviesInHistoryUseCaseTest {
    private val movieRepository: MovieRepository = mockk(relaxed = true)
    private lateinit var useCase: GetAllMoviesInHistoryUseCase

    @Before
    fun setUp() {
        useCase = GetAllMoviesInHistoryUseCase(movieRepository)
    }

    @Test
    fun `invoke SHOULD return movies list from repository`() = runTest {
        coEvery { movieRepository.getAllMoviesInHistory() } returns testMovies

        val result = useCase.invoke()

        Truth.assertThat(result).isEqualTo(testMovies)
        coVerify(exactly = 1) { movieRepository.getAllMoviesInHistory() }
    }

    @Test
    fun `invoke SHOULD return empty list when repository returns empty list`() = runTest {
        coEvery { movieRepository.getAllMoviesInHistory() } returns emptyList()

        val result = useCase.invoke()

        Truth.assertThat(result).isEmpty()
        coVerify(exactly = 1) { movieRepository.getAllMoviesInHistory() }
    }

    @Test(expected = RuntimeException::class)
    fun `invoke SHOULD throw exception when repository fails`() = runTest {
        coEvery { movieRepository.getAllMoviesInHistory() } throws RuntimeException("Network error")

        useCase.invoke()
    }

    @Test(expected = IllegalStateException::class)
    fun `invoke SHOULD throw exception when repository throws IllegalStateException`() = runTest {
        coEvery { movieRepository.getAllMoviesInHistory() } throws IllegalStateException("Database error")

        useCase.invoke()
    }

    @Test
    fun `invoke SHOULD call repository method exactly once`() = runTest {
        coEvery { movieRepository.getAllMoviesInHistory() } returns testMovies

        useCase.invoke()

        coVerify(exactly = 1) { movieRepository.getAllMoviesInHistory() }
    }

    private companion object {
        val testMovies = listOf(
            Movie(
                id = 1,
                title = "Test Movie 1",
                imageUrl = "/test_poster_1.jpg",
                rate = 8.5,
                releaseDate = "2023-01-01",
                movieDuration = "120 min",
                description = "Test movie overview 1",
                genre = listOf()
            ),
            Movie(
                id = 2,
                title = "Test Movie 2",
                imageUrl = "/test_poster_2.jpg",
                rate = 7.8,
                releaseDate = "2023-02-01",
                movieDuration = "105 min",
                description = "Test movie overview 2",
                genre = listOf()
            )
        )
    }
}