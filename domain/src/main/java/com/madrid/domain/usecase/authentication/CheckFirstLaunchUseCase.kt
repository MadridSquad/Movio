package com.madrid.domain.usecase.authentication

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CheckFirstLaunchUseCase() {
    operator fun invoke() : Flow<Boolean>{
        return flowOf(true)
    }
}

