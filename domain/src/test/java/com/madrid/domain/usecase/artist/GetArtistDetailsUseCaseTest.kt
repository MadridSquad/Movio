package com.madrid.domain.usecase.artist

import com.google.common.truth.Truth
import com.madrid.domain.entity.Artist
import com.madrid.domain.repository.ArtistRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetArtistDetailsUseCaseTest {
    private val artistRepository: ArtistRepository = mockk(relaxed = true)
    private lateinit var useCase: GetArtistDetailsUseCase

    @Before
    fun setUp() {
        useCase = GetArtistDetailsUseCase(artistRepository)
    }

    @Test
    fun `invoke SHOULD return artist details from repository`() = runTest {
        coEvery { artistRepository.getArtistDetailsById(123) } returns testArtist

        val result = useCase.invoke(123)

        Truth.assertThat(result).isEqualTo(testArtist)
        coVerify(exactly = 1) { artistRepository.getArtistDetailsById(123) }
    }

    @Test
    fun `invoke SHOULD return correct artist for different artist id`() = runTest {
        val anotherArtist = testArtist.copy(id = 456, name = "Another Artist")
        coEvery { artistRepository.getArtistDetailsById(456) } returns anotherArtist

        val result = useCase.invoke(456)

        Truth.assertThat(result).isEqualTo(anotherArtist)
        coVerify(exactly = 1) { artistRepository.getArtistDetailsById(456) }
    }

    @Test(expected = RuntimeException::class)
    fun `invoke SHOULD throw exception when repository fails`() = runTest {
        coEvery { artistRepository.getArtistDetailsById(123) } throws RuntimeException("Network error")

        useCase.invoke(123)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `invoke SHOULD throw exception when repository throws IllegalArgumentException`() =
        runTest {
            coEvery { artistRepository.getArtistDetailsById(-1) } throws IllegalArgumentException("Invalid artist ID")

            useCase.invoke(-1)
        }

    @Test
    fun `invoke SHOULD call repository with correct artist id`() = runTest {
        coEvery { artistRepository.getArtistDetailsById(999) } returns testArtist

        useCase.invoke(999)

        coVerify(exactly = 1) { artistRepository.getArtistDetailsById(999) }
    }

    private companion object {
        val testArtist = Artist(
            id = 123,
            name = "Test Artist",
            role = "actor",
            dateOfBirth = "1990-01-01",
            country = "Spain",
            overview = "Test artist biography",
            imageUrl = "/test_artist.jpg"
        )
    }
}