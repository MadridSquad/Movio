package com.madrid.domain.usecase.movie

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.entity.Genre
import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.SortType
import com.madrid.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetMoviesByGenreIdUseCaseTest {
    private val movieRepository: MovieRepository = mockk(relaxed = true)
    private lateinit var useCase: GetMoviesByGenreIdUseCase

    @Before
    fun setUp() {
        useCase = GetMoviesByGenreIdUseCase(movieRepository)
    }

    @Test
    fun `should return movies list from repository`() = runTest {
        coEvery {
            movieRepository.getMoviesByGenreId(
                1,
                123,
                SortType.POPULARITY
            )
        } returns testMovies

        val result = useCase.invoke(1, 123, SortType.POPULARITY)

        assertThat(result).isEqualTo(testMovies)
        coVerify(exactly = 1) { movieRepository.getMoviesByGenreId(1, 123, SortType.POPULARITY) }
    }

    @Test
    fun `should return movies with null genre id`() = runTest {
        coEvery {
            movieRepository.getMoviesByGenreId(
                1,
                null,
                SortType.POPULARITY
            )
        } returns testMovies

        val result = useCase.invoke(1, null, SortType.POPULARITY)

        assertThat(result).isEqualTo(testMovies)
        coVerify(exactly = 1) { movieRepository.getMoviesByGenreId(1, null, SortType.POPULARITY) }
    }

    @Test
    fun `should return movies with different sort type`() = runTest {
        coEvery { movieRepository.getMoviesByGenreId(1, 123, SortType.LATEST) } returns testMovies
        val result = useCase.invoke(1, 123, SortType.LATEST)
        assertThat(result).isEqualTo(testMovies)
        coVerify(exactly = 1) { movieRepository.getMoviesByGenreId(1, 123, SortType.LATEST) }
    }

    @Test
    fun `should return movies for different page`() = runTest {
        coEvery {
            movieRepository.getMoviesByGenreId(
                2,
                123,
                SortType.POPULARITY
            )
        } returns testMovies

        val result = useCase.invoke(2, 123, SortType.POPULARITY)

        assertThat(result).isEqualTo(testMovies)
        coVerify(exactly = 1) { movieRepository.getMoviesByGenreId(2, 123, SortType.POPULARITY) }
    }

    @Test
    fun `should return empty list when repository returns empty list`() = runTest {
        coEvery {
            movieRepository.getMoviesByGenreId(
                1,
                123,
                SortType.POPULARITY
            )
        } returns emptyList()

        val result = useCase.invoke(1, 123, SortType.POPULARITY)

        assertThat(result).isEmpty()
        coVerify(exactly = 1) { movieRepository.getMoviesByGenreId(1, 123, SortType.POPULARITY) }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        coEvery {
            movieRepository.getMoviesByGenreId(
                1,
                123,
                SortType.POPULARITY
            )
        } throws RuntimeException("Network error")

        useCase.invoke(1, 123, SortType.POPULARITY)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should throw exception when repository throws IllegalArgumentException`() =
        runTest {
            coEvery {
                movieRepository.getMoviesByGenreId(
                    -1,
                    123,
                    SortType.POPULARITY
                )
            } throws IllegalArgumentException("Invalid page number")

            useCase.invoke(-1, 123, SortType.POPULARITY)
        }

    @Test
    fun `should call repository with correct parameters`() = runTest {
        coEvery { movieRepository.getMoviesByGenreId(5, 999, SortType.ALL) } returns testMovies

        useCase.invoke(5, 999, SortType.ALL)

        coVerify(exactly = 1) { movieRepository.getMoviesByGenreId(5, 999, SortType.ALL) }
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
                genre = listOf(Genre(id = 1, name = "Action"))
            )
        )
    }
}