package com.madrid.domain.usecase.series

import jakarta.inject.Inject

class IsFavoriteSeriesUseCase @Inject constructor(
    private val getFavoriteSeriesUseCase: GetFavoriteSeriesUseCase
) {
    suspend operator fun invoke(
        seriesId: Int
    ): Boolean {
        val favoriteSeries = getFavoriteSeriesUseCase()
        return favoriteSeries.any { it.id == seriesId }
    }
}