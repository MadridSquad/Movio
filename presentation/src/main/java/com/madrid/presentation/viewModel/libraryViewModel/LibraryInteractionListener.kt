package com.madrid.presentation.viewModel.libraryViewModel

interface LibraryInteractionListener {
    fun onItemClick(itemId: Int)
    fun onViewAllClick(type: String)
}