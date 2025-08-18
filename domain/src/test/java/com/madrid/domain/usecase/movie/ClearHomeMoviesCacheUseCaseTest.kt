package com.madrid.domain.usecase.movie

import com.madrid.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ClearHomeMoviesCacheUseCaseTest {
    private val movieRepository: MovieRepository = mockk(relaxed = true)
    private lateinit var useCase: ClearHomeMoviesCacheUseCase

    @Before
    fun setUp() {
        useCase = ClearHomeMoviesCacheUseCase(movieRepository)
    }

    @Test
    fun `invoke SHOULD call repository clearHomeMoviesCache method`() = runTest {
        useCase.invoke()

        coVerify(exactly = 1) { movieRepository.clearHomeMoviesCache() }
    }

    @Test
    fun `invoke SHOULD complete successfully when repository succeeds`() = runTest {
        coEvery { movieRepository.clearHomeMoviesCache() } returns Unit

        useCase.invoke()

        coVerify(exactly = 1) { movieRepository.clearHomeMoviesCache() }
    }

    @Test(expected = RuntimeException::class)
    fun `invoke SHOULD throw exception when repository fails`() = runTest {
        coEvery { movieRepository.clearHomeMoviesCache() } throws RuntimeException("Cache clear failed")

        useCase.invoke()
    }

    @Test(expected = IllegalStateException::class)
    fun `invoke SHOULD throw exception when repository throws IllegalStateException`() = runTest {
        coEvery { movieRepository.clearHomeMoviesCache() } throws IllegalStateException("Invalid cache state")

        useCase.invoke()
    }

    @Test
    fun `invoke SHOULD call repository method only once per invocation`() = runTest {
        useCase.invoke()
        useCase.invoke()

        coVerify(exactly = 2) { movieRepository.clearHomeMoviesCache() }
    }
}