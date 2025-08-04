package com.madrid.data.repositories.remote

import com.madrid.data.dataSource.remote.dto.artist.ArtistDetailsResponse
import com.madrid.data.dataSource.remote.dto.artist.KnownForMoviesNetwork
import com.madrid.data.dataSource.remote.dto.artist.SearchArtistResponse
import com.madrid.data.dataSource.remote.dto.authentication.AccountDetailsResponse
import com.madrid.data.dataSource.remote.dto.common.TrailerResult
import com.madrid.data.dataSource.remote.dto.genre.RemoteGenreDto
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
import com.madrid.data.dataSource.remote.dto.movie.NowPlayingMovieResponse
import com.madrid.data.dataSource.remote.dto.movie.UpcomingMoviesResponse
import com.madrid.data.dataSource.remote.dto.series.AiringTodayTvShowsResponse
import com.madrid.data.dataSource.remote.dto.series.OnAirTvShowsResponse
import com.madrid.data.dataSource.remote.dto.series.RecommendedSeriesResponse
import com.madrid.data.dataSource.remote.dto.series.TopRatedSeriesResponse

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
    suspend fun getMovieGenres(): List<RemoteGenreDto>
    suspend fun getTrendingMovies(page: Int): SearchMovieResponse
    suspend fun getUpcomingMovie(page: Int = 1): UpcomingMoviesResponse
    suspend fun getNowPlayingMovie(page: Int = 1): NowPlayingMovieResponse
    suspend fun getMoviesByGenreId(page: Int, genreId: Int?, sortBy: String): SearchMovieResponse
    // endregion

    // region Series
    suspend fun getSeriesTrailersById(seriesId: Int): List<TrailerResult>
    suspend fun getSeriesCreditsById(seriesId: Int): SeriesCreditResponse
    suspend fun getSeriesReviewsById(seriesId: Int): SeriesReviewResponse
    suspend fun getSimilarSeriesById(seriesId: Int): SimilarSeriesResponse
    suspend fun getEpisodesBySeasonId(seriesId: Int, seasonNumber: Int): SeasonResponse
    suspend fun getSeriesGenres(): List<RemoteGenreDto>
    suspend fun getSeriesDetailsById(seriesId: Int): SeriesDetailsResponse
    suspend fun getTrendingSeries(page: Int): SearchSeriesResponse
    suspend fun getTopRatedSeries(page: Int = 1): TopRatedSeriesResponse
    suspend fun getOnAirSeries(page: Int = 1): OnAirTvShowsResponse
    suspend fun getAiringTodaySeries(page: Int = 1): AiringTodayTvShowsResponse
    suspend fun getRecommendedSeries(page: Int = 1): RecommendedSeriesResponse
    suspend fun getSeriesByGenreId(page: Int, genreId: Int?, sortBy: String): SearchSeriesResponse
    // endregion

    // region Artist
    suspend fun getArtistDetailsById(artistId: Int): ArtistDetailsResponse
    suspend fun getArtistMovies(artistId: Int): List<KnownForMoviesNetwork>

    // endregion
    // region authentication
    suspend fun login(username: String, password: String): String
    suspend fun loginAsGuest(): String
    suspend fun getCurrentUserDetails(sessionId: String): AccountDetailsResponse

    suspend fun getSessionId(username: String, password: String): String
    // endregion

}