package com.madrid.presentation.workManager

import android.util.Log
import com.madrid.domain.usecase.movie.ClearHomeMoviesCacheUseCase
import com.madrid.presentation.viewModel.base.BaseViewModel

class WorkManagerViewModel(val clearHomeMoviesCacheUseCase: ClearHomeMoviesCacheUseCase) :
    BaseViewModel<Unit, Unit>(Unit) {

    fun clearHomeMoviesCacheData() {
        tryToExecute(
            function = {
                clearHomeMoviesCacheUseCase()
            },
            onSuccess = { Log.d("clearHomeMoviesCacheData", "success")},
            onError = { Log.d("clearHomeMoviesCacheData", "error: ${it.message}") }
        )
    }
}