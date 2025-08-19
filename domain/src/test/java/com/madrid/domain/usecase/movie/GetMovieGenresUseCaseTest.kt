package com.madrid.domain.usecase.movie

import com.google.common.truth.Truth.assertThat
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
    fun `should return genres list from repository`() = runTest {
        coEvery { movieRepository.getMoviesGenres() } returns testGenres

        val result = useCase.invoke()

        assertThat(result).isEqualTo(testGenres)
        coVerify(exactly = 1) { movieRepository.getMoviesGenres() }
    }

    @Test
    fun `should return empty list when repository returns empty list`() = runTest {
        coEvery { movieRepository.getMoviesGenres() } returns emptyList()

        val result = useCase.invoke()

        assertThat(result).isEmpty()
        coVerify(exactly = 1) { movieRepository.getMoviesGenres() }
    }

    @Test
    fun `should return single genre when repository returns single genre`() = runTest {
        val singleGenre = listOf(Genre(id = 1, name = "Action"))
        coEvery { movieRepository.getMoviesGenres() } returns singleGenre

        val result = useCase.invoke()

        assertThat(result).hasSize(1)
        assertThat(result).isEqualTo(singleGenre)
        coVerify(exactly = 1) { movieRepository.getMoviesGenres() }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        coEvery { movieRepository.getMoviesGenres() } throws RuntimeException("Network error")

        useCase.invoke()
    }

    @Test(expected = IllegalStateException::class)
    fun `should throw exception when repository throws IllegalStateException`() = runTest {
        coEvery { movieRepository.getMoviesGenres() } throws IllegalStateException("Repository error")

        useCase.invoke()
    }

    @Test
    fun `should call repository method exactly once`() = runTest {
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