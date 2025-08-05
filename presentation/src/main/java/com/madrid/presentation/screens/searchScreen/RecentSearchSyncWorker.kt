package com.madrid.presentation.screens.searchScreen

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.madrid.domain.usecase.search.ClearAllRecentSearchesUseCase
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RecentSearchSyncWorker @Inject constructor(
     appContext: Context,
    params: WorkerParameters,
    private val clearAllRecentSearchesUseCase: ClearAllRecentSearchesUseCase,
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        clearAllRecentSearchesUseCase()
        return Result.success()
    }

    companion object {
        private const val WORK_NAME = "recent_search_clear_worker"

        fun enqueue(context: Context) {
            val workRequest = PeriodicWorkRequestBuilder<RecentSearchSyncWorker>(1, TimeUnit.HOURS)
                .build()
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.UPDATE,
                workRequest
            )
        }
    }
} 