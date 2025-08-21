package com.madrid.presentation.viewModel.shared

import com.madrid.domain.entity.Genre


data class CategoryUiState(
    val id: Int = -1,
    val name: String = "All",
)

fun Genre.toCategoryUiState() = CategoryUiState(
    id = this.id,
    name = this.name
)

