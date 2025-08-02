package com.madrid.presentation.screens.moreScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.viewModel.moreViewModel.MoreInteractionListener
import com.madrid.presentation.viewModel.moreViewModel.MoreUiState
import com.madrid.presentation.viewModel.moreViewModel.MoreViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MoreScreen(
    viewModel: MoreViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    MoreScreenContent(
        state = state,
        interactionListener = viewModel as MoreInteractionListener
    )
}

@Composable
private fun MoreScreenContent(
    state: MoreUiState,
    interactionListener: MoreInteractionListener
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Theme.color.surfaces.surface),
        contentAlignment = Alignment.Center

    ) {
        MovioText(
            text = stringResource(R.string.more_screen),
            textStyle = Theme.textStyle.title.largeBold14,
            color = Theme.color.brand.primary,
        )
    }
}