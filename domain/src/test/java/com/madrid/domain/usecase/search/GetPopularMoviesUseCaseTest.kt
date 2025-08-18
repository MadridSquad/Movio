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

class GetPopularMoviesUseCaseTest {
    private val movieRepository: MovieRepository = mockk(relaxed = true)
    private lateinit var useCase: GetPopularMoviesUseCase

    @Before
    fun setUp() {
        useCase = GetPopularMoviesUseCase(movieRepository)
    }

    @Test
    fun `should call repository getPopularMovies with default page`() = runTest {
        val expectedResult = emptyList<Movie>()
        coEvery { movieRepository.getPopularMovies(1) } returns expectedResult

        val result = useCase.invoke()

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { movieRepository.getPopularMovies(1) }
    }

    @Test
    fun `should call repository getPopularMovies with specific page`() = runTest {
        val page = 2
        val expectedResult = emptyList<Movie>()
        coEvery { movieRepository.getPopularMovies(page) } returns expectedResult

        val result = useCase.invoke(page)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { movieRepository.getPopularMovies(page) }
    }

    @Test
    fun `should call repository getPopularMovies with large page number`() = runTest {
        val page = 100
        val expectedResult = emptyList<Movie>()
        coEvery { movieRepository.getPopularMovies(page) } returns expectedResult

        val result = useCase.invoke(page)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { movieRepository.getPopularMovies(page) }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        coEvery { movieRepository.getPopularMovies(1) } throws RuntimeException("Popular movies error")

        useCase.invoke()
    }

    @Test
    fun `should return repository result when successful`() = runTest {
        val page = 3
        val expectedResult = emptyList<Movie>()
        coEvery { movieRepository.getPopularMovies(page) } returns expectedResult

        val result = useCase.invoke(page)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { movieRepository.getPopularMovies(page) }
    }

    @Test
    fun `should handle zero page number`() = runTest {
        val page = 0
        val expectedResult = emptyList<Movie>()
        coEvery { movieRepository.getPopularMovies(page) } returns expectedResult

        val result = useCase.invoke(page)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { movieRepository.getPopularMovies(page) }
    }
}