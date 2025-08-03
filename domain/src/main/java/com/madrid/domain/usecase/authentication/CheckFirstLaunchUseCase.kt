package com.madrid.domain.usecase.authentication

import com.madrid.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CheckFirstLaunchUseCase(private val userRepository: UserRepository) {
    fun isFirstLaunch() : Flow<Boolean>{
        return userRepository.isFirstLaunch()
    }

    suspend fun setOnBoardingDone(isCompleted : Boolean){
        userRepository.setOnboardingCompleted(isCompleted = isCompleted)
    }
}

