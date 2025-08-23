package com.madrid.data.dataSource.remote.dto.keywordSuggestion


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class KeyWordsSuggestionsResponse(
    @SerializedName("page")
    val page: Int? = 0,
    @SerializedName("results")
    val results: List<Result?>? = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int? = 0,
    @SerializedName("total_results")
    val totalResults: Int? = 0
)