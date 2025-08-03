package com.madrid.domain.usecase.series

import com.madrid.domain.entity.Series
import javax.inject.Inject

class FilterSeriesByCategoryUseCase @Inject constructor() {
    operator fun invoke(series: List<Series>, category: Int): List<Series> {
        return series.filter { series ->
            series.genre.any { it.id == category }
        }
    }
}