package com.madrid.domain.usecase.series

import com.madrid.domain.entity.Trailer
import com.madrid.domain.repository.SeriesRepository
import javax.inject.Inject

class GetSeriesEpisodeTrailersUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(seriesId: Int,seasonNumber: Int, episodeNumber: Int): List<Trailer> =
        seriesRepository.getSeriesEpisodeTrailersById(seriesId,seasonNumber,episodeNumber)
}