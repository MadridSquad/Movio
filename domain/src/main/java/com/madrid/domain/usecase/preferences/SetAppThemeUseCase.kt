package com.madrid.domain.usecase.preferences

import com.madrid.domain.repository.PreferencesRepository
import com.madrid.domain.utils.AppTheme
import javax.inject.Inject

class SetAppThemeUseCase@Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(appTheme: AppTheme) {
        preferencesRepository.setAppDarkModeOn(appTheme)
    }
}