package com.madrid.presentation.screens.exceptionScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.madrid.presentation.screens.exceptionScreen.EmptyStateCard
import com.madrid.designSystem.R

@Composable
fun NoHistoryScreen() {
    EmptyStateCard(
        icon = painterResource(id = R.drawable.not_found),
        title = stringResource(id = R.string.empty_no_history_title),
        description = stringResource(id = R.string.empty_no_history_description),
        modifier = Modifier.fillMaxSize(),
        iconContentDescription = "No history illustration"
    )
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_5")
@Composable
fun lkdfg(){
    NoHistoryScreen()
}