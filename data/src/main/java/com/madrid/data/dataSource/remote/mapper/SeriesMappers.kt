package com.madrid.data.dataSource.remote.mapper

import com.madrid.data.dataSource.remote.dto.series.EpisodeDto
import com.madrid.data.dataSource.remote.dto.series.SearchSeriesResponse
import com.madrid.data.dataSource.remote.dto.series.SeasonsNetwork
import com.madrid.data.dataSource.remote.dto.series.SeriesCastNetwork
import com.madrid.data.dataSource.remote.dto.series.SeriesDetailsResponse
import com.madrid.data.dataSource.remote.dto.series.SeriesGenres
import com.madrid.data.dataSource.remote.dto.series.SeriesResult
import com.madrid.data.dataSource.remote.dto.series.SeriesReviewResult
import com.madrid.data.dataSource.remote.dto.series.SimilarSeriesNetwork
import com.madrid.data.dataSource.remote.response.series.AiringTodaySeriesResult
import com.madrid.data.dataSource.remote.response.series.AiringTodayTvShowsResponse
import com.madrid.data.dataSource.remote.response.series.OnAirTvShowsResponse
import com.madrid.data.dataSource.remote.response.series.OnAirTvShowsResult
import com.madrid.data.dataSource.remote.response.series.RecommendedSeriesResponse
import com.madrid.data.dataSource.remote.response.series.RecommendedSeriesResult
import com.madrid.data.dataSource.remote.response.series.TopRatedSeriesResponse
import com.madrid.data.dataSource.remote.response.series.TopRatedSeriesResults
import com.madrid.domain.entity.Artist
import com.madrid.domain.entity.Episode
import com.madrid.domain.entity.Review
import com.madrid.domain.entity.Season
import com.madrid.domain.entity.Series

fun SearchSeriesResponse.toTvShows(): List<Series> {
    return this.seriesResults?.map {
        it.toSeries()
    } ?: emptyList()
}

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

fun SeriesResult.toSeries(): Series {
    return Series(
        id = this.id ?: 0,
        title = this.title ?: "",
        imageUrl = "https://image.tmdb.org/t/p/original${this.posterPath}",
        rate = this.popularity ?: 0.0,
        airDate = this.releaseDate ?: "",
        description = this.overview ?: "",
        genre = listOf(),
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
        genre = listOf(),
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
        genre = listOf(),
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
        genre = listOf(),
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
        genre = listOf(),
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
        genre = this.genres?.map { it.toMediaGenre().title } ?: emptyList(),
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

fun SeriesGenres.toMediaGenre(): MediaGenre {
    return MediaGenre(
        id = this.id ?: 0,
        title = this.name ?: ""
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