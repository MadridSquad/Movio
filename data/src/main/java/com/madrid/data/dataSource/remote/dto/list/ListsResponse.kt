package com.madrid.data.dataSource.remote.dto.list

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ListsResponse(
    @SerializedName("results")
    val results: List<ListDto>,
)

@Serializable
data class ListDto(
    val description: String? = null,
    @SerializedName("favorite_count")
    val favoriteCount: Int = 0,
    val id: Int,
    @SerializedName("item_count")
    val itemCount: Int = 0,
    @SerializedName("iso_639_1")
    val language: String? = null,
    @SerializedName("list_type")
    val listType: String? = null,
    val name: String? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
)