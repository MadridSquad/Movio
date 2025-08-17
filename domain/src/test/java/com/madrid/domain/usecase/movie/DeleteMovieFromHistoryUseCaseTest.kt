package com.madrid.domain.usecase.movie

import com.madrid.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteMovieFromHistoryUseCaseTest {
    private val movieRepository: MovieRepository = mockk(relaxed = true)
    private lateinit var useCase: DeleteMovieFromHistoryUseCase

    @Before
    fun setUp() {
        useCase = DeleteMovieFromHistoryUseCase(movieRepository)
    }

    @Test
    fun `invoke SHOULD call repository deleteMovieFromHistory with correct movieId`() = runTest {
        coEvery { movieRepository.deleteMovieFromHistory(123) } returns Unit

        useCase.invoke(123)

        coVerify(exactly = 1) { movieRepository.deleteMovieFromHistory(movieId = 123) }
    }

    @Test
    fun `invoke SHOULD call repository with different movie id`() = runTest {
        coEvery { movieRepository.deleteMovieFromHistory(456) } returns Unit

        useCase.invoke(456)

        coVerify(exactly = 1) { movieRepository.deleteMovieFromHistory(movieId = 456) }
    }

    @Test(expected = RuntimeException::class)
    fun `invoke SHOULD throw exception when repository fails`() = runTest {
        coEvery { movieRepository.deleteMovieFromHistory(123) } throws RuntimeException("Database error")

        useCase.invoke(123)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `invoke SHOULD throw exception when repository throws IllegalArgumentException`() =
        runTest {
            coEvery { movieRepository.deleteMovieFromHistory(-1) } throws IllegalArgumentException("Invalid movie ID")

            useCase.invoke(-1)
        }

    @Test
    fun `invoke SHOULD call repository with zero movieId`() = runTest {
        coEvery { movieRepository.deleteMovieFromHistory(0) } returns Unit

        useCase.invoke(0)

        coVerify(exactly = 1) { movieRepository.deleteMovieFromHistory(movieId = 0) }
    }

    @Test
    fun `invoke SHOULD call repository with large movieId`() = runTest {
        val largeMovieId = 999999
        coEvery { movieRepository.deleteMovieFromHistory(largeMovieId) } returns Unit

        useCase.invoke(largeMovieId)

        coVerify(exactly = 1) { movieRepository.deleteMovieFromHistory(movieId = largeMovieId) }
    }
}