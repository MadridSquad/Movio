package com.madrid.data.dataSource.remote

import com.madrid.data.dataSource.remote.dto.artist.ArtistDetailsResponse
import com.madrid.data.dataSource.remote.dto.artist.ArtistKnownForResponse
import com.madrid.data.dataSource.remote.dto.artist.SearchArtistResponse
import com.madrid.data.dataSource.remote.dto.authentication.AccountDetailsResponse
import com.madrid.data.dataSource.remote.dto.authentication.AuthenticationResponse
import com.madrid.data.dataSource.remote.dto.authentication.CreateSessionBody
import com.madrid.data.dataSource.remote.dto.authentication.CreateSessionRawBody
import com.madrid.data.dataSource.remote.dto.authentication.SessionIdResponse
import com.madrid.data.dataSource.remote.dto.common.AddToFavoriteRequest
import com.madrid.data.dataSource.remote.dto.common.TrailerResponse
import com.madrid.data.dataSource.remote.dto.genre.GenresResponse
import com.madrid.data.dataSource.remote.dto.list.AddToListRequest
import com.madrid.data.dataSource.remote.dto.list.CreateListResponse
import com.madrid.data.dataSource.remote.dto.list.ListOperationResponse
import com.madrid.data.dataSource.remote.dto.list.ListsDetailsResponse
import com.madrid.data.dataSource.remote.dto.list.ListsResponse
import com.madrid.data.dataSource.remote.dto.list.MovieListBody
import com.madrid.data.dataSource.remote.dto.list.RemoveMovieDto
import com.madrid.data.dataSource.remote.dto.movie.ListDetailsResponse
import com.madrid.data.dataSource.remote.dto.movie.MovieCreditsResponse
import com.madrid.data.dataSource.remote.dto.movie.MovieDetailsResponse
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
import com.madrid.data.dataSource.remote.dto.series.SeriesReviewResponse
import com.madrid.data.dataSource.remote.dto.series.SimilarSeriesResponse
import com.madrid.data.dataSource.remote.dto.series.TopRatedSeriesResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovioApi {

    // region Movies
    @GET("search/movie")
    suspend fun searchMoviesByQuery(
        @Query("query") name: String,
        @Query("page") page: Int
    ): SearchMovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int
    ): SearchMovieResponse

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("page") page: Int
    ): SearchMovieResponse
// endregion

    // region MovieDetails
    @GET("movie/{movie_id}")
    suspend fun getMovieDetailsById(
        @Path("movie_id") movieId: Int
    ): MovieDetailsResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieTrailersById(
        @Path("movie_id") movieId: Int
    ): TrailerResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCreditById(
        @Path("movie_id") movieId: Int
    ): MovieCreditsResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviewsById(
        @Path("movie_id") movieId: Int
    ): MovieReviewResponse

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMoviesById(
        @Path("movie_id") movieId: Int
    ): SimilarMoviesResponse

    @GET("genre/movie/list")
    suspend fun getMovieGenres(): GenresResponse

    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovies(
        @Path("time_window") timeWindow: String = "day",
        @Query("page") page: Int
    ): SearchMovieResponse

    @GET(DISCOVER_MOVIE)
    suspend fun getMoviesByGenreId(
        @Query(PAGE) page: Int,
        @Query(WITH_GENRES) genreId: Int?,
        @Query(SORT_BY) sortBy: String
    ): SearchMovieResponse
    // endregion

    // region Series
    @GET("search/tv")
    suspend fun searchSeriesByQuery(
        @Query("query") name: String,
        @Query("page") page: Int
    ): SearchSeriesResponse

    @GET("tv/{series_id}")
    suspend fun getSeriesDetailsById(
        @Path("series_id") seriesId: Int
    ): SeriesDetailsResponse

    @GET("tv/{series_id}/videos")
    suspend fun getSeriesTrailersById(
        @Path("series_id") seriesId: Int
    ): TrailerResponse

    @GET("tv/{series_id}/credits")
    suspend fun getSeriesCreditsById(
        @Path("series_id") seriesId: Int
    ): SeriesCreditResponse

    @GET("tv/{series_id}/reviews")
    suspend fun getSeriesReviewsById(
        @Path("series_id") seriesId: Int
    ): SeriesReviewResponse

    @GET("tv/{series_id}/similar")
    suspend fun getSimilarSeriesById(
        @Path("series_id") seriesId: Int
    ): SimilarSeriesResponse

    @GET("tv/{series_id}/season/{season_number}")
    suspend fun getEpisodesBySeasonId(
        @Path("series_id") seriesId: Int,
        @Path("season_number") seasonNumber: Int
    ): SeasonResponse

    @GET("trending/tv/{time_window}")
    suspend fun getTrendingSeries(
        @Path("time_window") timeWindow: String = "day",
        @Query("page") page: Int
    ): SearchSeriesResponse

    @GET("tv/top_rated")
    suspend fun getTopRatedSeries(
        @Query("page") page: Int
    ): TopRatedSeriesResponse

    @GET("tv/airing_today")
    suspend fun getAiringTvShowsToday(
        @Query("page") page: Int
    ): AiringTodayTvShowsResponse

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTvShows(
        @Query("page") page: Int
    ): OnAirTvShowsResponse

    @GET("tv/popular")
    suspend fun getPopularTvShows(
        @Query("page") page: Int
    ): RecommendedSeriesResponse

    @GET("genre/tv/list")
    suspend fun getSeriesGenres(): GenresResponse

    @GET(DISCOVER_MOVIE)
    suspend fun getSeriesByGenreId(
        @Query(PAGE) page: Int,
        @Query(WITH_GENRES) genreId: Int?,
        @Query(SORT_BY) sortBy: String
    ): SearchSeriesResponse

    @GET("tv/{series_id}/season/{season_number}/episode/{episode_number}/videos")
    suspend fun getEpisodeTrailers(
        @Path("series_id") seriesId: Int,
        @Path("season_number") seasonNumber: Int,
        @Path("episode_number") episodeNumber: Int
    ): TrailerResponse
    // endregion


    // region Artist
    @GET("search/person")
    suspend fun searchArtistByQuery(
        @Query("query") name: String,
        @Query("page") page: Int
    ): SearchArtistResponse

    @GET("person/{artist_id}")
    suspend fun getArtistDetailsById(
        @Path("artist_id") artistId: Int
    ): ArtistDetailsResponse

    @GET("person/{person_id}/movie_credits")
    suspend fun getArtistKnownForById(
        @Path("person_id") artistId: Int
    ): ArtistKnownForResponse

    // region authentication
    @GET("authentication/token/new")
    suspend fun getRequestToken(): AuthenticationResponse       // first

    @POST("authentication/token/validate_with_login")       // second
    suspend fun postCreateSession(
        @Body body: CreateSessionBody
    ): AuthenticationResponse

    @POST("authentication/session/new")                     // third
    suspend fun createSession(
        @Body body: CreateSessionRawBody
    ): SessionIdResponse


    @GET("authentication/guest_session/new")
    suspend fun getCreateGuestSession(): AuthenticationResponse

    @GET("account")
    suspend fun getAccountDetails(
        @Query("session_id") sessionId: String          // fourth
    ): AccountDetailsResponse
    // endregion

    // region home movies
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int
    ): NowPlayingMovieResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int
    ): UpcomingMoviesResponse
    // endregion

    // region add rating
    @POST("movie/{movie_id}/rating")
    suspend fun addRatingForMovie(
        @Path("movie_id") movieId: Int,
        @Query("session_id") sessionId: String,
        @Body body: RateRequest
    )

    @POST("tv/{series_id}/rating")
    suspend fun addRatingForSeries(
        @Path("series_id") seriesId: Int,
        @Query("session_id") sessionId: String,
        @Body body: RateRequest
    )
    // endregion

    @GET("list/{list_id}")
    suspend fun getListDetails(
        @Path("list_id") listId: Int,
        @Query("session_id") sessionId: String
    ): ListDetailsResponse

    @POST("list")
    suspend fun createMovieList(
        @Query("session_id") sessionId: String,
        @Body body: MovieListBody
    ): CreateListResponse

    @POST("list/{list_id}/add_item")
    suspend fun addMovieToList(
        @Path("list_id") listId: Int,
        @Query("session_id") sessionId: String,
        @Body request: AddToListRequest
    ): ListOperationResponse
    // region List

    @GET("account/account_id/lists")
    suspend fun getCustomLists(
        @Query("session_id") sessionId: String,
    ): ListsResponse

    @GET("list/{list_id}")
    suspend fun getCustomListDetails(
        @Path("list_id") listId: Int,
        @Query("session_id") sessionId: String
    ): ListsDetailsResponse
    // region EpisodeRating

    @GET("account/{account_id}/rated/movies")
    suspend fun getUserRatingForMovie(
        @Path("account_id") accountId: Int? = null,
        @Query("session_id") sessionId: String
    ): RatingMovieResponse

    @GET("account/{account_id}/rated/tv")
    suspend fun getUserRatingForSeries(
        @Path("account_id") accountId: Int? = null,
        @Query("session_id") sessionId: String
    ): RatingSeriesResponse

    // endregion

    @GET(FAVORITE_MOVIES)
    suspend fun getFavoriteMovies(
        @Query(SESSION_ID) sessionId: String,
    ): SearchMovieResponse

    @GET(FAVORITE_SERIES)
    suspend fun getFavoriteSeries(
        @Query(SESSION_ID) sessionId: String,
    ): SearchSeriesResponse

    @POST("account/{account_id}/favorite")
    suspend fun addToFavorite(
        @Path("account_id") accountId: Int? = null,
        @Query("session_id") sessionId: String,
        @Body body: AddToFavoriteRequest
    )

    @POST("list/{list_id}/remove_item")
    suspend fun removeMovieFromList(
        @Path("list_id") listId: Int,
        @Query("session_id") sessionId: String,
        @Body removeItemRequest: RemoveMovieDto
    )

    companion object {
        private const val DISCOVER_MOVIE = "discover/movie"
        private const val PAGE = "page"

        private const val SESSION_ID = "session_id"

        private const val WITH_GENRES = "with_genres"
        private const val SORT_BY = "sort_by"

        private const val FAVORITE_MOVIES = "account/account_id/favorite/movies"
        private const val FAVORITE_SERIES = "account/account_id/favorite/tv"

    }
}