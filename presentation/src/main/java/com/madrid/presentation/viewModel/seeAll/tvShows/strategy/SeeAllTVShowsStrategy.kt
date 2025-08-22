package com.madrid.presentation.viewModel.seeAll.tvShows.strategy

import com.madrid.domain.entity.Series

interface SeeAllTVShowsStrategy {
    fun getTitle(): Int
    suspend fun getAllTvShows(page: Int): List<Series>
    suspend fun getTvShowsBasedOnCategory(page: Int,categoryId: Int): List<Series>
    fun showTvShowsCategory(): Boolean
}