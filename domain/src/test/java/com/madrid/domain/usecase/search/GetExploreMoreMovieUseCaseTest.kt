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

class GetExploreMoreMovieUseCaseTest {
    private val movieRepository: MovieRepository = mockk(relaxed = true)
    private lateinit var useCase: GetExploreMoreMovieUseCase

    @Before
    fun setUp() {
        useCase = GetExploreMoreMovieUseCase(movieRepository)
    }

    @Test
    fun `should call repository getExploreMoreMovies with page 1`() = runTest {
        val page = 1
        val expectedResult = emptyList<Movie>()
        coEvery { movieRepository.getExploreMoreMovies(page) } returns expectedResult

        val result = useCase.invoke(page)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { movieRepository.getExploreMoreMovies(page) }
    }

    @Test
    fun `should call repository getExploreMoreMovies with different page`() = runTest {
        val page = 5
        val expectedResult = emptyList<Movie>()
        coEvery { movieRepository.getExploreMoreMovies(page) } returns expectedResult

        val result = useCase.invoke(page)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { movieRepository.getExploreMoreMovies(page) }
    }

    @Test
    fun `should call repository getExploreMoreMovies with large page number`() = runTest {
        val page = 1000
        val expectedResult = emptyList<Movie>()
        coEvery { movieRepository.getExploreMoreMovies(page) } returns expectedResult

        val result = useCase.invoke(page)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { movieRepository.getExploreMoreMovies(page) }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        val page = 1
        coEvery { movieRepository.getExploreMoreMovies(page) } throws RuntimeException("Movie error")

        useCase.invoke(page)
    }

    @Test
    fun `should return repository result when successful`() = runTest {
        val page = 2
        val expectedResult = emptyList<Movie>()
        coEvery { movieRepository.getExploreMoreMovies(page) } returns expectedResult

        val result = useCase.invoke(page)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { movieRepository.getExploreMoreMovies(page) }
    }

    @Test
    fun `should handle zero page number`() = runTest {
        val page = 0
        val expectedResult = emptyList<Movie>()
        coEvery { movieRepository.getExploreMoreMovies(page) } returns expectedResult

        val result = useCase.invoke(page)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { movieRepository.getExploreMoreMovies(page) }
    }
}