package com.madrid.data.dataSource.remote

import com.madrid.data.dataSource.remote.dto.artist.ArtistDetailsResponse
import com.madrid.data.dataSource.remote.dto.artist.ArtistKnownForResponse
import com.madrid.data.dataSource.remote.dto.artist.SearchArtistResponse
import com.madrid.data.dataSource.remote.dto.common.TrailerResponse
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
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

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
    // endregion
}