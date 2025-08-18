package com.madrid.domain.usecase.movie

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.entity.Movie
import com.madrid.domain.repository.AuthenticationRepository
import com.madrid.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@Suppress("UnusedFlow")
class GetFavoriteMoviesUseCaseTest {
    private val movieRepository: MovieRepository = mockk(relaxed = true)
    private val authenticationRepository: AuthenticationRepository = mockk(relaxed = true)
    private lateinit var useCase: GetFavoriteMoviesUseCase

    @Before
    fun setUp() {
        useCase = GetFavoriteMoviesUseCase(movieRepository, authenticationRepository)
    }

    @Test
    fun `should return favorite movies from repository`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("session123")
        coEvery { movieRepository.getFavoriteMovies("session123") } returns listOf(testMovie)

        val result = useCase.invoke()

        assertThat(result).isEqualTo(listOf(testMovie))
        coVerify(exactly = 1) { authenticationRepository.getSessionId() }
        coVerify(exactly = 1) { movieRepository.getFavoriteMovies("session123") }
    }

    @Test
    fun `should return empty list when no favorite movies exist`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("session123")
        coEvery { movieRepository.getFavoriteMovies("session123") } returns emptyList()

        val result = useCase.invoke()

        assertThat(result).isEmpty()
        coVerify(exactly = 1) { movieRepository.getFavoriteMovies("session123") }
    }

    @Test
    fun `should return multiple favorite movies`() = runTest {
        val favoriteMovies = listOf(testMovie, testMovie.copy(id = 456, title = "Another Movie"))
        coEvery { authenticationRepository.getSessionId() } returns flowOf("session123")
        coEvery { movieRepository.getFavoriteMovies("session123") } returns favoriteMovies

        val result = useCase.invoke()

        assertThat(result).hasSize(2)
        assertThat(result).isEqualTo(favoriteMovies)
    }

    @Test
    fun `should use correct session id from authentication repository`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("different_session")
        coEvery { movieRepository.getFavoriteMovies("different_session") } returns listOf(testMovie)

        useCase.invoke()

        coVerify(exactly = 1) { movieRepository.getFavoriteMovies("different_session") }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when authentication repository fails`() = runTest {
        coEvery { authenticationRepository.getSessionId() } throws RuntimeException("Auth error")

        useCase.invoke()
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when movie repository fails`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("session123")
        coEvery { movieRepository.getFavoriteMovies("session123") } throws RuntimeException("Network error")

        useCase.invoke()
    }

    @Test(expected = IllegalStateException::class)
    fun `should throw exception when session id is invalid`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("")
        coEvery { movieRepository.getFavoriteMovies("") } throws IllegalStateException("Invalid session")

        useCase.invoke()
    }

    private companion object {
        val testMovie = Movie(
            id = 123,
            title = "Test Movie",
            imageUrl = "/test_poster.jpg",
            rate = 8.5,
            releaseDate = "2023-01-01",
            movieDuration = "120 min",
            description = "A test movie overview",
            genre = emptyList()
        )
    }
}