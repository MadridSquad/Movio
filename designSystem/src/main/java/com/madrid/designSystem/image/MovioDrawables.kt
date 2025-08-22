package com.madrid.designSystem.image

import androidx.compose.runtime.staticCompositionLocalOf
import com.madrid.designSystem.color.lightThemeColors

data class MovioDrawables(
    val imagePlaceHolderId: Int,
    val profilePlaceHolderId: Int,
    val emptyLayoutId: Int,
    val noInternetId: Int,
    val notFoundLayoutId: Int,
    val saveLayoutId: Int
)

internal val LocalMovioDrawable = staticCompositionLocalOf { lightThemeDrawables }
