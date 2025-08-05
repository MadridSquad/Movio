package com.madrid.data.repositories.mapper

import com.madrid.domain.utils.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun AppTheme.toBoolean(): Boolean = when(this) {
    AppTheme.DARK -> true
    AppTheme.LIGHT -> false
}

fun Boolean.toAppTheme(): AppTheme = if (this) AppTheme.DARK else AppTheme.LIGHT

fun Flow<Boolean>.toTheme(): Flow<AppTheme> = this.map { it.toAppTheme() }