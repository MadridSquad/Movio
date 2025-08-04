package com.madrid.data.repositories

import com.madrid.domain.entity.SortType

fun getSortType(sortType: SortType): String =
    when (sortType) {
        SortType.ALL -> "vote_count.desc"
        SortType.POPULARITY -> "popularity.desc"
        SortType.LATEST -> "primary_release_date.desc"
    }