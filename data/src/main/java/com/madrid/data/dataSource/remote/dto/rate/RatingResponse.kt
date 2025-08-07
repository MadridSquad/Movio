package com.madrid.data.dataSource.remote.dto.rate

import com.google.gson.annotations.SerializedName
import com.madrid.data.dataSource.remote.dto.movie.MovieResult
import com.madrid.data.dataSource.remote.dto.series.SeriesResult
import kotlinx.serialization.Serializable

@Serializable
data class RatingMovieResponse(
    @SerializedName("results")
    val ratedMovie: List<MovieResult>,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null
)

@Serializable
data class RatingSeriesResponse(
    @SerializedName("results")
    val ratedSeries: List<SeriesResult>,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null
)