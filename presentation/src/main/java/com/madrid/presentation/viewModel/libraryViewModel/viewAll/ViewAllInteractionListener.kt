
package com.madrid.presentation.viewModel.libraryViewModel.viewAll

import com.madrid.presentation.viewModel.shared.MediaType

interface ViewAllInteractionListener {
    fun onBackClicked()
    fun onItemClicked(mediaId: String, mediaType: MediaType)
    fun onItemDeleted(mediaId: String, mediaType: MediaType)
    fun onRetryClicked()
    fun onDismissSnackBar()
    fun onUndoDeleteClicked()
    fun onTryAgainButtonClicked()
}