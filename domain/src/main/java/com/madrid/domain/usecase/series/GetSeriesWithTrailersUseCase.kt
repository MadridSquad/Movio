package com.madrid.domain.usecase.series

import com.madrid.domain.entity.Series
import com.madrid.domain.entity.Trailer
import javax.inject.Inject

class GetSeriesWithTrailersUseCase @Inject constructor(
    private val getSeriesTrailersUseCase: GetSeriesTrailersUseCase
) {
    suspend operator fun invoke(series: List<Series>): List<Series> {
        return series.map {
            if (getSeriesTrailersUseCase(it.id).isNotEmpty()) {
                val trailer = getSeriesTrailersUseCase(it.id).first()
                it.copy(
                    trailer = Trailer(
                        id = trailer.id,
                        key = trailer.key
                    )
                )
            } else {
                it
            }
        }
    }
}