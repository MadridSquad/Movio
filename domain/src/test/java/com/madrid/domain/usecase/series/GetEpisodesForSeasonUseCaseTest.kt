package com.madrid.domain.usecase.series

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.entity.Episode
import com.madrid.domain.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetEpisodesForSeasonUseCaseTest {
    private val seriesRepository: SeriesRepository = mockk(relaxed = true)
    private lateinit var useCase: GetEpisodesForSeasonUseCase

    @Before
    fun setUp() {
        useCase = GetEpisodesForSeasonUseCase(seriesRepository)
    }

    @Test
    fun `should call repository getEpisodesBySeriesId with seriesId and seasonNumber`() =
        runTest {
            val seriesId = 1
            val seasonNumber = 1
            val expectedEpisodes = listOf(
                Episode(
                    id = 1,
                    title = "Episode 1",
                    episodeNumber = 1,
                    duration = "45m",
                    imageUrl = "",
                    rate = 8.5
                ),
                Episode(
                    id = 2,
                    title = "Episode 2",
                    episodeNumber = 2,
                    duration = "45m",
                    imageUrl = "",
                    rate = 8.0
                )
            )
            coEvery {
                seriesRepository.getEpisodesBySeriesId(
                    seriesId,
                    seasonNumber
                )
            } returns expectedEpisodes

            val result = useCase.invoke(seriesId, seasonNumber)

            assertThat(result).isEqualTo(expectedEpisodes)
            coVerify(exactly = 1) { seriesRepository.getEpisodesBySeriesId(seriesId, seasonNumber) }
        }

    @Test
    fun `should return empty list when no episodes found`() = runTest {
        val seriesId = 1
        val seasonNumber = 1
        val expectedEpisodes = emptyList<Episode>()
        coEvery {
            seriesRepository.getEpisodesBySeriesId(
                seriesId,
                seasonNumber
            )
        } returns expectedEpisodes

        val result = useCase.invoke(seriesId, seasonNumber)

        assertThat(result).isEmpty()
        coVerify(exactly = 1) { seriesRepository.getEpisodesBySeriesId(seriesId, seasonNumber) }
    }

    @Test
    fun `should handle different season numbers`() = runTest {
        val seriesId = 123
        val seasonNumber = 5
        val expectedEpisodes = listOf(
            Episode(
                id = 1,
                title = "Season 5 Episode",
                episodeNumber = 1,
                duration = "45m",
                imageUrl = "",
                rate = 8.0
            )
        )
        coEvery {
            seriesRepository.getEpisodesBySeriesId(
                seriesId,
                seasonNumber
            )
        } returns expectedEpisodes

        val result = useCase.invoke(seriesId, seasonNumber)

        assertThat(result).isEqualTo(expectedEpisodes)
        coVerify(exactly = 1) { seriesRepository.getEpisodesBySeriesId(seriesId, seasonNumber) }
    }

    @Test
    fun `should handle large seriesId and seasonNumber`() = runTest {
        val seriesId = 999999
        val seasonNumber = 100
        val expectedEpisodes = listOf(
            Episode(
                id = 1,
                title = "Large Season Episode",
                episodeNumber = 1,
                duration = "45m",
                imageUrl = "",
                rate = 8.0
            )
        )
        coEvery {
            seriesRepository.getEpisodesBySeriesId(
                seriesId,
                seasonNumber
            )
        } returns expectedEpisodes

        val result = useCase.invoke(seriesId, seasonNumber)

        assertThat(result).isEqualTo(expectedEpisodes)
        coVerify(exactly = 1) { seriesRepository.getEpisodesBySeriesId(seriesId, seasonNumber) }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        val seriesId = 1
        val seasonNumber = 1
        coEvery {
            seriesRepository.getEpisodesBySeriesId(
                seriesId,
                seasonNumber
            )
        } throws RuntimeException("Network error")

        useCase.invoke(seriesId, seasonNumber)
    }

    @Test
    fun `should return multiple episodes for season`() = runTest {
        val seriesId = 456
        val seasonNumber = 2
        val expectedEpisodes = (1..10).map {
            Episode(
                id = it,
                title = "Episode $it",
                episodeNumber = it,
                duration = "45m",
                imageUrl = "",
                rate = 8.0
            )
        }
        coEvery {
            seriesRepository.getEpisodesBySeriesId(
                seriesId,
                seasonNumber
            )
        } returns expectedEpisodes

        val result = useCase.invoke(seriesId, seasonNumber)

        assertThat(result).hasSize(10)
        assertThat(result).isEqualTo(expectedEpisodes)
        coVerify(exactly = 1) { seriesRepository.getEpisodesBySeriesId(seriesId, seasonNumber) }
    }
}