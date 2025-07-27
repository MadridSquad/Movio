package com.madrid.data.dataSource.remote.mapper


import com.madrid.data.dataSource.remote.response.series.EpisodeNetwork
import com.madrid.data.dataSource.remote.response.series.SearchSeriesResponse
import com.madrid.data.dataSource.remote.response.series.SeasonEpisodesResponse
import com.madrid.data.dataSource.remote.response.series.SeasonsNetwork
import com.madrid.data.dataSource.remote.response.series.SeriesCastNetwork
import com.madrid.data.dataSource.remote.response.series.SeriesCreditResponse
import com.madrid.data.dataSource.remote.response.series.SeriesDetailsResponse
import com.madrid.data.dataSource.remote.response.series.SeriesGenres
import com.madrid.data.dataSource.remote.response.series.SeriesResult
import com.madrid.data.dataSource.remote.response.series.SeriesReviewResponse
import com.madrid.data.dataSource.remote.response.series.SeriesReviewResult
import com.madrid.data.dataSource.remote.response.series.SimilarSeriesNetwork
import com.madrid.data.dataSource.remote.response.series.SimilarSeriesResponse
import com.madrid.domain.entity.Cast
import com.madrid.domain.entity.Episode
import com.madrid.domain.entity.Review
import com.madrid.domain.entity.ReviewResult
import com.madrid.domain.entity.Season
import com.madrid.domain.entity.Series
import com.madrid.domain.entity.SimilarSeries


// region Search
fun SearchSeriesResponse.toSearchResult(): SearchResult {
    return SearchResult(
        page = this.page,
        searchResults = this.seriesResults?.map { it.toSeries() },
        totalPages = this.totalPages,
        totalResults = this.totalResults
    )
}

fun SeriesResult.toSeries(): Series {
    return Series(
        id = this.id ?: 0,
        title = this.title ?: "",
        imageUrl = "https://image.tmdb.org/t/p/original${this.posterPath}",
        rate = this.popularity ?: 0.0,
        yearOfRelease = this.releaseDate ?: "",
        description = this.overview ?: "",
        genre = listOf(),
    )
}
// endregion

//region Details
fun SeriesDetailsResponse.toSeries(): Series {
    return Series(
        id = this.id ?: 0,
        title = this.name ?: "",
        imageUrl = "https://image.tmdb.org/t/p/original${this.posterPath}",
        rate = this.voteAverage ?: 0.0,
        yearOfRelease = this.firstAirDate ?: "",
        seasons = this.seasons?.map { it.toSeason() } ?: emptyList(),
        description = this.overview ?: "",
        genre = this.genres?.map { it.toMediaGenre().title },
        profilePage = this.homepage ?: ""
    )
}
// endregion

//region Cast
fun SeriesCastNetwork.toCast(): Cast {
    return Cast(
        id = this.id ?: 0,
        name = this.name ?: "",
        imageUrl = "https://image.tmdb.org/t/p/original${this.profilePath}"
    )
}

fun SeriesCreditResponse.toCredits(): Credits {
    return Credits(
        id = this.id ?: 0,
        cast = this.seriesCastNetwork?.map { it.toCast() },
    )
}
// endregion


//region Review
fun SeriesReviewResponse.toReviewResult(): ReviewResult {
    return ReviewResult(
        mediaId = this.id ?: 0,
        page = this.page ?: 0,
        results = this.results?.map { it.toReview() } ?: emptyList(),
        totalPages = this.totalPages ?: 0,
        totalResults = this.totalResults ?: 0
    )
}

fun SeriesReviewResult.toReview(): Review {
    return Review(
        userId = this.id ?: "",
        rate = this.authorDetails?.rating ?: 0.0,
        dateOfRelease = this.createdAt ?: "",
        comment = this.content ?: "",
    )
}
// endregion


//region Similar
fun SimilarSeriesResponse.toSimilarSeries(): SimilarMedia {
    return SimilarMedia(
        page = this.page,
        results = this.results?.map { it.toSimilarSeries() },
        totalPages = this.totalPages,
        totalResults = this.totalResults,
    )
}

fun SimilarSeriesNetwork.toSimilarSeries(): SimilarSeries {
    return SimilarSeries(
        id = this.id ?: 0,
        title = this.name ?: "",
        imageUrl = "https://image.tmdb.org/t/p/original${this.posterPath}",
        rate = this.voteAverage ?: 0.0
    )
}
// endregion


// region series Season by series id

fun SeasonsNetwork.toSeason(): Season {
    return Season(
        id = this.id ?: 0,
        seasonNumber = this.seasonNumber ?: 0,
        imageUrl = "https://image.tmdb.org/t/p/original${this.posterPath}",
        rate = this.voteAverage ?: 0.0,
        yearOfRelease = this.airDate ?: "",
        description = this.overview ?: "",
    )

}

fun SeriesGenres.toMediaGenre(): MediaGenre {
    return MediaGenre(
        id = this.id ?: 0,
        title = this.name ?: ""
    )
}
// endregion

//region episodes by series id and season number

fun SeasonEpisodesResponse.toSeasonEpisodes(): SeasonEpisodes {
    return SeasonEpisodes(
        episodes = this.episodeNetworks?.map { it.toEpisode() } ?: emptyList(),
        seasonNumber = this.seasonNumber ?: 0,
        seriesId = this.id ?: 0,
    )
}

fun EpisodeNetwork.toEpisode(): Episode {
    return Episode(
        id = this.id ?: 0,
        title = this.name ?: "",
        rate = this.voteAverage ?: 0.0,
        episodeNumber = this.episodeNumber ?: 0,
        imageUrl = "https://image.tmdb.org/t/p/original${this.stillPath}",
        duration = this.runtime?.toString() ?: "",
    )
}
// endregion


