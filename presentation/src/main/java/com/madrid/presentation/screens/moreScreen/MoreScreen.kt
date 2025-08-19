package com.madrid.presentation.screens.moreScreen

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.madrid.designSystem.R
import com.madrid.designSystem.component.DialogWithButtonLayout
import com.madrid.designSystem.component.SettingsItem
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.screens.logOut.LogoutConfirmationBottomSheet
import com.madrid.presentation.screens.moreScreen.component.OptionUiState
import com.madrid.presentation.screens.moreScreen.component.ProfileSection
import com.madrid.presentation.screens.moreScreen.component.SelectionBottomSheet
import com.madrid.presentation.utils.Language
import com.madrid.presentation.viewModel.moreViewModel.MoreEffect
import com.madrid.presentation.viewModel.moreViewModel.MoreInteractionListener
import com.madrid.presentation.viewModel.moreViewModel.MoreUiState
import com.madrid.presentation.viewModel.moreViewModel.MoreViewModel
import com.madrid.presentation.viewModel.moreViewModel.ThemeType
import com.madrid.presentation.R as presentationR

@Composable
fun MoreScreen(
    viewModel: MoreViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val navController = LocalNavController.current

    val context = LocalContext.current
    val deviceLocale by remember { mutableStateOf(context.resources.configuration.locales.get(0)) }

    val currentLocale = remember { mutableStateOf(Language.getCurrentLanguage(context)) }

    val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags("en")
    AppCompatDelegate.setApplicationLocales(appLocale)

    val packageManager = context.packageManager
    val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
    val appVersion by remember { mutableStateOf(packageInfo.versionName) }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MoreEffect.navigateToLogin -> {
                    navController.navigate(Destinations.AuthenticationScreen)
                }

                is MoreEffect.navigateToMyRatings -> {
                    navController.navigate(Destinations.MyRatingScreen)
                }
            }
        }
    }

    MoreScreenContent(
        state = state,
        interactionListener = viewModel as MoreInteractionListener,
        currentLocale = currentLocale,
        currentLanguage = deviceLocale.language,
        appVersion = "v$appVersion"
    )

    LogoutConfirmationBottomSheet(
        isVisible = state.isLogoutSheetVisible,
        onDismiss = { viewModel.dismissLogoutSheet() },
        onNavigateToAuth = {
            navController.navigate(Destinations.AuthenticationScreen) {
                popUpTo(0) { inclusive = true }
            }
        },
    )
}

@Composable
private fun MoreScreenContent(
    state: MoreUiState,
    interactionListener: MoreInteractionListener,
    currentLocale: MutableState<Language>,
    currentLanguage: String,
    appVersion: String,
) {
    var selectedLanguage by remember { mutableStateOf(currentLanguage) }
    val context = LocalContext.current
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
                    text = if (state.currentTheme == ThemeType.DARK) stringResource(presentationR.string.dark)
                    else stringResource(
                        presentationR.string.light
                    ),
                    clickable = true,
                    onClick = { interactionListener.onClickTheme() }
                )
                SettingsItem(
                    icon = R.drawable.outline_earth,
                    title = stringResource(presentationR.string.language),
                    text = if (currentLanguage == Language.Arabic.code)
                        stringResource(presentationR.string.arabic)
                    else stringResource(presentationR.string.english),
                    clickable = true,
                    onClick = { interactionListener.onClickLanguage() }
                )
                SettingsItem(
                    icon = R.drawable.outline_smartphone,
                    title = stringResource(presentationR.string.app_version),
                    text = appVersion,
                    clickable = false,
                )
                SettingsItem(
                    icon = R.drawable.outline_arrows_logout,
                    title = stringResource(presentationR.string.logout),
                    clickable = true,
                    onClick = { interactionListener.onLogoutBtnClick() }
                )
            }
            AnimatedVisibility(state.isThemeSheetVisible) {
                SelectionBottomSheet(
                    title = stringResource(R.string.choose_theme),
                    isShown = state.isThemeSheetVisible,
                    onDismiss = interactionListener::onDismissBottomSheet,
                    selectedOption = state.selectedTheme.value,
                    options = listOf(
                        OptionUiState(
                            title = stringResource(R.string.light_mode),
                            leadingIcon = painterResource(R.drawable.outline_sun),
                            trailingIcon = painterResource(R.drawable.bold_check_circle),
                            id = ThemeType.LIGHT.value
                        ), OptionUiState(
                            title = stringResource(R.string.dark_mode),
                            leadingIcon = painterResource(R.drawable.outline_moon_stars),
                            trailingIcon = painterResource(R.drawable.bold_check_circle),
                            id = ThemeType.DARK.value
                        )
                    ),
                    onOptionSelected = { interactionListener.onSelectTheme(ThemeType.fromValue(it.id)) },
                    onConfirmButtonClick = interactionListener::onConfirmTheme
                )
            }

            AnimatedVisibility(state.isLanguageSheetVisible) {
                SelectionBottomSheet(
                    title = stringResource(R.string.choose_language),
                    onConfirmButtonClick = {
                        currentLocale.value =
                            if (selectedLanguage == Language.Arabic.code) Language.Arabic else Language.English
                        Language.setLocale(context = context, localeCode = currentLocale.value.code)
                        interactionListener.onConfirmLanguage()
                        interactionListener.onDismissBottomSheet()
                    },
                    isShown = state.isLanguageSheetVisible,
                    onDismiss = interactionListener::onDismissBottomSheet,
                    selectedOption = selectedLanguage,
                    options = listOf(
                        OptionUiState(
                            title = stringResource(Language.English.titleRes),
                            trailingIcon = painterResource(R.drawable.bold_check_circle),
                            id = Language.English.code,
                        ),
                        OptionUiState(
                            title = stringResource(Language.Arabic.titleRes),
                            trailingIcon = painterResource(R.drawable.bold_check_circle),
                            id = Language.Arabic.code,
                        ),
                    ),
                    onOptionSelected = { selectedLanguage = it.id },
                )
            }
        }
    }
}