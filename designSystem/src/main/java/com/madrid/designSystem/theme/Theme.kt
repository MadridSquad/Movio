package com.madrid.designSystem.theme

import com.madrid.designSystem.text_style.MovioTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.madrid.designSystem.color.LocalMovioColor
import com.madrid.designSystem.color.MovioColors
import com.madrid.designSystem.image.LocalMovioDrawable
import com.madrid.designSystem.image.MovioDrawables
import com.madrid.designSystem.text_style.LocalMovioTextStyle

object Theme {
    val color: MovioColors
        @Composable @ReadOnlyComposable get() = LocalMovioColor.current

    val textStyle: MovioTextStyle
        @Composable @ReadOnlyComposable get() = LocalMovioTextStyle.current

    val drawables: MovioDrawables
        @Composable @ReadOnlyComposable get() = LocalMovioDrawable.current
}