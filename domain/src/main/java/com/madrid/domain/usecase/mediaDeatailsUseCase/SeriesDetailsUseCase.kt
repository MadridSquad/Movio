package com.madrid.domain.usecase.mediaDeatailsUseCase

import com.madrid.domain.entity.Cast
import com.madrid.domain.entity.Episode
import com.madrid.domain.entity.Review
import com.madrid.domain.entity.Series
import com.madrid.domain.entity.SimilarSeries
import com.madrid.domain.entity.Trailer
import com.madrid.domain.repository.SeriesRepository
import kotlinx.coroutines.flow.Flow

class SeriesDetailsUseCase(private val seriesRepository: SeriesRepository) {

    suspend fun getSeriesDetailsById(seriesId: Int): Series =
        seriesRepository.getSeriesDetailsById(seriesId)

    suspend fun getSeriesTrailersById(seriesId: Int): Trailer =
        seriesRepository.getSeriesTrailersById(seriesId)

    suspend fun getSeriesCreditsById(seriesId: Int): List<Cast> =
        seriesRepository.getSeriesCreditsById(seriesId)

    suspend fun getSeriesReviewsById(seriesId: Int): List<Review> =
        seriesRepository.getSeriesReviewsById(seriesId)

    suspend fun getSimilarSeriesById(seriesId: Int): List<SimilarSeries> =
        seriesRepository.getSimilarSeriesById(seriesId)

    suspend fun getEpisodesBySeriesId(seriesId: Int, seasonNumber: Int): List<Episode> =
        seriesRepository.getEpisodesBySeriesId(seriesId, seasonNumber)


    suspend fun submitSeriesRating(rating: Float) = seriesRepository.submitSeriesRating(rating)

    suspend fun addSeriesToFavourites(seriesId: Int) = seriesRepository.addSeriesToFavourites(seriesId)

    suspend fun getListsCollection(): Flow<List<String>> = seriesRepository.getListsCollection()

    suspend fun addNewCollection(collection: String) = seriesRepository.addNewCollection(collection)

    suspend fun addSeriesToList(seriesId: Int,listName: String) = seriesRepository.addSeriesToList(seriesId,listName)
}