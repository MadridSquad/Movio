package com.madrid.presentation.screens.exceptionScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.designSystem.component.MovioIcon
import com.madrid.presentation.screens.exceptionScreen.EmptyStateCard

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
fun lkdg(){
    EmptySearchScreen()
}