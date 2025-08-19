package com.madrid.domain.usecase.movie

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.entity.Artist
import com.madrid.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetMovieTopCastUseCaseTest {
    private val movieRepository: MovieRepository = mockk(relaxed = true)
    private lateinit var useCase: GetMovieTopCastUseCase

    @Before
    fun setUp() {
        useCase = GetMovieTopCastUseCase(movieRepository)
    }

    @Test
    fun `should return artists list from repository`() = runTest {
        coEvery { movieRepository.getMovieCreditsById(123) } returns testArtists

        val result = useCase.invoke(123)

        assertThat(result).isEqualTo(testArtists)
        coVerify(exactly = 1) { movieRepository.getMovieCreditsById(123) }
    }

    @Test
    fun `should return correct artists for different movie id`() = runTest {
        val anotherArtists = listOf(testArtists.first().copy(id = 456, name = "Another Actor"))
        coEvery { movieRepository.getMovieCreditsById(456) } returns anotherArtists

        val result = useCase.invoke(456)

        assertThat(result).isEqualTo(anotherArtists)
        coVerify(exactly = 1) { movieRepository.getMovieCreditsById(456) }
    }

    @Test
    fun `should return empty list when repository returns empty list`() = runTest {
        coEvery { movieRepository.getMovieCreditsById(123) } returns emptyList()

        val result = useCase.invoke(123)

        assertThat(result).isEmpty()
        coVerify(exactly = 1) { movieRepository.getMovieCreditsById(123) }
    }

    @Test
    fun `should return single artist when repository returns single artist`() = runTest {
        val singleArtist = listOf(testArtists.first())
        coEvery { movieRepository.getMovieCreditsById(123) } returns singleArtist

        val result = useCase.invoke(123)

        assertThat(result).hasSize(1)
        assertThat(result).isEqualTo(singleArtist)
        coVerify(exactly = 1) { movieRepository.getMovieCreditsById(123) }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        coEvery { movieRepository.getMovieCreditsById(123) } throws RuntimeException("Network error")

        useCase.invoke(123)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should throw exception when repository throws IllegalArgumentException`() =
        runTest {
            coEvery { movieRepository.getMovieCreditsById(-1) } throws IllegalArgumentException("Invalid movie ID")

            useCase.invoke(-1)
        }

    @Test
    fun `should call repository with correct movie id`() = runTest {
        coEvery { movieRepository.getMovieCreditsById(999) } returns testArtists

        useCase.invoke(999)

        coVerify(exactly = 1) { movieRepository.getMovieCreditsById(999) }
    }

    private companion object {
        val testArtists = listOf(
            Artist(
                id = 123,
                name = "John Doe",
                role = "Main Character",
                dateOfBirth = "1980-01-15",
                country = "USA",
                overview = "Talented actor known for dramatic roles",
                imageUrl = "/john_doe.jpg"
            ),
            Artist(
                id = 124,
                name = "Jane Smith",
                role = "Supporting Character",
                dateOfBirth = "1985-03-22",
                country = "UK",
                overview = "Versatile actress with theater background",
                imageUrl = "/jane_smith.jpg"
            ),
            Artist(
                id = 125,
                name = "Bob Johnson",
                role = "Villain",
                dateOfBirth = "1975-11-08",
                country = "Canada",
                overview = "Character actor specializing in antagonist roles",
                imageUrl = "/bob_johnson.jpg"
            )
        )
    }
}