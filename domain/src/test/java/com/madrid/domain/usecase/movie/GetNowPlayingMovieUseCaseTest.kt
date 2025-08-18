package com.madrid.domain.usecase.movie

import com.google.common.truth.Truth
import com.madrid.domain.entity.Genre
import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Trailer
import com.madrid.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetNowPlayingMovieUseCaseTest {
    private val movieRepository: MovieRepository = mockk(relaxed = true)
    private lateinit var useCase: GetNowPlayingMovieUseCase

    @Before
    fun setUp() {
        useCase = GetNowPlayingMovieUseCase(movieRepository)
    }

    @Test
    fun `invoke SHOULD return movies list from repository`() = runTest {
        coEvery { movieRepository.getNowPlayingMovie(1) } returns testMovies

        val result = useCase.invoke(1)

        Truth.assertThat(result).isEqualTo(testMovies)
        coVerify(exactly = 1) { movieRepository.getNowPlayingMovie(1) }
    }

    @Test
    fun `invoke SHOULD return movies for different page`() = runTest {
        coEvery { movieRepository.getNowPlayingMovie(2) } returns testMovies

        val result = useCase.invoke(2)

        Truth.assertThat(result).isEqualTo(testMovies)
        coVerify(exactly = 1) { movieRepository.getNowPlayingMovie(2) }
    }

    @Test
    fun `invoke SHOULD return empty list when repository returns empty list`() = runTest {
        coEvery { movieRepository.getNowPlayingMovie(1) } returns emptyList()

        val result = useCase.invoke(1)

        Truth.assertThat(result).isEmpty()
        coVerify(exactly = 1) { movieRepository.getNowPlayingMovie(1) }
    }

    @Test
    fun `invoke SHOULD return single movie when repository returns single movie`() = runTest {
        val singleMovie = listOf(testMovies.first())
        coEvery { movieRepository.getNowPlayingMovie(1) } returns singleMovie

        val result = useCase.invoke(1)

        Truth.assertThat(result).hasSize(1)
        Truth.assertThat(result).isEqualTo(singleMovie)
        coVerify(exactly = 1) { movieRepository.getNowPlayingMovie(1) }
    }

    @Test(expected = RuntimeException::class)
    fun `invoke SHOULD throw exception when repository fails`() = runTest {
        coEvery { movieRepository.getNowPlayingMovie(1) } throws RuntimeException("Network error")

        useCase.invoke(1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `invoke SHOULD throw exception when repository throws IllegalArgumentException`() =
        runTest {
            coEvery { movieRepository.getNowPlayingMovie(-1) } throws IllegalArgumentException("Invalid page number")

            useCase.invoke(-1)
        }

    @Test
    fun `invoke SHOULD call repository with correct page parameter`() = runTest {
        coEvery { movieRepository.getNowPlayingMovie(5) } returns testMovies

        useCase.invoke(5)

        coVerify(exactly = 1) { movieRepository.getNowPlayingMovie(5) }
    }

    private companion object {
        val testMovies = listOf(
            Movie(
                id = 123,
                title = "Now Playing Movie 1",
                imageUrl = "/now_playing_poster1.jpg",
                rate = 8.5,
                releaseDate = "2023-01-01",
                movieDuration = "120 min",
                description = "Now playing movie description 1",
                genre = listOf(Genre(id = 1, name = "Action")),
                trailer = Trailer(key = "", id = "")
            ),
            Movie(
                id = 124,
                title = "Now Playing Movie 2",
                imageUrl = "/now_playing_poster2.jpg",
                rate = 7.5,
                releaseDate = "2023-01-02",
                movieDuration = "105 min",
                description = "Now playing movie description 2",
                genre = listOf(Genre(id = 2, name = "Drama")),
                trailer = Trailer(key = "", id = "")
            )
        )
    }
}