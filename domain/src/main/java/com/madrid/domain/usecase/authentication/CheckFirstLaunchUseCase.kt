package com.madrid.domain.usecase.authentication

import com.madrid.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckFirstLaunchUseCase @Inject constructor(private val authenticationRepository: AuthenticationRepository) {
    fun isFirstLaunch(): Flow<Boolean> {
        return authenticationRepository.isFirstLaunch()
    }

    suspend fun setOnBoardingDone(isCompleted: Boolean) {
        authenticationRepository.setOnboardingCompleted(isCompleted = isCompleted)
    }
}

