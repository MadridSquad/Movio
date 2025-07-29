package com.madrid.data.dataSource.remote

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
import com.madrid.data.repositories.remote.RemoteDataSource

class RemoteDataSourceImpl(
    private val api: MovieApi
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

    override suspend fun getMovieGenres(): GenresResponse {
        return api.getMovieGenres()
    }

    override suspend fun getTrendingMovies(page: Int): SearchMovieResponse {
        return api.getTrendingMovies(page = page)
    }
    // endregion

    // region Series
    override suspend fun getTopRatedSeries(page: Int): TopRatedSeriesResponse {
        return api.getTopRatedSeries(page)
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

    override suspend fun getSeriesGenres(): GenresResponse {
        return api.getSeriesGenres()
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
}