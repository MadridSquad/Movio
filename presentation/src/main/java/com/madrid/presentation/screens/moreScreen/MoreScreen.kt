package com.madrid.presentation.screens.moreScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.madrid.designSystem.R
import com.madrid.designSystem.component.DialogWithButtonLayout
import com.madrid.designSystem.component.SettingsItem
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.screens.moreScreen.component.ProfileSection
import com.madrid.presentation.viewModel.moreViewModel.MoreEffect
import com.madrid.presentation.viewModel.moreViewModel.MoreInteractionListener
import com.madrid.presentation.viewModel.moreViewModel.MoreUiState
import com.madrid.presentation.viewModel.moreViewModel.MoreViewModel
import com.madrid.presentation.R as presentationR

@Composable
fun MoreScreen(
    viewModel: MoreViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsStateWithLifecycle().value
    val navController = LocalNavController.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MoreEffect.navigateToLogin -> {
                    navController.navigate(Destinations.AuthenticationScreen)
                }

                is MoreEffect.navigateToMyRatings -> TODO()
            }
        }
    }
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

    if (state.isGuest) {
        DialogWithButtonLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp),
            title = stringResource(presentationR.string.unlock_your_personal_library),
            description = stringResource(presentationR.string.access_your_watch_history_favorites_and_watchlist_all_in_one_place),
            image = R.drawable.library_main_icon,
            buttonText = stringResource(presentationR.string.login),
            onClick = { interactionListener.onLoginBtnClick() },
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Log.e("MY_TAG" ," More screen ${ state.profilePictureUrl.toString() }")
            ProfileSection(
                username = state.username,
                profilePicture = state.profilePictureUrl,
                onProfileClick = { }
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                SettingsItem(
                    icon = R.drawable.outline_star,
                    title = stringResource(presentationR.string.my_ratings),
                    clickable = true,
                    onClick = { interactionListener.onMyRatingsBtnClick() }
                )
                SettingsItem(
                    icon = R.drawable.outline_pallete2,
                    title = stringResource(presentationR.string.theme),
                    text = if (state.isDarkModeEnabled) stringResource(presentationR.string.dark)
                    else stringResource(
                        presentationR.string.light
                    ),
                    clickable = true,
                    onClick = { interactionListener.onThemeClick() }
                )
                SettingsItem(
                    icon = R.drawable.outline_earth,
                    title = stringResource(presentationR.string.language),
                    text = state.language,
                    clickable = true,
                    onClick = { interactionListener.onLanguageBtnClick() }
                )
                SettingsItem(
                    icon = R.drawable.outline_smartphone,
                    title = stringResource(presentationR.string.app_version),
                    text = state.appVersion,
                    clickable = false,
                )
                SettingsItem(
                    icon = R.drawable.outline_arrows_logout,
                    title = stringResource(presentationR.string.logout),
                    clickable = true,
                    onClick = { interactionListener.onLogoutBtnClick() }
                )
            }
        }
    }
}