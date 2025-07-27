package com.madrid.data.dataSource.remote

import com.madrid.data.dataSource.remote.response.artist.ArtistDetailsResponse
import com.madrid.data.dataSource.remote.response.artist.ArtistKnownForResponse
import com.madrid.data.dataSource.remote.response.artist.SearchArtistResponse
import com.madrid.data.dataSource.remote.response.common.TrailerResponse
import com.madrid.data.dataSource.remote.response.genre.GenresResponse
import com.madrid.data.dataSource.remote.response.movie.MovieCreditsResponse
import com.madrid.data.dataSource.remote.response.movie.MovieDetailsResponse
import com.madrid.data.dataSource.remote.response.movie.MovieReviewResponse
import com.madrid.data.dataSource.remote.response.movie.SearchMovieResponse
import com.madrid.data.dataSource.remote.response.movie.SimilarMoviesResponse
import com.madrid.data.dataSource.remote.response.series.SearchSeriesResponse
import com.madrid.data.dataSource.remote.response.series.SeasonEpisodesResponse
import com.madrid.data.dataSource.remote.response.series.SeriesCreditResponse
import com.madrid.data.dataSource.remote.response.series.SeriesDetailsResponse
import com.madrid.data.dataSource.remote.response.series.SeriesReviewResponse
import com.madrid.data.dataSource.remote.response.series.SimilarSeriesResponse
import com.madrid.data.dataSource.remote.response.trending.AllTrendingResponse
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

    override suspend fun getPopularMovie(page: Int): SearchMovieResponse {
        return api.getPopularMovie(page)
    }
    // endregion

    override suspend fun getTopRatedSeries(query: String, page: Int): SearchSeriesResponse {
        return api.searchSeriesByQuery(query, page)
    }


    override suspend fun getMovieDetailsById(movieId: Int): MovieDetailsResponse {
        return api.getMovieDetailsById(movieId)
    }

    override suspend fun getMovieTrailersById(movieId: Int): TrailerResponse {
        return api.getMovieTrailersById(movieId)
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

    // Series
    override suspend fun searchSeriesByQuery(name: String, page: Int): SearchSeriesResponse {
        return api.searchSeriesByQuery(name, page)
    }

    override suspend fun getSeriesTrailersById(seriesId: Int): TrailerResponse {
        return api.getSeriesTrailersById(seriesId)
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
    ): SeasonEpisodesResponse {
        return api.getEpisodesBySeasonId(seriesId, seasonNumber)
    }

    override suspend fun getSeriesGenres(): GenresResponse {
        return api.getSeriesGenres()
    }

    // Artist
    override suspend fun searchArtistByQuery(name: String, page: Int): SearchArtistResponse {
        return api.searchArtistByQuery(name, page)
    }

    override suspend fun getArtistDetailsById(artistId: Int): ArtistDetailsResponse {
        return api.getArtistDetailsById(artistId)
    }

    override suspend fun getArtistKnownForById(artistId: Int): ArtistKnownForResponse {
        return api.getArtistKnownForById(artistId)
    }


    override suspend fun searchMoviesByQuery(name: String): SearchMovieResponse {
        return api.searchMoviesByQuery(name, 1)
    }

    override suspend fun searchSeriesByQuery(name: String): SearchSeriesResponse {
        return api.searchSeriesByQuery(name, 1)
    }

    override suspend fun getTopRatedMovies(query: String, page: Int): SearchMovieResponse {
        return api.getTopRatedMovies(page)
    }


    override suspend fun getSeriesDetailsById(seriesId: Int): SeriesDetailsResponse {
        return api.getSeriesDetailsById(seriesId)
    }

    override suspend fun getArtistById(artistId: Int): ArtistDetailsResponse {
        val response = api.getArtistDetailsById(artistId)
        val result = response
        return result
    }

    // region Trending
    override suspend fun getAllTrending(page: Int): AllTrendingResponse {
        return api.getAllTrending(page = page)
    }
    // endregion
}