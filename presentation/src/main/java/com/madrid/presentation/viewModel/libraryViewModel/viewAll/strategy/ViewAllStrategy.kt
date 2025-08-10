package com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy

import com.madrid.domain.entity.Movie

interface ViewAllStrategy {
    fun getTitle(): String
    suspend fun getAllItems(page: Int): List<Movie>
}