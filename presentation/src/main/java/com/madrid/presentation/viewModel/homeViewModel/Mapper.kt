package com.madrid.presentation.viewModel.homeViewModel

import com.madrid.domain.entity.Genre
import com.madrid.domain.entity.SortType

fun SortingType.toSortType(): SortType {
    return when (this) {
        SortingType.ALL -> SortType.ALL
        SortingType.POPULARITY -> SortType.POPULARITY
        SortingType.LATEST -> SortType.LATEST
    }
}