package com.madrid.domain.usecase.series

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.entity.Trailer
import com.madrid.domain.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetEpisodeTrailersUseCaseTest {
    private val seriesRepository: SeriesRepository = mockk(relaxed = true)
    private lateinit var useCase: GetEpisodeTrailersUseCase

    @Before
    fun setUp() {
        useCase = GetEpisodeTrailersUseCase(seriesRepository)
    }

    @Test
    fun `should call repository getEpisodeTrailers with seriesId seasonNumber and episodeNumber`() =
        runTest {
            val seriesId = 1
            val seasonNumber = 1
            val episodeNumber = 1
            val expectedTrailers = listOf(
                Trailer(key = "Trailer 1", id = "1"),
                Trailer(key = "Trailer 2", id = "2")
            )
            coEvery {
                seriesRepository.getEpisodeTrailers(
                    seriesId,
                    seasonNumber,
                    episodeNumber
                )
            } returns expectedTrailers

            val result = useCase.invoke(seriesId, seasonNumber, episodeNumber)

            assertThat(result).isEqualTo(expectedTrailers)
            coVerify(exactly = 1) {
                seriesRepository.getEpisodeTrailers(
                    seriesId,
                    seasonNumber,
                    episodeNumber
                )
            }
        }

    @Test
    fun `should return empty list when no trailers found`() = runTest {
        val seriesId = 1
        val seasonNumber = 1
        val episodeNumber = 1
        val expectedTrailers = emptyList<Trailer>()
        coEvery {
            seriesRepository.getEpisodeTrailers(
                seriesId,
                seasonNumber,
                episodeNumber
            )
        } returns expectedTrailers

        val result = useCase.invoke(seriesId, seasonNumber, episodeNumber)

        assertThat(result).isEmpty()
        coVerify(exactly = 1) {
            seriesRepository.getEpisodeTrailers(
                seriesId,
                seasonNumber,
                episodeNumber
            )
        }
    }

    @Test
    fun `should return empty list when repository throws exception`() = runTest {
        val seriesId = 1
        val seasonNumber = 1
        val episodeNumber = 1
        coEvery {
            seriesRepository.getEpisodeTrailers(
                seriesId,
                seasonNumber,
                episodeNumber
            )
        } throws RuntimeException("Network error")

        val result = useCase.invoke(seriesId, seasonNumber, episodeNumber)

        assertThat(result).isEmpty()
        coVerify(exactly = 1) {
            seriesRepository.getEpisodeTrailers(
                seriesId,
                seasonNumber,
                episodeNumber
            )
        }
    }

    @Test
    fun `should handle different episode parameters`() = runTest {
        val seriesId = 123
        val seasonNumber = 5
        val episodeNumber = 10
        val expectedTrailers = listOf(Trailer(key = "Episode Trailer", id = "1"))
        coEvery {
            seriesRepository.getEpisodeTrailers(
                seriesId,
                seasonNumber,
                episodeNumber
            )
        } returns expectedTrailers

        val result = useCase.invoke(seriesId, seasonNumber, episodeNumber)

        assertThat(result).isEqualTo(expectedTrailers)
        coVerify(exactly = 1) {
            seriesRepository.getEpisodeTrailers(
                seriesId,
                seasonNumber,
                episodeNumber
            )
        }
    }

    @Test
    fun `should handle large parameter values`() = runTest {
        val seriesId = 999999
        val seasonNumber = 100
        val episodeNumber = 50
        val expectedTrailers = listOf(Trailer(key = "Large Episode Trailer", id = "1"))
        coEvery {
            seriesRepository.getEpisodeTrailers(
                seriesId,
                seasonNumber,
                episodeNumber
            )
        } returns expectedTrailers

        val result = useCase.invoke(seriesId, seasonNumber, episodeNumber)

        assertThat(result).isEqualTo(expectedTrailers)
        coVerify(exactly = 1) {
            seriesRepository.getEpisodeTrailers(
                seriesId,
                seasonNumber,
                episodeNumber
            )
        }
    }

    @Test
    fun `should return multiple trailers when available`() = runTest {
        val seriesId = 456
        val seasonNumber = 2
        val episodeNumber = 3
        val expectedTrailers = (1..5).map { Trailer(key = "Trailer $it", id = it.toString()) }
        coEvery {
            seriesRepository.getEpisodeTrailers(
                seriesId,
                seasonNumber,
                episodeNumber
            )
        } returns expectedTrailers

        val result = useCase.invoke(seriesId, seasonNumber, episodeNumber)

        assertThat(result).hasSize(5)
        assertThat(result).isEqualTo(expectedTrailers)
        coVerify(exactly = 1) {
            seriesRepository.getEpisodeTrailers(
                seriesId,
                seasonNumber,
                episodeNumber
            )
        }
    }
}