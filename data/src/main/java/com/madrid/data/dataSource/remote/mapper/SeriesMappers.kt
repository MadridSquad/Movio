package com.madrid.data.dataSource.remote.mapper

import com.madrid.data.dataSource.remote.dto.series.AiringTodaySeriesResult
import com.madrid.data.dataSource.remote.dto.series.AiringTodayTvShowsResponse
import com.madrid.data.dataSource.remote.dto.series.EpisodeDto
import com.madrid.data.dataSource.remote.dto.series.OnAirTvShowsResponse
import com.madrid.data.dataSource.remote.dto.series.OnAirTvShowsResult
import com.madrid.data.dataSource.remote.dto.series.RecommendedSeriesResponse
import com.madrid.data.dataSource.remote.dto.series.RecommendedSeriesResult
import com.madrid.data.dataSource.remote.dto.series.SeasonsNetwork
import com.madrid.data.dataSource.remote.dto.series.SeriesCastNetwork
import com.madrid.data.dataSource.remote.dto.series.SeriesDetailsResponse
import com.madrid.data.dataSource.remote.dto.series.SeriesResult
import com.madrid.data.dataSource.remote.dto.series.SeriesReviewResult
import com.madrid.data.dataSource.remote.dto.series.SimilarSeriesNetwork
import com.madrid.data.dataSource.remote.dto.series.TopRatedSeriesResponse
import com.madrid.data.dataSource.remote.dto.series.TopRatedSeriesResults
import com.madrid.domain.entity.Artist
import com.madrid.domain.entity.Episode
import com.madrid.domain.entity.Genre
import com.madrid.domain.entity.Review
import com.madrid.domain.entity.Season
import com.madrid.domain.entity.Series

fun TopRatedSeriesResponse.toTvShows(): List<Series> {
    return this.results?.map { it ->
        it?.toSeries() ?: getDefaultSeries()
    } ?: emptyList()
}

fun OnAirTvShowsResponse.toTvShows(): List<Series> {
    return this.onAirTvShowsResults?.map {
        it?.toSeries() ?: getDefaultSeries()
    } ?: emptyList()
}

fun AiringTodayTvShowsResponse.toTvShows(): List<Series> {
    return this.airingTodaySeriesResult?.map {
        it?.toSeries() ?: getDefaultSeries()
    } ?: emptyList()
}

fun RecommendedSeriesResponse.toTvShows(): List<Series> {
    return this.recommendedSeriesResults?.map {
        it?.toSeries() ?: getDefaultSeries()
    } ?: emptyList()
}

fun SeriesResult.toSeries(
    genres : List<Genre> = emptyList(),
): Series {
    return Series(
        id = this.id ?: 0,
        title = this.title ?: "",
        imageUrl = "https://image.tmdb.org/t/p/original${this.posterPath}",
        rate = this.voteAverage ?: 0.0,
        airDate = this.releaseDate ?: "",
        description = this.overview ?: "",
        genre = genres,
        seasons = emptyList(),
    )
}


fun TopRatedSeriesResults.toSeries(): Series {
    return Series(
        id = this.id ?: 0,
        title = this.name ?: "",
        imageUrl = "https://image.tmdb.org/t/p/original${this.posterPath}",
        rate = this.voteAverage ?: 0.0,
        airDate = this.firstAirDate ?: "",
        description = this.overview ?: "",
        genre = this.genreIds?.map { Genre(id = it ?: 0, name = "") } ?: listOf(),
        seasons = emptyList(),
    )
}

fun OnAirTvShowsResult.toSeries(): Series {
    return Series(
        id = this.id ?: 0,
        title = this.name ?: "",
        imageUrl = "https://image.tmdb.org/t/p/original${this.posterPath}",
        rate = this.voteAverage ?: 0.0,
        airDate = "",
        description = this.overview ?: "",
        genre = this.genreIds?.map { Genre(id = it ?: 0, name = "") } ?: listOf(),
        seasons = emptyList(),
    )
}

fun AiringTodaySeriesResult.toSeries(): Series {
    return Series(
        id = this.id ?: 0,
        title = this.name ?: "",
        imageUrl = "https://image.tmdb.org/t/p/original${this.posterPath}",
        rate = this.voteAverage ?: 0.0,
        airDate = this.firstAirDate ?: "",
        description = this.overview ?: "",
        genre = this.genreIds?.map { Genre(id = it ?: 0, name = "") } ?: listOf(),
        seasons = emptyList(),
    )
}

fun RecommendedSeriesResult.toSeries(): Series {
    return Series(
        id = this.id ?: 0,
        title = this.name ?: "",
        imageUrl = "https://image.tmdb.org/t/p/original${this.posterPath}",
        rate = this.voteAverage ?: 0.0,
        airDate = this.firstAirDate ?: "",
        description = this.overview ?: "",
        genre = this.genreIds.map { Genre(id = it ?: 0, name = "") },
        seasons = emptyList(),
    )
}

fun SeriesDetailsResponse.toSeries(): Series {
    return Series(
        id = this.id ?: 0,
        title = this.name ?: "",
        imageUrl = "https://image.tmdb.org/t/p/original${this.posterPath}",
        rate = this.voteAverage ?: 0.0,
        airDate = this.firstAirDate ?: "",
        seasons = this.seasons?.map { it.toSeason() } ?: emptyList(),
        description = this.overview ?: "",
        genre = this.genres?.map { it.toGenre() } ?: emptyList(),
    )
}

fun SeriesCastNetwork.toArtist(): Artist {
    return Artist(
        id = this.id ?: 0,
        name = this.name ?: "",
        imageUrl = "https://image.tmdb.org/t/p/original${this.profilePath}",
        role = this.knownForDepartment ?: "",
        dateOfBirth = "",
        country = "",
        overview = "",
    )
}

fun SeriesReviewResult.toReview(): Review {
    return Review(
        reviewId = this.id ?: "",
        reviewerName = this.author ?: "",
        reviewerPhotoUrl = "https://image.tmdb.org/t/p/original${this.authorDetails?.avatarPath}",
        rate = this.authorDetails?.rating ?: 0.0,
        date = this.createdAt ?: "",
        comment = this.content ?: "",
    )
}

fun SimilarSeriesNetwork.toSimilarSeries(): Series {
    return Series(
        id = this.id ?: 0,
        title = this.name ?: "",
        imageUrl = "https://image.tmdb.org/t/p/original${this.posterPath}",
        rate = this.voteAverage ?: 0.0,
        airDate = this.releaseDate ?: "",
        seasons = emptyList(),
        description = this.overview ?: "",
        genre = emptyList(),
    )
}

fun SeasonsNetwork.toSeason(): Season {
    return Season(
        id = this.id ?: 0,
        seasonNumber = this.seasonNumber ?: 0,
        imageUrl = "https://image.tmdb.org/t/p/original${this.posterPath}",
        rate = this.voteAverage ?: 0.0,
        date = this.airDate ?: "",
        description = this.overview ?: "",
        episodeCount = this.episodeCount ?: 0,
    )

}

fun EpisodeDto.toEpisode(): Episode {
    return Episode(
        id = this.id ?: 0,
        title = this.name ?: "",
        rate = this.voteAverage ?: 0.0,
        episodeNumber = this.episodeNumber ?: 0,
        imageUrl = "https://image.tmdb.org/t/p/original${this.stillPath}",
        duration = this.runtime?.toString() ?: "",
    )
}

private fun getDefaultSeries(): Series {
    return Series(
        id = 0,
        title = "",
        imageUrl = "",
        rate = 0.0,
        airDate = "",
        description = "",
        genre = listOf(),
        seasons = emptyList(),
    )
}