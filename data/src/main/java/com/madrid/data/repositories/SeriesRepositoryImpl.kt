package com.madrid.data.repositories

import android.util.Log
import com.madrid.data.dataSource.remote.mapper.toCredits
import com.madrid.data.dataSource.remote.mapper.toEpisode
import com.madrid.data.dataSource.remote.mapper.toReviewResult
import com.madrid.data.dataSource.remote.mapper.toSeries
import com.madrid.data.dataSource.remote.mapper.toSimilarSeries
import com.madrid.data.dataSource.remote.mapper.toTrailer
import com.madrid.data.dataSource.remote.mapper.toTvShows
import com.madrid.data.repositories.local.LocalDataSource
import com.madrid.data.repositories.remote.RemoteDataSource
import com.madrid.domain.entity.Cast
import com.madrid.domain.entity.Episode
import com.madrid.domain.entity.Review
import com.madrid.domain.entity.Series
import com.madrid.domain.entity.SimilarSeries
import com.madrid.domain.entity.Trailer
import com.madrid.domain.repository.SeriesRepository
import kotlinx.coroutines.flow.Flow

class SeriesRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : SeriesRepository {

    //region series details
    override suspend fun getSeriesDetailsById(seriesId: Int): Series {
        val seriesResponse = remoteDataSource.getSeriesDetailsById(seriesId)
        seriesResponse.genres?.map { genre ->
            localDataSource.increaseSeriesGenreSeenCount(genre.name ?: "")
        }
        return seriesResponse.toSeries()
    }

    override suspend fun getSeriesTrailersById(seriesId: Int): Trailer {
        return remoteDataSource.getSeriesTrailersById(seriesId).toTrailer()
    }

    override suspend fun getSeriesCreditsById(seriesId: Int): List<Cast> {
        return remoteDataSource.getSeriesCreditsById(seriesId).toCredits().cast ?: emptyList()
    }

    override suspend fun getSeriesReviewsById(seriesId: Int): List<Review> {
        Log.d("TAG lol", "getSeriesReviewsById repo impl: ")
        val x = remoteDataSource.getSeriesReviewsById(seriesId).toReviewResult().results ?: emptyList()
        Log.d("TAG lol", "getSeriesReviewsById repo impl: $x")
        return x
    }

    override suspend fun getSimilarSeriesById(seriesId: Int): List<SimilarSeries> {
        return remoteDataSource.getSimilarSeriesById(seriesId).results?.map { it.toSimilarSeries() }
            ?: emptyList()
    }

    override suspend fun getEpisodesBySeriesId(seriesId: Int, seasonNumber: Int): List<Episode> {
        return remoteDataSource.getEpisodesBySeasonId(
            seriesId = seriesId,
            seasonNumber = seasonNumber
        ).episodeNetworks?.map { it.toEpisode() } ?: emptyList()
    }
    //End region

    override suspend fun getTopRatedSeries(): List<Series> {
        return remoteDataSource.getTopRatedSeries().toTvShows()
    }

    override suspend fun getOnAirSeries(): List<Series> {
        return remoteDataSource.getOnAirSeries().toTvShows()
    }

    override suspend fun getAiringTodaySeries(): List<Series> {
        return remoteDataSource.getAiringTodaySeries().toTvShows()
    }

    override suspend fun getRecommendedSeries(): List<Series> {
        return remoteDataSource.getRecommendedSeries().toTvShows()
    }


    override suspend fun submitSeriesRating(rating: Float) {
        TODO("Not yet implemented")
    }

    override suspend fun addSeriesToFavourites(seriesId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getListsCollection(): Flow<List<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun addNewCollection(collection: String) {
        TODO("Not yet implemented")
    }

    override suspend fun addSeriesToList(seriesId: Int, listName: String): Boolean {
        TODO("Not yet implemented")
    }
}