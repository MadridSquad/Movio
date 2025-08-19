package com.madrid.domain.entity

data class Review(
    val reviewId: String,
    val reviewerName: String,
    val reviewerPhotoUrl: String?,
    val rate: Double,
    val date: String,
    val comment: String,
)