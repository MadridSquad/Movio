package com.madrid.data.repositories

import com.madrid.data.dataSource.local.mappers.toGenre
import com.madrid.data.dataSource.local.mappers.toSeries
import com.madrid.data.dataSource.mapper.toSeriesGenreTable
import com.madrid.data.dataSource.remote.mapper.toArtist
import com.madrid.data.dataSource.remote.mapper.toEpisode
import com.madrid.data.dataSource.remote.mapper.toRatedSeries
import com.madrid.data.dataSource.remote.mapper.toReview
import com.madrid.data.dataSource.remote.mapper.toSeries
import com.madrid.data.dataSource.remote.mapper.toSimilarSeries
import com.madrid.data.dataSource.remote.mapper.toTrailer
import com.madrid.data.dataSource.remote.mapper.toTvShows
import com.madrid.data.repositories.local.LocalDataSource
import com.madrid.data.repositories.remote.RemoteDataSource
import com.madrid.domain.entity.Artist
import com.madrid.domain.entity.Episode
import com.madrid.domain.entity.Genre
import com.madrid.domain.entity.Review
import com.madrid.domain.entity.Series
import com.madrid.domain.entity.SortType
import com.madrid.domain.entity.Trailer
import com.madrid.domain.repository.SeriesRepository
import com.madrid.domain.usecase.series.GetUserRatedSeriesUseCase
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : SeriesRepository {

    override suspend fun getSeriesDetailsById(seriesId: Int): Series {
        return remoteDataSource.getSeriesDetailsById(seriesId).toSeries()
    }

    override suspend fun getSeriesTrailersById(seriesId: Int): List<Trailer> {
        return remoteDataSource.getSeriesTrailersById(seriesId).map { it.toTrailer() }
    }

    override suspend fun getSeriesCreditsById(seriesId: Int): List<Artist> {
        return remoteDataSource.getSeriesCreditsById(seriesId).seriesCastNetwork?.map { it.toArtist() }
            ?: emptyList()
    }

    override suspend fun getSeriesReviewsById(seriesId: Int): List<Review> {
        return remoteDataSource.getSeriesReviewsById(seriesId).results?.map { it.toReview() }
            ?: emptyList()
    }

    override suspend fun getSimilarSeriesById(seriesId: Int): List<Series> {
        return remoteDataSource.getSimilarSeriesById(seriesId).results?.map { it.toSimilarSeries() }
            ?: emptyList()
    }

    override suspend fun getEpisodesBySeriesId(seriesId: Int, seasonNumber: Int): List<Episode> {
        return remoteDataSource.getEpisodesBySeasonId(
            seriesId = seriesId,
            seasonNumber = seasonNumber
        ).episodes?.map { it.toEpisode() } ?: emptyList()
    }

    override suspend fun getTopRatedSeries(page: Int): List<Series> {
        return remoteDataSource.getTopRatedSeries(page = page).toTvShows()
    }

    override suspend fun getOnAirSeries(page: Int): List<Series> {
        return remoteDataSource.getOnAirSeries(page = page).toTvShows()
    }

    override suspend fun getAiringTodaySeries(page: Int): List<Series> {
        return remoteDataSource.getAiringTodaySeries(page = page).toTvShows()
    }

    override suspend fun getRecommendedSeries(page: Int): List<Series> {
        return remoteDataSource.getRecommendedSeries(page = page).toTvShows()
    }

    override suspend fun increaseSeriesGenreInterestPoints(genreTitle: String) {
        localDataSource.increaseSeriesGenreInterestPoints(genreTitle)
    }

    override suspend fun getSeriesGenres(): List<Genre> {
        return localDataSource.getAllSeriesGenres().ifEmpty {
            remoteDataSource.getSeriesGenres().forEach {
                localDataSource.insertSeriesGenre(it.toSeriesGenreTable())
            }
            localDataSource.getAllSeriesGenres()
        }.map { it.toGenre() }
    }

    override suspend fun getSeriesByGenreId(
        page: Int,
        genreId: Int?,
        sortBy: SortType
    ): List<Series> {
        val sortType = getSortType(sortBy)
        return remoteDataSource.getSeriesByGenreId(
            page,
            genreId,
            sortType
        ).seriesResults.map { it.toSeries() }
    }

    override suspend fun getSeriesByGenres(): Map<String, List<Series>> {
        val genresWithSeries = localDataSource.getSeriesByGenres()
        return genresWithSeries.associate { genreWithSeries ->
            val genreTitle = genreWithSeries.genre.genreTitle
            val series = genreWithSeries.series.map { it.toSeries() }
            genreTitle to series
        }
    }

    override suspend fun getUserSeriesRate(sessionId: String): List<GetUserRatedSeriesUseCase.RatedSeries> {
        val result =
            remoteDataSource.getUserRatingForSeries(sessionId)
        return result.ratedSeries.map { it.toRatedSeries() }
    }

    override suspend fun addSeriesToHistory(seriesId: Int) {
        localDataSource.addSeriesToHistory(seriesId = seriesId)
    }

    override suspend fun getAllSeriesInHistory(): List<Series> {
        val seriesIds = localDataSource.getAllSeriesInHistory().map { it.mediaId }
        return seriesIds.map { getSeriesDetailsById(it) }
    }

    override suspend fun getFavoriteSeries(sessionId: String): List<Series> {
        return remoteDataSource.getFavoriteSeries(sessionId).map { it.toSeries() }
    }
}