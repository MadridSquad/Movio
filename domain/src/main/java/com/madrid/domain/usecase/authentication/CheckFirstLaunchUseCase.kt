package com.madrid.domain.usecase.authentication

import com.madrid.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckFirstLaunchUseCase @Inject constructor(private val userRepository: UserRepository) {
    fun isFirstLaunch() : Flow<Boolean>{
        return userRepository.isFirstLaunch()
    }

    suspend fun setOnBoardingDone(isCompleted : Boolean){
        userRepository.setOnboardingCompleted(isCompleted = isCompleted)
    }
}

