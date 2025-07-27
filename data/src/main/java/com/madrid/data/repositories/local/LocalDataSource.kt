package com.madrid.data.repositories.local

import com.madrid.data.dataSource.local.entity.ArtistEntity
import com.madrid.data.dataSource.local.entity.MovieGenreEntity
import com.madrid.data.dataSource.local.entity.MovieEntity
import com.madrid.data.dataSource.local.entity.RecentSearchEntity
import com.madrid.data.dataSource.local.entity.SeriesEntity
import com.madrid.data.dataSource.local.entity.SeriesGenreEntity
import com.madrid.data.dataSource.local.entity.relationship.GenreWithMovies
import com.madrid.data.dataSource.local.entity.relationship.GenreWithSeries
import com.madrid.data.dataSource.local.entity.relationship.MovieGenreCrossRef
import com.madrid.data.dataSource.local.entity.relationship.SeriesGenreCrossRef

interface LocalDataSource {

    suspend fun insertMovie(movie: MovieEntity)
    suspend fun insertSeries(series: SeriesEntity)
    suspend fun insertArtist(artist: ArtistEntity)
    suspend fun insertMovieGenre(genre: MovieGenreEntity)
    suspend fun insertSeriesGenre(genre: SeriesGenreEntity)

    suspend fun getTopRatedMovies(): List<MovieEntity>

    suspend fun searchMovieByQueryFromDB(query: String, page: Int): List<MovieEntity>
    suspend fun searchSeriesByQueryFromDB(query: String, page: Int): List<SeriesEntity>
    suspend fun searchArtistByQueryFromDB(query: String, page: Int): List<ArtistEntity>

    suspend fun getRecentSearches(): List<RecentSearchEntity>
    suspend fun addRecentSearch(item: String)
    suspend fun removeRecentSearch(item: String)
    suspend fun clearAllRecentSearches()

    suspend fun relateMovieToGenre(movieGenreCrossRef: MovieGenreCrossRef)
    suspend fun increaseMovieGenreSeenCount(genreTitle: String)

    suspend fun relateSeriesToGenre(seriesGenreEntity: SeriesGenreCrossRef)
    suspend fun increaseSeriesGenreSeenCount(genreTitle: String)

    suspend fun getAllMovieGenres(): List<MovieGenreEntity>
    suspend fun getAllSeriesGenres(): List<SeriesGenreEntity>

    suspend fun getMoviesByGenres(): List<GenreWithMovies>
    suspend fun getSeriesByGenres(): List<GenreWithSeries>
}