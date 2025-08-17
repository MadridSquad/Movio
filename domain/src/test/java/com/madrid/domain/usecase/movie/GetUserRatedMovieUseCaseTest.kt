package com.madrid.domain.usecase.movie

import com.google.common.truth.Truth
import com.madrid.domain.entity.Genre
import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Trailer
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
class GetUserRatedMovieUseCaseTest {
    private val movieRepository: MovieRepository = mockk(relaxed = true)
    private val authenticationRepository: AuthenticationRepository = mockk(relaxed = true)
    private lateinit var useCase: GetUserRatedMovieUseCase

    @Before
    fun setUp() {
        useCase = GetUserRatedMovieUseCase(movieRepository, authenticationRepository)
    }

    @Test
    fun `invoke SHOULD return user rated movies list from repository`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("test_session_id")
        coEvery { movieRepository.getUserMovieRate("test_session_id") } returns testRatedMovies

        val result = useCase.invoke()

        Truth.assertThat(result).isEqualTo(testRatedMovies)
        coVerify(exactly = 1) { authenticationRepository.getSessionId() }
        coVerify(exactly = 1) { movieRepository.getUserMovieRate("test_session_id") }
    }

    @Test
    fun `invoke SHOULD return empty list when repository returns empty list`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("test_session_id")
        coEvery { movieRepository.getUserMovieRate("test_session_id") } returns emptyList()

        val result = useCase.invoke()

        Truth.assertThat(result).isEmpty()
        coVerify(exactly = 1) { authenticationRepository.getSessionId() }
        coVerify(exactly = 1) { movieRepository.getUserMovieRate("test_session_id") }
    }

    @Test
    fun `invoke SHOULD return single rated movie when repository returns single movie`() = runTest {
        val singleRatedMovie = listOf(testRatedMovies.first())
        coEvery { authenticationRepository.getSessionId() } returns flowOf("test_session_id")
        coEvery { movieRepository.getUserMovieRate("test_session_id") } returns singleRatedMovie

        val result = useCase.invoke()

        Truth.assertThat(result).hasSize(1)
        Truth.assertThat(result).isEqualTo(singleRatedMovie)
        coVerify(exactly = 1) { authenticationRepository.getSessionId() }
        coVerify(exactly = 1) { movieRepository.getUserMovieRate("test_session_id") }
    }

    @Test
    fun `invoke SHOULD use correct session id from authentication repository`() = runTest {
        val customSessionId = "custom_session_123"
        coEvery { authenticationRepository.getSessionId() } returns flowOf(customSessionId)
        coEvery { movieRepository.getUserMovieRate(customSessionId) } returns testRatedMovies

        val result = useCase.invoke()

        Truth.assertThat(result).isEqualTo(testRatedMovies)
        coVerify(exactly = 1) { authenticationRepository.getSessionId() }
        coVerify(exactly = 1) { movieRepository.getUserMovieRate(customSessionId) }
    }

    @Test(expected = RuntimeException::class)
    fun `invoke SHOULD throw exception when movie repository fails`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("test_session_id")
        coEvery { movieRepository.getUserMovieRate("test_session_id") } throws RuntimeException("Network error")

        useCase.invoke()
    }

    @Test(expected = RuntimeException::class)
    fun `invoke SHOULD throw exception when authentication repository fails`() = runTest {
        coEvery { authenticationRepository.getSessionId() } throws RuntimeException("Authentication error")

        useCase.invoke()
    }

    @Test(expected = IllegalStateException::class)
    fun `invoke SHOULD throw exception when session id is empty`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("")
        coEvery { movieRepository.getUserMovieRate("") } throws IllegalStateException("Empty session ID")

        useCase.invoke()
    }

    private companion object {
        val testRatedMovies = listOf(
            GetUserRatedMovieUseCase.RatedMovie(
                rate = 8.5,
                movie = Movie(
                    id = 123,
                    title = "Rated Movie 1",
                    imageUrl = "/rated_poster1.jpg",
                    rate = 8.0,
                    releaseDate = "2023-01-01",
                    movieDuration = "120 min",
                    description = "User rated movie description 1",
                    genre = listOf(Genre(id = 1, name = "Action")),
                    trailer = Trailer(key = "", id = "")
                )
            ),
            GetUserRatedMovieUseCase.RatedMovie(
                rate = 7.0,
                movie = Movie(
                    id = 124,
                    title = "Rated Movie 2",
                    imageUrl = "/rated_poster2.jpg",
                    rate = 7.5,
                    releaseDate = "2023-01-02",
                    movieDuration = "110 min",
                    description = "User rated movie description 2",
                    genre = listOf(Genre(id = 2, name = "Drama")),
                    trailer = Trailer(key = "", id = "")
                )
            )
        )
    }
}