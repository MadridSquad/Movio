package com.madrid.data.dataSource.remote.dto.authentication

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CreateSessionBody(
    val username: String,
    val password: String,
    @SerializedName("request_token")
    val requestToken: String
)

@Serializable
data class CreateSessionRawBody(
    @SerializedName("request_token")
    val requestToken: String
)
