package com.madrid.domain.usecase.preferences

import com.madrid.domain.repository.PreferencesRepository
import com.madrid.domain.utils.AppLanguage
import javax.inject.Inject

class SetLanguageUseCase@Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(language: AppLanguage) {
        preferencesRepository.setAppLanguage(language)
    }
}