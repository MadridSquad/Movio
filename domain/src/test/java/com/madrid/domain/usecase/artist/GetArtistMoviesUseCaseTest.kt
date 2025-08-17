package com.madrid.domain.usecase.artist

import com.google.common.truth.Truth
import com.madrid.domain.entity.Movie
import com.madrid.domain.repository.ArtistRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetArtistMoviesUseCaseTest {
    private val artistRepository: ArtistRepository = mockk(relaxed = true)
    private lateinit var useCase: GetArtistMoviesUseCase

    @Before
    fun setUp() {
        useCase = GetArtistMoviesUseCase(artistRepository)
    }

    @Test
    fun `invoke SHOULD return artist movies from repository`() = runTest {
        coEvery { artistRepository.getArtistMovies(123) } returns testMovies

        val result = useCase.invoke(123)

        Truth.assertThat(result).isEqualTo(testMovies)
        coVerify(exactly = 1) { artistRepository.getArtistMovies(123) }
    }

    @Test
    fun `invoke SHOULD return correct movies for different artist id`() = runTest {
        val anotherMovie = listOf(testMovies.first().copy(id = 456, title = "Another Movie"))
        coEvery { artistRepository.getArtistMovies(456) } returns anotherMovie

        val result = useCase.invoke(456)

        Truth.assertThat(result).isEqualTo(anotherMovie)
        coVerify(exactly = 1) { artistRepository.getArtistMovies(456) }
    }

    @Test
    fun `invoke SHOULD return empty list when artist has no movies`() = runTest {
        coEvery { artistRepository.getArtistMovies(789) } returns emptyList()

        val result = useCase.invoke(789)

        Truth.assertThat(result).isEmpty()
        coVerify(exactly = 1) { artistRepository.getArtistMovies(789) }
    }

    @Test(expected = RuntimeException::class)
    fun `invoke SHOULD throw exception when repository fails`() = runTest {
        coEvery { artistRepository.getArtistMovies(123) } throws RuntimeException("Network error")

        useCase.invoke(123)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `invoke SHOULD throw exception when repository throws IllegalArgumentException`() =
        runTest {
            coEvery { artistRepository.getArtistMovies(-1) } throws IllegalArgumentException("Invalid artist ID")

            useCase.invoke(-1)
        }

    @Test
    fun `invoke SHOULD call repository with correct artist id`() = runTest {
        coEvery { artistRepository.getArtistMovies(999) } returns testMovies

        useCase.invoke(999)

        coVerify(exactly = 1) { artistRepository.getArtistMovies(999) }
    }

    private companion object {
        val testMovies = listOf(
            Movie(
                id = 1,
                title = "Test Movie 1",
                imageUrl = "/test_movie1.jpg",
                rate = 8.5,
                releaseDate = "2023-01-01",
                movieDuration = "120",
                description = "Test movie 1 overview",
                genre = listOf()
            ),
            Movie(
                id = 2,
                title = "Test Movie 2",
                imageUrl = "/test_movie2.jpg",
                rate = 7.8,
                releaseDate = "2023-06-15",
                movieDuration = "95",
                description = "Test movie 2 overview",
                genre = listOf()
            )
        )
    }
}