package com.madrid.data.repositories

import com.madrid.data.dataSource.local.entity.relationship.MovieGenreCrossRef
import com.madrid.data.dataSource.local.entity.relationship.SeriesGenreCrossRef
import com.madrid.data.dataSource.local.mappers.toArtist
import com.madrid.data.dataSource.local.mappers.toArtistEntity
import com.madrid.data.dataSource.local.mappers.toMovieGenreEntity
import com.madrid.data.dataSource.local.mappers.toMovie
import com.madrid.data.dataSource.local.mappers.toMovieEntity
import com.madrid.data.dataSource.local.mappers.toSeries
import com.madrid.data.dataSource.local.mappers.toSeriesEntity
import com.madrid.data.dataSource.local.mappers.toSeriesGenreEntity
import com.madrid.data.dataSource.remote.mapper.toMovie
import com.madrid.data.dataSource.remote.mapper.toSeries
import com.madrid.data.repositories.local.LocalDataSource
import com.madrid.data.repositories.remote.RemoteDataSource
import com.madrid.domain.entity.Artist
import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Series
import com.madrid.domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localSource: LocalDataSource
) : SearchRepository {


    override suspend fun getMovieByQuery(query: String, page: Int): List<Movie> {
        var result = localSource.searchMovieByQueryFromDB(query, page)
        if (result.size < 7) {
            localSource.getAllMovieGenres().ifEmpty {
                remoteDataSource.getMovieGenres().genres?.map {
                    localSource.insertMovieGenre(it.toMovieGenreEntity())
                }
            }
            remoteDataSource.searchMoviesByQuery(
                name = query,
                page = page
            ).movieResults?.map { movieResult ->
                movieResult.genreIds?.map { genreId ->
                    localSource.relateMovieToGenre(
                        MovieGenreCrossRef(
                            movieId = movieResult.id ?: 0,
                            genreId = genreId
                        )
                    )
                }
                localSource.insertMovie(movieResult.toMovieEntity())
            }
            result = localSource.searchMovieByQueryFromDB(query, page)
        }
        return result.map { it.toMovie() }
    }

    override suspend fun getSeriesByQuery(query: String, page: Int): List<Series> {
        var result = localSource.searchSeriesByQueryFromDB(query, page)
        if (result.size < 7) {
            localSource.getAllSeriesGenres().ifEmpty {
                remoteDataSource.getSeriesGenres().genres?.map {
                    localSource.insertSeriesGenre(it.toSeriesGenreEntity())
                }
            }
            remoteDataSource.searchSeriesByQuery(
                name = query,
                page = page
            ).seriesResults?.map { seriesResult ->
                seriesResult.genreIds?.map { genreId ->
                    localSource.relateSeriesToGenre(
                        SeriesGenreCrossRef(
                            seriesId = seriesResult.id ?: 0,
                            genreId = genreId
                        )
                    )
                }
                localSource.insertSeries(seriesResult.toSeriesEntity())
            }
            result = localSource.searchSeriesByQueryFromDB(query, page)
        }
        return result.map { it.toSeries() }
    }

    override suspend fun getArtistByQuery(query: String, page: Int): List<Artist> {
        val result = localSource.searchArtistByQueryFromDB(query, page)
        if (result.size < 7) {
            remoteDataSource.searchArtistByQuery(
                name = query,
                page = page
            ).artistResults?.map {
                localSource.insertArtist(it.toArtistEntity())
            }
        }
        return localSource.searchArtistByQueryFromDB(query, page).map {
            it.toArtist()
        }
    }


    override suspend fun getTopRatedMovies(query: String, page: Int): List<Movie> {
        val res = remoteDataSource.getTopRatedMovies(
            query = query,
            page = page
        ).movieResults?.map {
            it.toMovie()
        } ?: listOf()
        return res
    }

    override suspend fun getTopRatedSeries(query: String, page: Int): List<Series> {
        val res = remoteDataSource.getTopRatedSeries(
            query = query,
            page = page
        ).seriesResults?.map {
            it.toSeries()
        } ?: listOf()
        return res
    }


    override suspend fun getRecommendedMovie(): List<Movie> {
        return emptyList()
    }

    override suspend fun getPopularMovie(page: Int): List<Movie> {
        val movies = remoteDataSource.getPopularMovie(
            page = page
        ).movieResults
        movies?.map { movie ->
            localSource.insertMovie(movie.toMovieEntity())
            movie.genreIds?.map { genreId ->
                localSource.relateMovieToGenre(
                    MovieGenreCrossRef(
                        movieId = movie.id ?: 0,
                        genreId = genreId
                    )
                )
            }
        }
        return movies?.map { it.toMovie() } ?: emptyList()
    }


    override suspend fun getRecentSearches(): List<String> {
        return localSource.getRecentSearches().map {
            it.searchQuery
        }
    }

    override suspend fun addRecentSearchByQuery(query: String) {
        localSource.addRecentSearch(query)
    }

    override suspend fun removeRecentSearchByQuery(query: String) {
        localSource.removeRecentSearch(query)
    }

    override suspend fun clearAllRecentSearches() {
        localSource.clearAllRecentSearches()
    }
}