package com.madrid.data.dataSource.remote

import android.util.Log
import com.madrid.data.dataSource.remote.dto.artist.ArtistDetailsResponse
import com.madrid.data.dataSource.remote.dto.artist.KnownForMoviesNetwork
import com.madrid.data.dataSource.remote.dto.artist.SearchArtistResponse
import com.madrid.data.dataSource.remote.dto.authentication.AccountDetailsResponse
import com.madrid.data.dataSource.remote.dto.authentication.CreateSessionBody
import com.madrid.data.dataSource.remote.dto.authentication.CreateSessionRawBody
import com.madrid.data.dataSource.remote.dto.common.TrailerResult
import com.madrid.data.dataSource.remote.dto.genre.RemoteGenreDto
import com.madrid.data.dataSource.remote.dto.list.ListDto
import com.madrid.data.dataSource.remote.dto.list.ListsDetailsResponse
import com.madrid.data.dataSource.remote.dto.movie.MovieCreditsResponse
import com.madrid.data.dataSource.remote.dto.movie.MovieDetailsResponse
import com.madrid.data.dataSource.remote.dto.movie.MovieReviewResponse
import com.madrid.data.dataSource.remote.dto.movie.NowPlayingMovieResponse
import com.madrid.data.dataSource.remote.dto.movie.SearchMovieResponse
import com.madrid.data.dataSource.remote.dto.movie.SimilarMoviesResponse
import com.madrid.data.dataSource.remote.dto.movie.UpcomingMoviesResponse
import com.madrid.data.dataSource.remote.dto.rate.RatingMovieResponse
import com.madrid.data.dataSource.remote.dto.rate.RatingSeriesResponse
import com.madrid.data.dataSource.remote.dto.series.AiringTodayTvShowsResponse
import com.madrid.data.dataSource.remote.dto.series.OnAirTvShowsResponse
import com.madrid.data.dataSource.remote.dto.series.RecommendedSeriesResponse
import com.madrid.data.dataSource.remote.dto.series.SearchSeriesResponse
import com.madrid.data.dataSource.remote.dto.series.SeasonResponse
import com.madrid.data.dataSource.remote.dto.series.SeriesCreditResponse
import com.madrid.data.dataSource.remote.dto.series.SeriesDetailsResponse
import com.madrid.data.dataSource.remote.dto.series.SeriesReviewResponse
import com.madrid.data.dataSource.remote.dto.series.SimilarSeriesResponse
import com.madrid.data.dataSource.remote.dto.series.TopRatedSeriesResponse
import com.madrid.data.repositories.remote.RemoteDataSource
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val api: MovioApi
) : RemoteDataSource {
    //  region Movies
    override suspend fun searchMoviesByQuery(name: String, page: Int): SearchMovieResponse {
        return api.searchMoviesByQuery(name, page)
    }

    override suspend fun getTopRatedMovies(page: Int): SearchMovieResponse {
        return api.getTopRatedMovies(page)
    }

    override suspend fun getPopularMovies(page: Int): SearchMovieResponse {
        return api.getPopularMovie(page)
    }

    override suspend fun getMovieDetailsById(movieId: Int): MovieDetailsResponse {
        return api.getMovieDetailsById(movieId)
    }

    override suspend fun getMovieTrailersByMovieId(movieId: Int): List<TrailerResult> {
        return api.getMovieTrailersById(movieId).results
    }

    override suspend fun getMovieCreditById(movieId: Int): MovieCreditsResponse {
        return api.getMovieCreditById(movieId)
    }

    override suspend fun getMovieReviewsById(movieId: Int): MovieReviewResponse {
        return api.getMovieReviewsById(movieId)
    }

    override suspend fun getSimilarMoviesById(movieId: Int): SimilarMoviesResponse {
        return api.getSimilarMoviesById(movieId)
    }

    override suspend fun getMovieGenres(): List<RemoteGenreDto> {
        return api.getMovieGenres().genres.orEmpty()
    }

    override suspend fun getTrendingMovies(page: Int): SearchMovieResponse {
        return api.getTrendingMovies(page = page)
    }

    override suspend fun getMoviesByGenreId(
        page: Int,
        genreId: Int?,
        sortBy: String
    ): SearchMovieResponse {
        return api.getMoviesByGenreId(page, genreId, sortBy)
    }
    // endregion

    // region Series
    override suspend fun getTopRatedSeries(page: Int): TopRatedSeriesResponse {
        return  api.getTopRatedSeries(page)
    }

    override suspend fun searchSeriesByQuery(name: String, page: Int): SearchSeriesResponse {
        return api.searchSeriesByQuery(name, page)
    }

    override suspend fun getSeriesTrailersById(seriesId: Int): List<TrailerResult> {
        return api.getSeriesTrailersById(seriesId).results
    }

    override suspend fun getSeriesCreditsById(seriesId: Int): SeriesCreditResponse {
        return api.getSeriesCreditsById(seriesId)
    }

    override suspend fun getSeriesReviewsById(seriesId: Int): SeriesReviewResponse {
        return api.getSeriesReviewsById(seriesId)
    }

    override suspend fun getSimilarSeriesById(seriesId: Int): SimilarSeriesResponse {
        return api.getSimilarSeriesById(seriesId)
    }

    override suspend fun getEpisodesBySeasonId(
        seriesId: Int,
        seasonNumber: Int
    ): SeasonResponse {
        return api.getEpisodesBySeasonId(seriesId, seasonNumber)
    }

    override suspend fun getSeriesGenres(): List<RemoteGenreDto> {
        return api.getSeriesGenres().genres.orEmpty()
    }

    override suspend fun getTrendingSeries(page: Int): SearchSeriesResponse {
        return api.getTrendingSeries(page = page)
    }

    override suspend fun getSeriesDetailsById(seriesId: Int): SeriesDetailsResponse {
        return api.getSeriesDetailsById(seriesId)
    }

    // Artist
    override suspend fun searchArtistByQuery(name: String, page: Int): SearchArtistResponse {
        return api.searchArtistByQuery(name, page)
    }

    override suspend fun getArtistDetailsById(artistId: Int): ArtistDetailsResponse {
        return api.getArtistDetailsById(artistId)
    }

    override suspend fun getArtistMovies(artistId: Int): List<KnownForMoviesNetwork> {
        return api.getArtistKnownForById(artistId).movies ?: emptyList()
    }

    override suspend fun getOnAirSeries(page: Int): OnAirTvShowsResponse {
        return api.getOnTheAirTvShows(page = page)
    }

    override suspend fun getAiringTodaySeries(page: Int): AiringTodayTvShowsResponse {
        return api.getAiringTvShowsToday(page = page)
    }

    override suspend fun getRecommendedSeries(page: Int): RecommendedSeriesResponse {
        return api.getPopularTvShows(page = page)
    }

    override suspend fun getUpcomingMovie(page: Int): UpcomingMoviesResponse {
        return api.getUpcomingMovies(page)
    }

    override suspend fun getNowPlayingMovie(page: Int): NowPlayingMovieResponse {
        return api.getNowPlayingMovies(page)
    }

    override suspend fun getSeriesByGenreId(
        page: Int,
        genreId: Int?,
        sortBy: String
    ): SearchSeriesResponse {
        return api.getSeriesByGenreId(page, genreId, sortBy)
    }

    override suspend fun getCustomLists(sessionId: String): List<ListDto> {
        return api.getCustomLists(sessionId).results
    }

    override suspend fun getCustomListDetails(listId: Int): ListsDetailsResponse {
        return api.getCustomListDetails(listId)
    }

    override suspend fun login(username: String, password: String): String {
        val requestTokenResponse = api.getRequestToken()
        val requestToken = requestTokenResponse.requestToken
        val sessionResponse = api.postCreateSession(
            CreateSessionBody(
                username = username,
                password = password,
                requestToken = requestToken
            )
        )
        return sessionResponse.requestToken
    }

    override suspend fun getSessionId(username: String, password: String): String {
        val requestTokenResponse = api.getRequestToken()
        val requestToken = requestTokenResponse.requestToken
        val sessionResponse = api.postCreateSession(
            CreateSessionBody(
                username = username,
                password = password,
                requestToken = requestToken
            )
        )
        val sessionId = api.createSession(
            CreateSessionRawBody(sessionResponse.requestToken)
        )
        return sessionId.sessionId
    }

    override suspend fun loginAsGuest(): String {
        return api.getCreateGuestSession().requestToken
    }

    override suspend fun getCurrentUserDetails(sessionId: String): AccountDetailsResponse {
        return api.getAccountDetails(sessionId)
    }

    override suspend fun getUserRatingForMovie(sessionId: String): RatingMovieResponse {
        val accountId = api.getAccountDetails(sessionId).id
        return api.getUserRatingForMovie(accountId, sessionId)
    }

    override suspend fun getUserRatingForSeries(sessionId: String): RatingSeriesResponse {
        val accountId = api.getAccountDetails(sessionId).id
        return api.getUserRatingForSeries(accountId, sessionId)
    }
}