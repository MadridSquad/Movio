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
fun NoInternetScreen(onTryAgain: () -> Unit = {}) {
    EmptyStateCard(
        icon = painterResource(id = R.drawable.no_internet),
        title = stringResource(id = R.string.empty_no_internet_title),
        description = stringResource(id = R.string.empty_no_internet_description),
        modifier = Modifier.fillMaxSize(),
        iconContentDescription = "No internet illustration"
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun lfg(){
    NoInternetScreen()
}