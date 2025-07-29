package com.madrid.presentation.viewModel.seeAll

import com.madrid.domain.entity.Series

interface SeeAllTVShowsStrategy {
    fun getTitle(): String
    suspend fun getTVShows(page: Int): List<Series>
    fun showTVShowsCategory(): Boolean
}