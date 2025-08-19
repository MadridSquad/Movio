package com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy

import com.madrid.presentation.viewModel.shared.MediaType
import com.madrid.presentation.viewModel.shared.MediaUiState

interface ViewAllStrategy {
    fun getTitle(): Int
    fun getEmptyListMessage(): Int
    suspend fun getAllItems(): List<MediaUiState>
    suspend fun deleteItem(mediaId: String, mediaType: MediaType)
    suspend fun onUndoDelete(mediaId: Int, mediaType: MediaType)
}