package com.madrid.presentation.screens.exceptionScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EmptySearchScreen() {
    EmptyStateCard(
        icon = painterResource(id = com.madrid.designSystem.R.drawable.not_found),
        title = stringResource(id = com.madrid.designSystem.R.string.empty_search_title),
        description = stringResource(id = com.madrid.designSystem.R.string.empty_search_description),
        modifier = Modifier
            .fillMaxSize(),
        iconContentDescription = "Empty search illustration"
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EmptySearchScreenPreview() {
    EmptySearchScreen()
}