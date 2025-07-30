package com.madrid.domain.usecase.series

import com.madrid.domain.entity.Series

class FilterSeriesByCategoryUseCase {
    operator fun invoke(series: List<Series>, category: String): List<Series> {
        return series.filter { series ->
            series.genre.any { it.equals(category, ignoreCase = true) }
        }
    }
}