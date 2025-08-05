package com.madrid.data.repositories.mapper

import com.madrid.domain.utils.AppLanguage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun AppLanguage.toStringLanguage(): String = when(this) {
    AppLanguage.ARABIC -> "ar"
    AppLanguage.ENGLISH -> "en"
}

fun String.toAppLanguage(): AppLanguage = when(this) {
    "ar" -> AppLanguage.ARABIC
    "en" -> AppLanguage.ENGLISH
    else -> AppLanguage.ENGLISH
}

fun Flow<String>.toLanguage(): Flow<AppLanguage> = this.map { it.toAppLanguage() }