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

class GetMovieDetailsUseCaseTest {
    private val movieRepository: MovieRepository = mockk(relaxed = true)
    private lateinit var useCase: GetMovieDetailsUseCase

    @Before
    fun setUp() {
        useCase = GetMovieDetailsUseCase(movieRepository)
    }

    @Test
    fun `invoke SHOULD return movie details from repository`() = runTest {
        coEvery { movieRepository.getMovieDetailsById(123) } returns testMovie

        val result = useCase.invoke(123)

        Truth.assertThat(result).isEqualTo(testMovie)
        coVerify(exactly = 1) { movieRepository.getMovieDetailsById(123) }
    }

    @Test
    fun `invoke SHOULD increase genre interest points for each genre`() = runTest {
        coEvery { movieRepository.getMovieDetailsById(123) } returns testMovie

        useCase.invoke(123)

        coVerify(exactly = 1) { movieRepository.increaseMovieGenreInterestPoints("Action") }
        coVerify(exactly = 1) { movieRepository.increaseMovieGenreInterestPoints("Drama") }
    }

    @Test
    fun `invoke SHOULD return correct movie for different movie id`() = runTest {
        val anotherMovie = testMovie.copy(id = 456, title = "Another Movie")
        coEvery { movieRepository.getMovieDetailsById(456) } returns anotherMovie

        val result = useCase.invoke(456)

        Truth.assertThat(result).isEqualTo(anotherMovie)
        coVerify(exactly = 1) { movieRepository.getMovieDetailsById(456) }
    }

    @Test(expected = RuntimeException::class)
    fun `invoke SHOULD throw exception when repository fails`() = runTest {
        coEvery { movieRepository.getMovieDetailsById(123) } throws RuntimeException("Network error")

        useCase.invoke(123)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `invoke SHOULD throw exception when repository throws IllegalArgumentException`() =
        runTest {
            coEvery { movieRepository.getMovieDetailsById(-1) } throws IllegalArgumentException("Invalid movie ID")

            useCase.invoke(-1)
        }

    @Test
    fun `invoke SHOULD call repository with correct movie id`() = runTest {
        coEvery { movieRepository.getMovieDetailsById(999) } returns testMovie

        useCase.invoke(999)

        coVerify(exactly = 1) { movieRepository.getMovieDetailsById(999) }
    }

    @Test
    fun `invoke SHOULD handle movie with empty genres list`() = runTest {
        val movieWithoutGenres = testMovie.copy(genre = emptyList())
        coEvery { movieRepository.getMovieDetailsById(123) } returns movieWithoutGenres

        val result = useCase.invoke(123)

        Truth.assertThat(result).isEqualTo(movieWithoutGenres)
        coVerify(exactly = 0) { movieRepository.increaseMovieGenreInterestPoints(any()) }
    }

    private companion object {
        val testMovie = Movie(
            id = 123,
            title = "Test Movie",
            imageUrl = "/test_poster.jpg",
            rate = 8.5,
            releaseDate = "2023-01-01",
            movieDuration = "120 min",
            description = "Test movie description",
            genre = listOf(Genre(id = 1, name = "Action"), Genre(id = 2, name = "Drama"))
        )
    }
}