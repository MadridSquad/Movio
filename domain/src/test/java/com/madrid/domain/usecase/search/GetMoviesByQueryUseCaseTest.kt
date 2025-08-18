package com.madrid.domain.usecase.search

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.entity.Genre
import com.madrid.domain.entity.Movie
import com.madrid.domain.repository.MovieRepository
import com.madrid.domain.repository.SearchRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetMoviesByQueryUseCaseTest {
    private val movieRepository: MovieRepository = mockk(relaxed = true)
    private val searchRepository: SearchRepository = mockk(relaxed = true)
    private lateinit var useCase: GetMoviesByQueryUseCase

    @Before
    fun setUp() {
        useCase = GetMoviesByQueryUseCase(movieRepository, searchRepository)
    }

    @Test
    fun `should call repositories and return sorted movies by interest points`() = runTest {
        val query = "Batman"
        val genres = listOf(
            Genre(id = 1, name = "Action", interestPoints = 100),
            Genre(id = 2, name = "Drama", interestPoints = 50)
        )
        val movies = listOf(
            Movie(
                id = 1,
                title = "Movie1",
                imageUrl = "",
                rate = 0.0,
                releaseDate = "",
                movieDuration = "",
                description = "",
                genre = listOf(Genre(id = 1, name = "Drama", interestPoints = 0))
            ),
            Movie(
                id = 2,
                title = "Movie2",
                imageUrl = "",
                rate = 0.0,
                releaseDate = "",
                movieDuration = "",
                description = "",
                genre = listOf(Genre(id = 2, name = "Action", interestPoints = 0))
            )
        )
        coEvery { movieRepository.getMoviesGenres() } returns genres
        coEvery { searchRepository.getMoviesByQuery(query, 1) } returns movies

        val result = useCase.invoke(query)

        assertThat(result).isEqualTo(listOf(movies[1], movies[0]))
        coVerify(exactly = 1) { movieRepository.getMoviesGenres() }
        coVerify(exactly = 1) { searchRepository.getMoviesByQuery(query, 1) }
    }

    @Test
    fun `should call repositories with specific page number`() = runTest {
        val query = "Spider-Man"
        val page = 3
        val genres = listOf(Genre(id = 1, name = "Action", interestPoints = 75))
        val movies = listOf(
            Movie(
                id = 1,
                title = "Movie1",
                imageUrl = "",
                rate = 0.0,
                releaseDate = "",
                movieDuration = "",
                description = "",
                genre = emptyList()
            )
        )
        coEvery { movieRepository.getMoviesGenres() } returns genres
        coEvery { searchRepository.getMoviesByQuery(query, page) } returns movies

        val result = useCase.invoke(query, page)

        assertThat(result).isEqualTo(movies)
        coVerify(exactly = 1) { movieRepository.getMoviesGenres() }
        coVerify(exactly = 1) { searchRepository.getMoviesByQuery(query, page) }
    }

    @Test
    fun `should handle empty movies list`() = runTest {
        val query = "Unknown"
        val genres = listOf(Genre(id = 1, name = "Comedy", interestPoints = 80))
        coEvery { movieRepository.getMoviesGenres() } returns genres
        coEvery { searchRepository.getMoviesByQuery(query, 1) } returns emptyList()

        val result = useCase.invoke(query)

        assertThat(result).isEmpty()
        coVerify(exactly = 1) { movieRepository.getMoviesGenres() }
        coVerify(exactly = 1) { searchRepository.getMoviesByQuery(query, 1) }
    }

    @Test
    fun `should handle movies with unknown genres`() = runTest {
        val query = "Horror"
        val genres = listOf(Genre(id = 1, name = "Action", interestPoints = 90))
        val movies = listOf(
            Movie(
                id = 1,
                title = "Movie1",
                imageUrl = "",
                rate = 0.0,
                releaseDate = "",
                movieDuration = "",
                description = "",
                genre = listOf(Genre(id = 1, name = "Unknown", interestPoints = 0))
            )
        )
        coEvery { movieRepository.getMoviesGenres() } returns genres
        coEvery { searchRepository.getMoviesByQuery(query, 1) } returns movies

        val result = useCase.invoke(query)

        assertThat(result).isEqualTo(movies)
        coVerify(exactly = 1) { movieRepository.getMoviesGenres() }
        coVerify(exactly = 1) { searchRepository.getMoviesByQuery(query, 1) }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when movieRepository fails`() = runTest {
        val query = "Test"
        coEvery { movieRepository.getMoviesGenres() } throws RuntimeException("Repository error")

        useCase.invoke(query)
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when searchRepository fails`() = runTest {
        val query = "Test"
        val genres = listOf(Genre(id = 1, name = "Action", interestPoints = 100))
        coEvery { movieRepository.getMoviesGenres() } returns genres
        coEvery {
            searchRepository.getMoviesByQuery(
                query,
                1
            )
        } throws RuntimeException("Search error")

        useCase.invoke(query)
    }
}