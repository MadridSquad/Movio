package com.madrid.data.dataSource.remote.mapper

import android.util.Log
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
        itemCount = itemCount,
        description = description ?: "",
        posterUrl = posterPath,
        isSelected = false,
        isLoading = false
    )
}

fun ListItemDto.toMovie(genres: List<Genre>): Movie {
    return Movie(
        id = id,
        title = originalTitle ?: "no name",
        imageUrl = "https://image.tmdb.org/t/p/original/$posterPath" ?: "",
        rate = voteAverage,
        releaseDate = releaseDate ?: "",
        movieDuration = "",
        description = overview ?: "",
        genre = genres,
    )
}


fun ListItemDto.toSeries(genres: List<Genre>): Series {
    Log.i("MY_TAG","original title ${originalTitle.toString()}   title${title.toString()}")
    return Series(
        id = id,
        title = originalTitle ?: "no name",
        imageUrl = "https://image.tmdb.org/t/p/original/$posterPath" ?: "",
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
    val movies = this
        .filter { media -> media.mediaType == "movie" }
        .map { movieDto ->
            val genres = movieDto.genreIds.mapNotNull { genreId ->
                moviesGenres[genreId]
            }
            movieDto.toMovie(genres)
        }

    val series = this
        .filter { media -> media.mediaType == "tv" }
        .map { seriesDto ->
            val genres = seriesDto.genreIds.mapNotNull { genreId ->
                seriesGenres[genreId]
            }
            seriesDto.toSeries(genres)
        }

    return GetWatchListItemsUseCase.WatchListItems(
        movies = movies,
        series = series
    )
}