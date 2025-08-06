package com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy

interface ViewAllStrategy {
    fun getTitle(): String
    suspend fun getAllItems(page: Int): List<Any>
}