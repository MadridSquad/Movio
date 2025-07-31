package com.madrid.presentation.viewModel.seeAll.tvShows

import com.madrid.domain.entity.Series

interface SeeAllTVShowsStrategy {
    fun getTitle(): String
    suspend fun getAllTvShows(page: Int): List<Series>
    suspend fun getTvShowsBasedOnCategory(categoryId: Int): List<Series>
    fun showTvShowsCategory(): Boolean
}