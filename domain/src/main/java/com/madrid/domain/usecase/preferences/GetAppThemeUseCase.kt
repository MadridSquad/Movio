package com.madrid.domain.usecase.preferences

import com.madrid.domain.repository.PreferencesRepository
import javax.inject.Inject

class GetAppThemeUseCase@Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    operator fun invoke() = preferencesRepository.getAppDarkModeOn()
}