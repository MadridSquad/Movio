package com.madrid.data.dataSource.remote.dto.list

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoveMovieDto(
    @SerialName("media_id")
    val mediaId: Int,
)