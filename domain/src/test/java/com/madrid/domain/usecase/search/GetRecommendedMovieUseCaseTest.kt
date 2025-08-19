package com.madrid.domain.usecase.search

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.entity.Movie
import com.madrid.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetRecommendedMovieUseCaseTest {
    private val movieRepository: MovieRepository = mockk(relaxed = true)
    private lateinit var useCase: GetRecommendedMovieUseCase

    @Before
    fun setUp() {
        useCase = GetRecommendedMovieUseCase(movieRepository)
    }

    @Test
    fun `should call repository getRecommendedMovies with page 1`() = runTest {
        val page = 1
        val expectedResult = emptyList<Movie>()
        coEvery { movieRepository.getRecommendedMovies(page) } returns expectedResult

        val result = useCase.invoke(page)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { movieRepository.getRecommendedMovies(page) }
    }

    @Test
    fun `should call repository getRecommendedMovies with different page`() = runTest {
        val page = 5
        val expectedResult = emptyList<Movie>()
        coEvery { movieRepository.getRecommendedMovies(page) } returns expectedResult

        val result = useCase.invoke(page)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { movieRepository.getRecommendedMovies(page) }
    }

    @Test
    fun `should call repository getRecommendedMovies with large page number`() = runTest {
        val page = 1000
        val expectedResult = emptyList<Movie>()
        coEvery { movieRepository.getRecommendedMovies(page) } returns expectedResult

        val result = useCase.invoke(page)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { movieRepository.getRecommendedMovies(page) }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        val page = 1
        coEvery { movieRepository.getRecommendedMovies(page) } throws RuntimeException("Recommended movies error")

        useCase.invoke(page)
    }

    @Test
    fun `should return repository result when successful`() = runTest {
        val page = 2
        val expectedResult = emptyList<Movie>()
        coEvery { movieRepository.getRecommendedMovies(page) } returns expectedResult

        val result = useCase.invoke(page)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { movieRepository.getRecommendedMovies(page) }
    }

    @Test
    fun `should handle zero page number`() = runTest {
        val page = 0
        val expectedResult = emptyList<Movie>()
        coEvery { movieRepository.getRecommendedMovies(page) } returns expectedResult

        val result = useCase.invoke(page)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { movieRepository.getRecommendedMovies(page) }
    }
}