package com.madrid.domain.usecase.movie

import com.google.common.truth.Truth
import com.madrid.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AddMovieToHistoryUseCaseTest {
    private val movieRepository: MovieRepository = mockk(relaxed = true)
    private lateinit var useCase: AddMovieToHistoryUseCase

    @Before
    fun setUp() {
        useCase = AddMovieToHistoryUseCase(movieRepository)
    }

    @Test
    fun `invoke SHOULD add movie to history successfully`() = runTest {
        coEvery { movieRepository.addMovieToHistory(123) } returns Unit

        val result = useCase.invoke(123)

        Truth.assertThat(result).isEqualTo(Unit)
        coVerify(exactly = 1) { movieRepository.addMovieToHistory(123) }
    }

    @Test
    fun `invoke SHOULD add correct movie for different movie id`() = runTest {
        coEvery { movieRepository.addMovieToHistory(456) } returns Unit

        val result = useCase.invoke(456)

        Truth.assertThat(result).isEqualTo(Unit)
        coVerify(exactly = 1) { movieRepository.addMovieToHistory(456) }
    }

    @Test(expected = RuntimeException::class)
    fun `invoke SHOULD throw exception when repository fails`() = runTest {
        coEvery { movieRepository.addMovieToHistory(123) } throws RuntimeException("Database error")

        useCase.invoke(123)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `invoke SHOULD throw exception when repository throws IllegalArgumentException`() =
        runTest {
            coEvery { movieRepository.addMovieToHistory(-1) } throws IllegalArgumentException("Invalid movie ID")

            useCase.invoke(-1)
        }

    @Test
    fun `invoke SHOULD call repository with correct movie id`() = runTest {
        coEvery { movieRepository.addMovieToHistory(999) } returns Unit

        useCase.invoke(999)

        coVerify(exactly = 1) { movieRepository.addMovieToHistory(999) }
    }
}