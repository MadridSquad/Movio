package com.madrid.domain.usecase.movie

import com.google.common.truth.Truth
import com.madrid.domain.entity.Genre
import com.madrid.domain.entity.Movie
import com.madrid.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetMoviesByGenresUseCaseTest {
    private val movieRepository: MovieRepository = mockk(relaxed = true)
    private lateinit var useCase: GetMoviesByGenresUseCase

    @Before
    fun setUp() {
        useCase = GetMoviesByGenresUseCase(movieRepository)
    }

    @Test
    fun `invoke SHOULD return movies map from repository`() = runTest {
        coEvery { movieRepository.getMoviesByGenres() } returns testMoviesMap

        val result = useCase.invoke()

        Truth.assertThat(result).isEqualTo(testMoviesMap)
        coVerify(exactly = 1) { movieRepository.getMoviesByGenres() }
    }

    @Test
    fun `invoke SHOULD return empty map when repository returns empty map`() = runTest {
        coEvery { movieRepository.getMoviesByGenres() } returns emptyMap()

        val result = useCase.invoke()

        Truth.assertThat(result).isEmpty()
        coVerify(exactly = 1) { movieRepository.getMoviesByGenres() }
    }

    @Test
    fun `invoke SHOULD return single genre with movies when repository returns single entry`() =
        runTest {
            val singleGenreMap = mapOf("Action" to testMovies)
            coEvery { movieRepository.getMoviesByGenres() } returns singleGenreMap

            val result = useCase.invoke()

            Truth.assertThat(result).hasSize(1)
            Truth.assertThat(result).isEqualTo(singleGenreMap)
            coVerify(exactly = 1) { movieRepository.getMoviesByGenres() }
        }

    @Test
    fun `invoke SHOULD return map with empty movie lists when repository returns empty lists`() =
        runTest {
            val emptyMoviesMap = mapOf(
                "Action" to emptyList<Movie>(),
                "Drama" to emptyList()
            )
            coEvery { movieRepository.getMoviesByGenres() } returns emptyMoviesMap

            val result = useCase.invoke()

            Truth.assertThat(result).isEqualTo(emptyMoviesMap)
            Truth.assertThat(result["Action"]).isEmpty()
            Truth.assertThat(result["Drama"]).isEmpty()
            coVerify(exactly = 1) { movieRepository.getMoviesByGenres() }
        }

    @Test(expected = RuntimeException::class)
    fun `invoke SHOULD throw exception when repository fails`() = runTest {
        coEvery { movieRepository.getMoviesByGenres() } throws RuntimeException("Network error")

        useCase.invoke()
    }

    @Test(expected = IllegalStateException::class)
    fun `invoke SHOULD throw exception when repository throws IllegalStateException`() = runTest {
        coEvery { movieRepository.getMoviesByGenres() } throws IllegalStateException("Repository error")

        useCase.invoke()
    }

    @Test
    fun `invoke SHOULD call repository method exactly once`() = runTest {
        coEvery { movieRepository.getMoviesByGenres() } returns testMoviesMap

        useCase.invoke()

        coVerify(exactly = 1) { movieRepository.getMoviesByGenres() }
    }

    private companion object {
        val testMovies = listOf(
            Movie(
                id = 123,
                title = "Test Movie 1",
                imageUrl = "/test_poster1.jpg",
                rate = 8.5,
                releaseDate = "2023-01-01",
                movieDuration = "120 min",
                description = "Test movie description 1",
                genre = listOf(Genre(id = 1, name = "Action"))
            ),
            Movie(
                id = 124,
                title = "Test Movie 2",
                imageUrl = "/test_poster2.jpg",
                rate = 7.5,
                releaseDate = "2023-01-02",
                movieDuration = "110 min",
                description = "Test movie description 2",
                genre = listOf(Genre(id = 2, name = "Drama"))
            )
        )

        val testMoviesMap = mapOf(
            "Action" to listOf(testMovies[0]),
            "Drama" to listOf(testMovies[1]),
            "Comedy" to listOf(
                testMovies[0].copy(id = 125, title = "Comedy Movie")
            )
        )
    }
}