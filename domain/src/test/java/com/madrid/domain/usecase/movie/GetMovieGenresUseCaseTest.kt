package com.madrid.domain.usecase.movie

import com.google.common.truth.Truth
import com.madrid.domain.entity.Genre
import com.madrid.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetMovieGenresUseCaseTest {
    private val movieRepository: MovieRepository = mockk(relaxed = true)
    private lateinit var useCase: GetMovieGenresUseCase

    @Before
    fun setUp() {
        useCase = GetMovieGenresUseCase(movieRepository)
    }

    @Test
    fun `invoke SHOULD return genres list from repository`() = runTest {
        coEvery { movieRepository.getMoviesGenres() } returns testGenres

        val result = useCase.invoke()

        Truth.assertThat(result).isEqualTo(testGenres)
        coVerify(exactly = 1) { movieRepository.getMoviesGenres() }
    }

    @Test
    fun `invoke SHOULD return empty list when repository returns empty list`() = runTest {
        coEvery { movieRepository.getMoviesGenres() } returns emptyList()

        val result = useCase.invoke()

        Truth.assertThat(result).isEmpty()
        coVerify(exactly = 1) { movieRepository.getMoviesGenres() }
    }

    @Test
    fun `invoke SHOULD return single genre when repository returns single genre`() = runTest {
        val singleGenre = listOf(Genre(id = 1, name = "Action"))
        coEvery { movieRepository.getMoviesGenres() } returns singleGenre

        val result = useCase.invoke()

        Truth.assertThat(result).hasSize(1)
        Truth.assertThat(result).isEqualTo(singleGenre)
        coVerify(exactly = 1) { movieRepository.getMoviesGenres() }
    }

    @Test(expected = RuntimeException::class)
    fun `invoke SHOULD throw exception when repository fails`() = runTest {
        coEvery { movieRepository.getMoviesGenres() } throws RuntimeException("Network error")

        useCase.invoke()
    }

    @Test(expected = IllegalStateException::class)
    fun `invoke SHOULD throw exception when repository throws IllegalStateException`() = runTest {
        coEvery { movieRepository.getMoviesGenres() } throws IllegalStateException("Repository error")

        useCase.invoke()
    }

    @Test
    fun `invoke SHOULD call repository method exactly once`() = runTest {
        coEvery { movieRepository.getMoviesGenres() } returns testGenres

        useCase.invoke()

        coVerify(exactly = 1) { movieRepository.getMoviesGenres() }
    }

    private companion object {
        val testGenres = listOf(
            Genre(id = 1, name = "Action"),
            Genre(id = 2, name = "Drama"),
            Genre(id = 3, name = "Comedy"),
            Genre(id = 4, name = "Thriller")
        )
    }
}