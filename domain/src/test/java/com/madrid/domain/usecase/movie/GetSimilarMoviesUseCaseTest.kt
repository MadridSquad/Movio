package com.madrid.domain.usecase.movie

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.entity.Genre
import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Trailer
import com.madrid.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetSimilarMoviesUseCaseTest {
    private val movieRepository: MovieRepository = mockk(relaxed = true)
    private lateinit var useCase: GetSimilarMoviesUseCase

    @Before
    fun setUp() {
        useCase = GetSimilarMoviesUseCase(movieRepository)
    }

    @Test
    fun `should return similar movies list from repository`() = runTest {
        coEvery { movieRepository.getSimilarMoviesById(123) } returns testMovies

        val result = useCase.invoke(123)

        assertThat(result).isEqualTo(testMovies)
        coVerify(exactly = 1) { movieRepository.getSimilarMoviesById(123) }
    }

    @Test
    fun `should return correct movies for different movie id`() = runTest {
        val anotherMovies =
            listOf(testMovies.first().copy(id = 456, title = "Another Similar Movie"))
        coEvery { movieRepository.getSimilarMoviesById(456) } returns anotherMovies

        val result = useCase.invoke(456)

        assertThat(result).isEqualTo(anotherMovies)
        coVerify(exactly = 1) { movieRepository.getSimilarMoviesById(456) }
    }

    @Test
    fun `should return empty list when repository returns empty list`() = runTest {
        coEvery { movieRepository.getSimilarMoviesById(123) } returns emptyList()

        val result = useCase.invoke(123)

        assertThat(result).isEmpty()
        coVerify(exactly = 1) { movieRepository.getSimilarMoviesById(123) }
    }

    @Test
    fun `should return single movie when repository returns single movie`() = runTest {
        val singleMovie = listOf(testMovies.first())
        coEvery { movieRepository.getSimilarMoviesById(123) } returns singleMovie

        val result = useCase.invoke(123)

        assertThat(result).hasSize(1)
        assertThat(result).isEqualTo(singleMovie)
        coVerify(exactly = 1) { movieRepository.getSimilarMoviesById(123) }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        coEvery { movieRepository.getSimilarMoviesById(123) } throws RuntimeException("Network error")

        useCase.invoke(123)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should throw exception when repository throws IllegalArgumentException`() =
        runTest {
            coEvery { movieRepository.getSimilarMoviesById(-1) } throws IllegalArgumentException("Invalid movie ID")

            useCase.invoke(-1)
        }

    @Test
    fun `should call repository with correct movie id`() = runTest {
        coEvery { movieRepository.getSimilarMoviesById(999) } returns testMovies

        useCase.invoke(999)

        coVerify(exactly = 1) { movieRepository.getSimilarMoviesById(999) }
    }

    private companion object {
        val testMovies = listOf(
            Movie(
                id = 123,
                title = "Similar Movie 1",
                imageUrl = "/similar_poster1.jpg",
                rate = 8.5,
                releaseDate = "2023-01-01",
                movieDuration = "120 min",
                description = "Similar movie description 1",
                genre = listOf(Genre(id = 1, name = "Action")),
                trailer = Trailer(key = "", id = "")
            ),
            Movie(
                id = 124,
                title = "Similar Movie 2",
                imageUrl = "/similar_poster2.jpg",
                rate = 7.5,
                releaseDate = "2023-01-02",
                movieDuration = "105 min",
                description = "Similar movie description 2",
                genre = listOf(Genre(id = 2, name = "Drama")),
                trailer = Trailer(key = "", id = "")
            )
        )
    }
}