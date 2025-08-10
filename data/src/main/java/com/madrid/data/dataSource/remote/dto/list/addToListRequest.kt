package com.madrid.data.dataSource.remote.dto.list

data class AddToListRequest(
    val media_id: Int,
    val media_type: String = "movie"
)

data class MovieListBody(
    val name: String,
    val description: String,
    val language: String
)


data class CreateListResponse(
    val success: Boolean,
    val status_code: Int,
    val status_message: String,
    val list_id: Int
)