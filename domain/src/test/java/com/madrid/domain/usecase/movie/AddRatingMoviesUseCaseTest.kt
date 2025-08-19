package com.madrid.domain.usecase.movie

import com.madrid.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AddRatingMoviesUseCaseTest {
    private val movieRepository: MovieRepository = mockk(relaxed = true)
    private lateinit var useCase: AddRatingMoviesUseCase

    @Before
    fun setUp() {
        useCase = AddRatingMoviesUseCase(movieRepository)
    }

    @Test
    fun `should call repository with correct parameters`() = runTest {
        val movieId = 123
        val rating = 4.5

        useCase.invoke(movieId, rating)

        coVerify(exactly = 1) { movieRepository.addRatingMovie(movieId, rating) }
    }

    @Test
    fun `should handle different movie ids and ratings`() = runTest {
        val movieId = 456
        val rating = 3.0

        useCase.invoke(movieId, rating)

        coVerify(exactly = 1) { movieRepository.addRatingMovie(movieId, rating) }
    }

    @Test
    fun `should handle minimum rating value`() = runTest {
        val movieId = 789
        val rating = 0.0

        useCase.invoke(movieId, rating)

        coVerify(exactly = 1) { movieRepository.addRatingMovie(movieId, rating) }
    }

    @Test
    fun `should handle maximum rating value`() = runTest {
        val movieId = 321
        val rating = 5.0

        useCase.invoke(movieId, rating)

        coVerify(exactly = 1) { movieRepository.addRatingMovie(movieId, rating) }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        val movieId = 123
        val rating = 4.0
        coEvery {
            movieRepository.addRatingMovie(
                movieId,
                rating
            )
        } throws RuntimeException("Network error")

        useCase.invoke(movieId, rating)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should throw exception when repository throws IllegalArgumentException`() =
        runTest {
            val movieId = -1
            val rating = 4.0
            coEvery {
                movieRepository.addRatingMovie(
                    movieId,
                    rating
                )
            } throws IllegalArgumentException("Invalid movie ID")

            useCase.invoke(movieId, rating)
        }

    @Test
    fun `should call repository exactly once per invocation`() = runTest {
        val movieId = 555
        val rating = 2.5

        useCase.invoke(movieId, rating)
        useCase.invoke(movieId, rating)

        coVerify(exactly = 2) { movieRepository.addRatingMovie(movieId, rating) }
    }
}