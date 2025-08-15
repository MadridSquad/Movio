package com.madrid.domain.usecase.series

import com.madrid.domain.entity.Trailer
import com.madrid.domain.repository.SeriesRepository
import javax.inject.Inject

class GetEpisodeTrailersUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): List<Trailer> {
        return try {
            seriesRepository.getEpisodeTrailers(seriesId, seasonNumber, episodeNumber)
        } catch (e: Exception) {
            println("Error fetching episode trailer: $e")
            emptyList()
        }
    }
}
