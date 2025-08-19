package com.madrid.data.dataSource.remote.dto.list

data class AddToListRequest(
    val mediaId: Int,
    val mediaType: String = "movie"
)

data class MovieListBody(
    val name: String,
    val description: String,
    val language: String
)


data class CreateListResponse(
    val success: Boolean,
    val statusCode: Int,
    val statusMessage: String,
    val listId: Int
)