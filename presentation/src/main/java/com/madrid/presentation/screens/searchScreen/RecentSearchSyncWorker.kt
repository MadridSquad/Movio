package com.madrid.presentation.screens.searchScreen

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.madrid.domain.usecase.search.ClearAllRecentSearchesUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.TimeUnit

class RecentSearchSyncWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params), KoinComponent {
    private val clearAllRecentSearchesUseCase: ClearAllRecentSearchesUseCase by inject()
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