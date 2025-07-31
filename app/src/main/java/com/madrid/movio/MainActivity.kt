package com.madrid.movio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.navigation.MovioNavGraph
import com.madrid.presentation.viewModel.authentication.MainViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainActivity : ComponentActivity(), KoinComponent {

    val mainViewModel: MainViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        actionBar?.hide()
        installSplashScreen().setKeepOnScreenCondition {
            mainViewModel.isLoading.value
        }
        setContent {
            MovioTheme {
                MainScreen(mainViewModel)
                // AuthenticationScreen()
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()

    CompositionLocalProvider(LocalNavController provides navController) {
        MovioNavGraph(navController = navController, isLoggedIn = isLoggedIn)
    }
}

