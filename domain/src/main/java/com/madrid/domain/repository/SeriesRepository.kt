package com.madrid.domain.repository

import com.madrid.domain.entity.Artist
import com.madrid.domain.entity.Episode
import com.madrid.domain.entity.Genre
import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Review
import com.madrid.domain.entity.Series
import com.madrid.domain.entity.SortType
import com.madrid.domain.entity.Trailer
import com.madrid.domain.usecase.series.GetUserRatedSeriesUseCase

interface SeriesRepository {
    suspend fun getSeriesDetailsById(seriesId: Int): Series
    suspend fun getSeriesTrailersById(seriesId: Int): List<Trailer>
    suspend fun getSeriesCreditsById(seriesId: Int): List<Artist>
    suspend fun getSeriesReviewsById(seriesId: Int): List<Review>
    suspend fun getSimilarSeriesById(seriesId: Int): List<Series>
    suspend fun getEpisodesBySeriesId(seriesId: Int, seasonNumber: Int): List<Episode>
    suspend fun increaseSeriesGenreInterestPoints(genreTitle: String)
    suspend fun getSeriesByGenres(): Map<String, List<Series>>
    suspend fun getTopRatedSeries(page: Int): List<Series>
    suspend fun getOnAirSeries(page: Int): List<Series>
    suspend fun getAiringTodaySeries(page: Int): List<Series>
    suspend fun getRecommendedSeries(page: Int): List<Series>
    suspend fun getSeriesGenres(): List<Genre>
    suspend fun getSeriesByGenreId(page: Int, genreId: Int?, sortBy: SortType): List<Series>
    suspend fun addRatingSeries(seriesId: Int, rate: Double)
    suspend fun getUserSeriesRate(sessionId:String): List<GetUserRatedSeriesUseCase.RatedSeries>
    suspend fun addSeriesToHistory(seriesId: Int)
    suspend fun getAllSeriesInHistory(): List<Series>
    suspend fun getFavoriteSeries(sessionId: String): List<Series>
    suspend fun addSeriesToFavorite(mediaId:Int, sessionId: String)
}