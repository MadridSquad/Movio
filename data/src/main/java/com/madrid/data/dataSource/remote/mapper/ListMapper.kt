package com.madrid.data.dataSource.remote.mapper

import com.madrid.data.dataSource.local.mappers.toGenre
import com.madrid.data.dataSource.local.table.MovieGenreTable
import com.madrid.data.dataSource.local.table.SeriesGenreTable
import com.madrid.data.dataSource.remote.dto.list.ListDto
import com.madrid.data.dataSource.remote.dto.list.ListItemDto
import com.madrid.domain.entity.Genre
import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Series
import com.madrid.domain.entity.WatchList
import com.madrid.domain.usecase.watchList.GetWatchListItemsUseCase


fun ListDto.toWatchList(): WatchList {
    return WatchList(
        id = id,
        name = name ?: "",
        description = description,
        itemCount = itemCount,
        posterPath = posterPath
    )
}

fun ListItemDto.toMovie(genres: List<Genre>): Movie {
    return Movie(
        id = id,
        title = title ?: "",
        imageUrl = posterPath ?: "",
        rate = voteAverage,
        releaseDate = releaseDate ?: "",
        movieDuration = "",
        description = overview ?: "",
        genre = genres,
    )
}

fun ListItemDto.toSeries(genres: List<Genre>): Series {
    return Series(
        id = id,
        title = title ?: "",
        imageUrl = posterPath ?: "",
        rate = voteAverage,
        description = overview ?: "",
        airDate = "",
        seasons = emptyList(),
        genre = genres,
    )
}


fun List<ListItemDto>.toWatchListItems(
    moviesGenres: Map<Int, Genre>,
    seriesGenres: Map<Int, Genre>
): GetWatchListItemsUseCase.WatchListItems {
    val movies = this.filter { media ->
        media.mediaType == "movie"
    }.map { movie ->
        val genres = movie.genreIds.mapNotNull { moviesGenres[it] }
        movie.toMovie(genres)
    }

    val series = this.filter { media ->
        media.mediaType == "tv"
    }.map { series ->
        val genres = series.genreIds.mapNotNull { seriesGenres[it]}
        series.toSeries(genres)
    }
    return GetWatchListItemsUseCase.WatchListItems(movies, series)
}
