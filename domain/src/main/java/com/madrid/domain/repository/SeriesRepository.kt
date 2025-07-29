package com.madrid.domain.repository

import com.madrid.domain.entity.Cast
import com.madrid.domain.entity.Episode
import com.madrid.domain.entity.Review
import com.madrid.domain.entity.Series
import com.madrid.domain.entity.SimilarSeries
import com.madrid.domain.entity.Trailer
import kotlinx.coroutines.flow.Flow

interface SeriesRepository {

    suspend fun getSeriesDetailsById(seriesId: Int): Series
    suspend fun getSeriesTrailersById(seriesId: Int): Trailer
    suspend fun getSeriesCreditsById(seriesId: Int): List<Cast>
    suspend fun getSeriesReviewsById(seriesId: Int): List<Review>
    suspend fun getSimilarSeriesById(seriesId: Int): List<SimilarSeries>
    suspend fun getEpisodesBySeriesId(seriesId: Int, seasonNumber: Int): List<Episode>

    suspend fun submitSeriesRating(rating: Float)
    suspend fun addSeriesToFavourites(seriesId: Int)
    suspend fun getListsCollection(): Flow<List<String>>
    suspend fun addNewCollection(collection: String)
    suspend fun addSeriesToList(seriesId: Int,listName: String): Boolean

    suspend fun getTopRatedSeries(): List<Series>
    suspend fun getOnAirSeries(): List<Series>
    suspend fun getAiringTodaySeries(): List<Series>
    suspend fun getRecommendedSeries(): List<Series>
}