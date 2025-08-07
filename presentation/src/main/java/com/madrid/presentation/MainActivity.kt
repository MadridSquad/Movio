package com.madrid.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.navigation.MovioNavGraph
import com.madrid.presentation.utils.Language
import com.madrid.presentation.viewModel.authentication.MainViewModel
import com.madrid.presentation.workManager.MovieCacheCleanupWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU)
            Language.configureLocaleOnStartForDevicesLowerThanTiramisu(this)
        enableEdgeToEdge()
        actionBar?.hide()
        installSplashScreen().setKeepOnScreenCondition {
            mainViewModel.isLoading.value
        }
        setContent {
            val isDarkTheme by mainViewModel.isDarkTheme.collectAsStateWithLifecycle()

            MovioTheme(isDarkTheme) {
                MainScreen(mainViewModel)
            }
        }

        val uploadWorkRequest =
            PeriodicWorkRequestBuilder<MovieCacheCleanupWorker>(24, TimeUnit.HOURS).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "clearHome",
            ExistingPeriodicWorkPolicy.KEEP,
            uploadWorkRequest,
        )
    }

}

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val isLoggedIn by mainViewModel.isLoggedIn.collectAsStateWithLifecycle()
    val isFirstLaunch by mainViewModel.isFirstLaunch.collectAsStateWithLifecycle()

    CompositionLocalProvider(LocalNavController provides navController) {
        MovioNavGraph(
            navController = navController,
            isLoggedIn = isLoggedIn,
            isFirstLaunch = isFirstLaunch,
            setOnBoardingComplete = mainViewModel::setOnBoardingCompleted
        )
    }
}