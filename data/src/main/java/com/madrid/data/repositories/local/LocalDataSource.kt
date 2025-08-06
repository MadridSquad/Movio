package com.madrid.data.repositories.local

import com.madrid.data.dataSource.local.table.ArtistTable
import com.madrid.data.dataSource.local.table.MovieGenreTable
import com.madrid.data.dataSource.local.table.MovieTable
import com.madrid.data.dataSource.local.table.RecentSearchTable
import com.madrid.data.dataSource.local.table.SectionsMovieTable
import com.madrid.data.dataSource.local.table.SeriesTable
import com.madrid.data.dataSource.local.table.SeriesGenreTable
import com.madrid.data.dataSource.local.table.relationship.GenreWithMovies
import com.madrid.data.dataSource.local.table.relationship.GenreWithSeries
import com.madrid.data.dataSource.local.table.relationship.MovieGenreCrossRef
import com.madrid.data.dataSource.local.table.relationship.MovieWithGenres
import com.madrid.data.dataSource.local.table.relationship.SeriesGenreCrossRef
import com.madrid.data.dataSource.local.table.relationship.SeriesWithGenres

interface LocalDataSource {

    suspend fun insertMovie(movie: MovieTable)
    suspend fun insertSectionMovie(movie: SectionsMovieTable)
    suspend fun insertSeries(series: SeriesTable)
    suspend fun insertArtist(artist: ArtistTable)
    suspend fun insertMovieGenre(genre: MovieGenreTable)
    suspend fun insertSeriesGenre(genre: SeriesGenreTable)

    suspend fun getTopRatedMovies(): List<MovieTable>

    suspend fun searchMovieByQueryFromDB(query: String, page: Int): List<MovieWithGenres>
    suspend fun searchSeriesByQueryFromDB(query: String, page: Int): List<SeriesWithGenres>
    suspend fun searchArtistByQueryFromDB(query: String, page: Int): List<ArtistTable>

    suspend fun getRecentSearches(): List<RecentSearchTable>
    suspend fun addRecentSearch(item: String)
    suspend fun removeRecentSearch(item: String)
    suspend fun clearAllRecentSearches()

    suspend fun relateMovieToGenre(movieGenreCrossRef: MovieGenreCrossRef)
    suspend fun increaseMovieGenreInterestPoints(genreTitle: String)

    suspend fun relateSeriesToGenre(seriesGenreEntity: SeriesGenreCrossRef)
    suspend fun increaseSeriesGenreInterestPoints(genreTitle: String)

    suspend fun getAllMovieGenres(): List<MovieGenreTable>
    suspend fun getAllSeriesGenres(): List<SeriesGenreTable>

    suspend fun getMoviesByGenres(): List<GenreWithMovies>
    suspend fun getSeriesByGenres(): List<GenreWithSeries>

    suspend fun getNowPlayingMovies(): List<MovieTable>
    suspend fun getUpComingMovies(): List<MovieTable>

    suspend fun clearHomeMoviesCache()
}