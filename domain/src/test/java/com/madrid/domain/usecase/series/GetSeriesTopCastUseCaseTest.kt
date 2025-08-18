package com.madrid.domain.usecase.series

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.entity.Artist
import com.madrid.domain.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetSeriesTopCastUseCaseTest {
    private val seriesRepository: SeriesRepository = mockk(relaxed = true)
    private lateinit var useCase: GetSeriesTopCastUseCase

    @Before
    fun setUp() {
        useCase = GetSeriesTopCastUseCase(seriesRepository)
    }

    @Test
    fun `should call repository getSeriesCreditsById and return list`() = runTest {
        val seriesId = 123
        val expectedArtists = listOf(
            Artist(
                id = 1,
                name = "John Doe",
                role = "Actor",
                dateOfBirth = "1970-01-01",
                country = "USA",
                overview = "Test overview",
                imageUrl = "https://example.com/image1.jpg"
            ),
            Artist(
                id = 2,
                name = "Jane Smith",
                role = "Actress",
                dateOfBirth = "1975-05-15",
                country = "UK",
                overview = "Test overview",
                imageUrl = "https://example.com/image2.jpg"
            )
        )
        coEvery { seriesRepository.getSeriesCreditsById(seriesId) } returns expectedArtists

        val result = useCase.invoke(seriesId)

        assertThat(result).isEqualTo(expectedArtists)
        coVerify(exactly = 1) { seriesRepository.getSeriesCreditsById(seriesId) }
    }

    @Test
    fun `should return empty list when no cast found`() = runTest {
        val seriesId = 456
        val expectedArtists = emptyList<Artist>()
        coEvery { seriesRepository.getSeriesCreditsById(seriesId) } returns expectedArtists

        val result = useCase.invoke(seriesId)

        assertThat(result).isEmpty()
        coVerify(exactly = 1) { seriesRepository.getSeriesCreditsById(seriesId) }
    }

    @Test
    fun `should return single artist`() = runTest {
        val seriesId = 789
        val expectedArtists = listOf(
            Artist(
                id = 1,
                name = "Solo Actor",
                role = "Actor",
                dateOfBirth = "1980-01-01",
                country = "USA",
                overview = "Test overview",
                imageUrl = "https://example.com/image.jpg"
            )
        )
        coEvery { seriesRepository.getSeriesCreditsById(seriesId) } returns expectedArtists

        val result = useCase.invoke(seriesId)

        assertThat(result).hasSize(1)
        assertThat(result.first().name).isEqualTo("Solo Actor")
        assertThat(result).isEqualTo(expectedArtists)
        coVerify(exactly = 1) { seriesRepository.getSeriesCreditsById(seriesId) }
    }

    @Test
    fun `should return multiple artists`() = runTest {
        val seriesId = 111
        val expectedArtists = (1..15).map {
            Artist(
                id = it,
                name = "Actor $it",
                role = "Actor",
                dateOfBirth = "1980-01-01",
                country = "USA",
                overview = "Test overview",
                imageUrl = "https://example.com/image$it.jpg"
            )
        }
        coEvery { seriesRepository.getSeriesCreditsById(seriesId) } returns expectedArtists

        val result = useCase.invoke(seriesId)

        assertThat(result).hasSize(15)
        assertThat(result).containsExactlyElementsIn(expectedArtists)
        coVerify(exactly = 1) { seriesRepository.getSeriesCreditsById(seriesId) }
    }

    @Test
    fun `should handle different seriesId values`() = runTest {
        val seriesId = 0
        val expectedArtists = listOf(
            Artist(
                id = 1,
                name = "Zero Series Actor",
                role = "Actor",
                dateOfBirth = "1980-01-01",
                country = "USA",
                overview = "Test overview",
                imageUrl = "https://example.com/image.jpg"
            )
        )
        coEvery { seriesRepository.getSeriesCreditsById(seriesId) } returns expectedArtists

        val result = useCase.invoke(seriesId)

        assertThat(result).isEqualTo(expectedArtists)
        coVerify(exactly = 1) { seriesRepository.getSeriesCreditsById(seriesId) }
    }

    @Test
    fun `should handle large seriesId values`() = runTest {
        val seriesId = 999999
        val expectedArtists = listOf(
            Artist(
                id = 1,
                name = "Large ID Series Actor",
                role = "Actor",
                dateOfBirth = "1980-01-01",
                country = "USA",
                overview = "Test overview",
                imageUrl = "https://example.com/image.jpg"
            )
        )
        coEvery { seriesRepository.getSeriesCreditsById(seriesId) } returns expectedArtists

        val result = useCase.invoke(seriesId)

        assertThat(result).isEqualTo(expectedArtists)
        coVerify(exactly = 1) { seriesRepository.getSeriesCreditsById(seriesId) }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        val seriesId = 123
        coEvery { seriesRepository.getSeriesCreditsById(seriesId) } throws RuntimeException("Network error")

        useCase.invoke(seriesId)
    }

    @Test
    fun `should return cast in correct order`() = runTest {
        val seriesId = 222
        val expectedArtists = listOf(
            Artist(
                id = 3,
                name = "Third Actor",
                role = "Actor",
                dateOfBirth = "1980-01-01",
                country = "USA",
                overview = "Test overview",
                imageUrl = "https://example.com/image3.jpg"
            ),
            Artist(
                id = 1,
                name = "First Actor",
                role = "Actor",
                dateOfBirth = "1980-01-01",
                country = "USA",
                overview = "Test overview",
                imageUrl = "https://example.com/image1.jpg"
            ),
            Artist(
                id = 2,
                name = "Second Actor",
                role = "Actor",
                dateOfBirth = "1980-01-01",
                country = "USA",
                overview = "Test overview",
                imageUrl = "https://example.com/image2.jpg"
            )
        )
        coEvery { seriesRepository.getSeriesCreditsById(seriesId) } returns expectedArtists

        val result = useCase.invoke(seriesId)

        assertThat(result).containsExactlyElementsIn(expectedArtists).inOrder()
        coVerify(exactly = 1) { seriesRepository.getSeriesCreditsById(seriesId) }
    }
}