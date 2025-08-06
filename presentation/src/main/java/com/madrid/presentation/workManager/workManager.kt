package com.madrid.presentation.workManager

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.madrid.domain.usecase.movie.ClearHomeMoviesCacheUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class MovieCacheCleanupWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val clearHomeMoviesCacheUseCase: ClearHomeMoviesCacheUseCase
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            clearHomeMoviesCacheUseCase()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}