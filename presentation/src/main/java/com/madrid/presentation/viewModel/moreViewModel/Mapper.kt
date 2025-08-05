package com.madrid.presentation.viewModel.moreViewModel

import com.madrid.domain.utils.AppTheme

fun ThemeType.toAppTheme() = when (this) {
    ThemeType.LIGHT -> AppTheme.LIGHT
    ThemeType.DARK -> AppTheme.DARK
}

fun AppTheme.toThemeType() = when (this) {
    AppTheme.LIGHT -> ThemeType.LIGHT
    AppTheme.DARK -> ThemeType.DARK
}
