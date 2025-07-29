package com.madrid.data.repositories.remote

import com.madrid.data.dataSource.remote.dto.artist.ArtistDetailsResponse
import com.madrid.data.dataSource.remote.dto.artist.KnownForMoviesNetwork
import com.madrid.data.dataSource.remote.dto.artist.SearchArtistResponse
import com.madrid.data.dataSource.remote.dto.common.TrailerResult
import com.madrid.data.dataSource.remote.dto.genre.GenresResponse
import com.madrid.data.dataSource.remote.dto.movie.MovieCreditsResponse
import com.madrid.data.dataSource.remote.dto.movie.MovieDetailsResponse
import com.madrid.data.dataSource.remote.dto.movie.MovieReviewResponse
import com.madrid.data.dataSource.remote.dto.movie.SearchMovieResponse
import com.madrid.data.dataSource.remote.dto.movie.SimilarMoviesResponse
import com.madrid.data.dataSource.remote.dto.series.SearchSeriesResponse
import com.madrid.data.dataSource.remote.dto.series.SeasonResponse
import com.madrid.data.dataSource.remote.dto.series.SeriesCreditResponse
import com.madrid.data.dataSource.remote.dto.series.SeriesDetailsResponse
import com.madrid.data.dataSource.remote.dto.series.SeriesReviewResponse
import com.madrid.data.dataSource.remote.dto.series.SimilarSeriesResponse
import com.madrid.data.dataSource.remote.response.series.AiringTodayTvShowsResponse
import com.madrid.data.dataSource.remote.response.series.OnAirTvShowsResponse
import com.madrid.data.dataSource.remote.response.series.RecommendedSeriesResponse
import com.madrid.data.dataSource.remote.response.series.TopRatedSeriesResponse

interface RemoteDataSource {

    // region Search
    suspend fun searchMoviesByQuery(name: String, page: Int): SearchMovieResponse
    suspend fun searchSeriesByQuery(name: String, page: Int): SearchSeriesResponse
    suspend fun searchArtistByQuery(name: String, page: Int): SearchArtistResponse
    // endregion

    // region Movies
    suspend fun getTopRatedMovies(page: Int): SearchMovieResponse
    suspend fun getPopularMovies(page: Int): SearchMovieResponse
    suspend fun getMovieDetailsById(movieId: Int): MovieDetailsResponse
    suspend fun getMovieTrailersByMovieId(movieId: Int): List<TrailerResult>
    suspend fun getMovieCreditById(movieId: Int): MovieCreditsResponse
    suspend fun getMovieReviewsById(movieId: Int): MovieReviewResponse
    suspend fun getSimilarMoviesById(movieId: Int): SimilarMoviesResponse
    suspend fun getMovieGenres(): GenresResponse
    suspend fun getTrendingMovies(page: Int): SearchMovieResponse
    // endregion

    // region Series
    suspend fun getSeriesTrailersById(seriesId: Int): List<TrailerResult>
    suspend fun getSeriesCreditsById(seriesId: Int): SeriesCreditResponse
    suspend fun getSeriesReviewsById(seriesId: Int): SeriesReviewResponse
    suspend fun getSimilarSeriesById(seriesId: Int): SimilarSeriesResponse
    suspend fun getEpisodesBySeasonId(seriesId: Int, seasonNumber: Int): SeasonResponse
    suspend fun getSeriesGenres(): GenresResponse
    suspend fun getSeriesDetailsById(seriesId: Int): SeriesDetailsResponse
    suspend fun getTrendingSeries(page: Int): SearchSeriesResponse
    suspend fun getTopRatedSeries(page: Int = 1): TopRatedSeriesResponse
    suspend fun getOnAirSeries(page: Int = 1): OnAirTvShowsResponse
    suspend fun getAiringTodaySeries(page: Int = 1): AiringTodayTvShowsResponse
    suspend fun getRecommendedSeries(page: Int = 1): RecommendedSeriesResponse
    // endregion

    // region Artist
    suspend fun getArtistDetailsById(artistId: Int): ArtistDetailsResponse
    suspend fun getArtistMovies(artistId: Int): List<KnownForMoviesNetwork>
    // endregion
}