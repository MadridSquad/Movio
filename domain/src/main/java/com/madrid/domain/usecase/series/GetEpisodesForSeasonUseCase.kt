package com.madrid.domain.usecase.series

import com.madrid.domain.entity.Episode
import com.madrid.domain.repository.SeriesRepository
import javax.inject.Inject

class GetEpisodesForSeasonUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(seriesId: Int, seasonNumber: Int): List<Episode> =
        seriesRepository.getEpisodesBySeriesId(seriesId, seasonNumber)
}