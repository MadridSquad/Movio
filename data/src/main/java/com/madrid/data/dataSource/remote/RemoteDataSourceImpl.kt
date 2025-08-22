package com.madrid.data.dataSource.remote

import com.madrid.data.dataSource.remote.dto.artist.ArtistDetailsResponse
import com.madrid.data.dataSource.remote.dto.artist.KnownForMoviesNetwork
import com.madrid.data.dataSource.remote.dto.artist.SearchArtistResponse
import com.madrid.data.dataSource.remote.dto.authentication.AccountDetailsResponse
import com.madrid.data.dataSource.remote.dto.authentication.CreateSessionBody
import com.madrid.data.dataSource.remote.dto.authentication.CreateSessionRawBody
import com.madrid.data.dataSource.remote.dto.common.AddToFavoriteRequest
import com.madrid.data.dataSource.remote.dto.common.TrailerResult
import com.madrid.data.dataSource.remote.dto.genre.RemoteGenreDto
import com.madrid.data.dataSource.remote.dto.list.AddToListRequest
import com.madrid.data.dataSource.remote.dto.list.CreateListResponse
import com.madrid.data.dataSource.remote.dto.list.ListDto
import com.madrid.data.dataSource.remote.dto.list.ListOperationResponse
import com.madrid.data.dataSource.remote.dto.list.ListsDetailsResponse
import com.madrid.data.dataSource.remote.dto.list.MovieListBody
import com.madrid.data.dataSource.remote.dto.list.RemoveMovieDto
import com.madrid.data.dataSource.remote.dto.movie.MovieCreditsResponse
import com.madrid.data.dataSource.remote.dto.movie.MovieDetailsResponse
import com.madrid.data.dataSource.remote.dto.movie.MovieResult
import com.madrid.data.dataSource.remote.dto.movie.MovieReviewResponse
import com.madrid.data.dataSource.remote.dto.movie.NowPlayingMovieResponse
import com.madrid.data.dataSource.remote.dto.movie.SearchMovieResponse
import com.madrid.data.dataSource.remote.dto.movie.SimilarMoviesResponse
import com.madrid.data.dataSource.remote.dto.movie.UpcomingMoviesResponse
import com.madrid.data.dataSource.remote.dto.rate.RatingMovieResponse
import com.madrid.data.dataSource.remote.dto.rate.RatingSeriesResponse
import com.madrid.data.dataSource.remote.dto.rating.RateRequest
import com.madrid.data.dataSource.remote.dto.series.AiringTodayTvShowsResponse
import com.madrid.data.dataSource.remote.dto.series.OnAirTvShowsResponse
import com.madrid.data.dataSource.remote.dto.series.RecommendedSeriesResponse
import com.madrid.data.dataSource.remote.dto.series.SearchSeriesResponse
import com.madrid.data.dataSource.remote.dto.series.SeasonResponse
import com.madrid.data.dataSource.remote.dto.series.SeriesCreditResponse
import com.madrid.data.dataSource.remote.dto.series.SeriesDetailsResponse
import com.madrid.data.dataSource.remote.dto.series.SeriesResult
import com.madrid.data.dataSource.remote.dto.series.SeriesReviewResponse
import com.madrid.data.dataSource.remote.dto.series.SimilarSeriesResponse
import com.madrid.data.dataSource.remote.dto.series.TopRatedSeriesResponse
import com.madrid.data.dataSource.remote.utils.responseWrapper
import com.madrid.data.dataSource.remote.utils.retrofitResponseWrapper
import com.madrid.data.repositories.datasource.UserPreferences
import com.madrid.data.repositories.remote.RemoteDataSource
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class RemoteDataSourceImpl @Inject constructor(
    private val api: MovioApi,
    private val userPreferences: UserPreferences,
) : RemoteDataSource {
    //  region Movies
    override suspend fun searchMoviesByQuery(name: String, page: Int): SearchMovieResponse {
        return responseWrapper { api.searchMoviesByQuery(name, page) }
    }

    override suspend fun getTopRatedMovies(page: Int): SearchMovieResponse {
        return responseWrapper { api.getTopRatedMovies(page) }
    }

    override suspend fun getPopularMovies(page: Int): SearchMovieResponse {
        return responseWrapper { api.getPopularMovie(page) }
    }

    override suspend fun getMovieDetailsById(movieId: Int): MovieDetailsResponse {
        return responseWrapper { api.getMovieDetailsById(movieId) }
    }

    override suspend fun getMovieTrailersByMovieId(movieId: Int): List<TrailerResult> {
        return responseWrapper { api.getMovieTrailersById(movieId).results }
    }

    override suspend fun getMovieCreditById(movieId: Int): MovieCreditsResponse {
        return responseWrapper { api.getMovieCreditById(movieId) }
    }

    override suspend fun getMovieReviewsById(movieId: Int): MovieReviewResponse {
        return responseWrapper { api.getMovieReviewsById(movieId) }
    }

    override suspend fun getSimilarMoviesById(movieId: Int): SimilarMoviesResponse {
        return responseWrapper { api.getSimilarMoviesById(movieId) }
    }

    override suspend fun getMovieGenres(): List<RemoteGenreDto> {
        return responseWrapper { api.getMovieGenres().genres.orEmpty() }
    }

    override suspend fun getTrendingMovies(page: Int): SearchMovieResponse {
        return responseWrapper { api.getTrendingMovies(page = page) }
    }

    override suspend fun getMoviesByGenreId(
        page: Int,
        genreId: Int?,
        sortBy: String
    ): SearchMovieResponse {
        return responseWrapper { api.getMoviesByGenreId(page, genreId, sortBy) }
    }

    override suspend fun getFavoriteMovies(sessionId: String): List<MovieResult> {
        return responseWrapper { api.getFavoriteMovies(sessionId).movieResults }
    }
    // endregion

    // region Series
    override suspend fun getTopRatedSeries(page: Int): TopRatedSeriesResponse {
        return responseWrapper { api.getTopRatedSeries(page) }
    }

    override suspend fun searchSeriesByQuery(name: String, page: Int): SearchSeriesResponse {
        return responseWrapper { api.searchSeriesByQuery(name, page) }
    }

    override suspend fun getSeriesTrailersById(seriesId: Int): List<TrailerResult> {
        return responseWrapper { api.getSeriesTrailersById(seriesId).results }
    }

    override suspend fun getSeriesCreditsById(seriesId: Int): SeriesCreditResponse {
        return responseWrapper { api.getSeriesCreditsById(seriesId) }
    }

    override suspend fun getSeriesReviewsById(seriesId: Int): SeriesReviewResponse {
        return responseWrapper { api.getSeriesReviewsById(seriesId) }
    }

    override suspend fun getSimilarSeriesById(seriesId: Int): SimilarSeriesResponse {
        return responseWrapper { api.getSimilarSeriesById(seriesId) }
    }

    override suspend fun getEpisodesBySeasonId(
        seriesId: Int,
        seasonNumber: Int
    ): SeasonResponse {
        return responseWrapper { api.getEpisodesBySeasonId(seriesId, seasonNumber) }
    }

    override suspend fun getSeriesGenres(): List<RemoteGenreDto> {
        return responseWrapper { api.getSeriesGenres().genres.orEmpty() }
    }

    override suspend fun getTrendingSeries(page: Int): SearchSeriesResponse {
        return responseWrapper { api.getTrendingSeries(page = page) }
    }

    override suspend fun getSeriesDetailsById(seriesId: Int): SeriesDetailsResponse {
        return responseWrapper { api.getSeriesDetailsById(seriesId) }
    }

    override suspend fun getFavoriteSeries(sessionId: String): List<SeriesResult> {
        return responseWrapper { api.getFavoriteSeries(sessionId).seriesResults }
    }

    override suspend fun getEpisodeTrailers(
        episodeNumber: Int,
        seasonNumber: Int,
        seriesId: Int
    ): List<TrailerResult> {
        return responseWrapper {
            api.getEpisodeTrailers(
                seriesId,
                seasonNumber,
                episodeNumber
            ).results
        }
    }


    // Artist
    override suspend fun searchArtistByQuery(name: String, page: Int): SearchArtistResponse {
        return responseWrapper { api.searchArtistByQuery(name, page) }
    }

    override suspend fun getArtistDetailsById(artistId: Int): ArtistDetailsResponse {
        return responseWrapper { api.getArtistDetailsById(artistId) }
    }

    override suspend fun getArtistMovies(artistId: Int): List<KnownForMoviesNetwork> {
        return responseWrapper { api.getArtistKnownForById(artistId).movies ?: emptyList() }
    }

    override suspend fun getOnAirSeries(page: Int): OnAirTvShowsResponse {
        return responseWrapper { api.getOnTheAirTvShows(page = page) }
    }

    override suspend fun getAiringTodaySeries(page: Int): AiringTodayTvShowsResponse {
        return responseWrapper { api.getAiringTvShowsToday(page = page) }
    }

    override suspend fun getRecommendedSeries(page: Int): RecommendedSeriesResponse {
        return responseWrapper { api.getPopularTvShows(page = page) }
    }

    override suspend fun getUpcomingMovie(page: Int): UpcomingMoviesResponse {
        return responseWrapper { api.getUpcomingMovies(page) }
    }

    override suspend fun getNowPlayingMovie(page: Int): NowPlayingMovieResponse {
        return responseWrapper { api.getNowPlayingMovies(page) }
    }

    override suspend fun addRatingMovie(movieId: Int, value: Double) {
        return responseWrapper {
            api.addRatingForMovie(
                movieId = movieId,
                sessionId = userPreferences.getAuthToken().first(),
                body = RateRequest(value)
            )
        }
    }

    override suspend fun addRatingSeries(seriesId: Int, value: Double) {
        return responseWrapper {
            api.addRatingForSeries(
                seriesId = seriesId,
                sessionId = userPreferences.getAuthToken().first(),
                body = RateRequest(value)
            )
        }
    }

    override suspend fun getSeriesByGenreId(
        page: Int,
        genreId: Int?,
        sortBy: String
    ): SearchSeriesResponse {
        return responseWrapper { api.getSeriesByGenreId(page, genreId, sortBy) }
    }

    override suspend fun getCustomLists(sessionId: String): List<ListDto> {
        return responseWrapper { api.getCustomLists(sessionId).results }
    }

    override suspend fun getCustomListDetails(
        listId: Int,
        sessionId: String
    ): ListsDetailsResponse {
        return responseWrapper { api.getCustomListDetails(listId, sessionId) }
    }

    override suspend fun removeMovieFromList(
        listId: Int,
        mediaId: Int,
        sessionId: String
    ) {
        val request = RemoveMovieDto(
            mediaId = mediaId
        )

        responseWrapper {
            api.removeMovieFromList(
                listId = listId,
                sessionId = sessionId,
                removeItemRequest = request
            )
        }
    }

    override suspend fun login(username: String, password: String): String {
        return retrofitResponseWrapper {
            getSessionId(username, password)
        }
    }

    override suspend fun getSessionId(username: String, password: String): String {
        return retrofitResponseWrapper {
            val requestToken = api.getRequestToken().requestToken
            val validateResponse = api.postCreateSession(
                CreateSessionBody(username, password, requestToken)
            )
            api.createSession(CreateSessionRawBody(validateResponse.requestToken)).sessionId
        }
    }

    override suspend fun setMovieFavoriteStatus(
        movieId: Int,
        sessionId: String,
        isFavorite: Boolean
    ) {
        val request = AddToFavoriteRequest(
            mediaType = "movie",
            mediaId = movieId,
            favorite = isFavorite
        )
        val accountId = api.getAccountDetails(sessionId).id

        responseWrapper {
            api.addToFavorite(
                accountId = accountId,
                sessionId = sessionId,
                body = request
            )
        }
    }

    override suspend fun setSeriesFavoriteStatus(
        seriesId: Int,
        sessionId: String,
        isFavorite: Boolean
    ) {
        val request = AddToFavoriteRequest(
            mediaType = "tv",
            mediaId = seriesId,
            favorite = isFavorite
        )
        val accountId = api.getAccountDetails(sessionId).id

        responseWrapper {
            api.addToFavorite(
                accountId = accountId,
                sessionId = sessionId,
                body = request
            )
        }
    }

    override suspend fun loginAsGuest(): String {
        return retrofitResponseWrapper {
            val guestSessionResponse = api.getCreateGuestSession()
            guestSessionResponse.requestToken
        }
    }

    override suspend fun getCurrentUserDetails(sessionId: String): AccountDetailsResponse {
        return responseWrapper { api.getAccountDetails(sessionId) }
    }

    override suspend fun createMovieList(
        sessionId: String,
        movieListBody: MovieListBody
    ): CreateListResponse {
        return responseWrapper { api.createMovieList(sessionId, movieListBody) }
    }

    override suspend fun addMovieToList(
        listId: Int,
        movieId: Int,
        sessionId: String
    ): ListOperationResponse {
        val request = AddToListRequest(
            media_id = movieId,
            media_type = "movie"
        )

        return responseWrapper {
            api.addMovieToList(
                listId = listId,
                sessionId = sessionId,
                request = request
            )
        }
    }

    override suspend fun getUserRatingForMovie(sessionId: String): RatingMovieResponse {
        val accountId = api.getAccountDetails(sessionId).id
        return responseWrapper { api.getUserRatingForMovie(accountId, sessionId) }
    }

    override suspend fun getUserRatingForSeries(sessionId: String): RatingSeriesResponse {
        val accountId = api.getAccountDetails(sessionId).id
        return responseWrapper { api.getUserRatingForSeries(accountId, sessionId) }
    }
}
