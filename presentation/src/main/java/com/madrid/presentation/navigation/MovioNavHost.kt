package com.madrid.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.madrid.presentation.screens.searchScreen.SeeAllForYou.SeeAllForYouScreen
import com.madrid.presentation.screens.detailsScreen.detailsMovieScreen.MovieDetailsScreen
import com.madrid.presentation.screens.detailsScreen.seriesDetails.EpisodesScreen
import com.madrid.presentation.screens.detailsScreen.seriesDetails.SeasonsScreen
import com.madrid.presentation.screens.loginScreen.AuthenticationScreen
import com.madrid.presentation.screens.loginScreen.component.ForgotPassword
import com.madrid.presentation.screens.loginScreen.component.WebViewScreen
import com.madrid.presentation.screens.searchScreen.SearchScreen

@Composable
fun MovioNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Destinations.AuthenticationScreen,
        enterTransition = {
            fadeIn(tween(0))
        },
        exitTransition = {
            fadeOut(tween(0))
        }
    ) {
        composable<Destinations.SeeAllForYouScreen> {
            SeeAllForYouScreen()
        }
        composable<Destinations.SplashScreen> {
            //call SplashScreen()
        }
        composable<Destinations.OnBoarding> {
            //call OnBoarding()
        }

        composable<Destinations.SearchScreen> {
            SearchScreen()
        }
        composable<Destinations.HomeScreen> {
            FakeHomeScreen(

            )
        }
        composable<Destinations.EpisodesScreen> {
            EpisodesScreen()
        }

        composable<Destinations.MovieDetailsScreen> {
            MovieDetailsScreen()
        }
        composable<Destinations.SeriesDetailsScreen> {
            //call SeriesDetailsScreen()
        }
        composable<Destinations.TopCastScreen> {
            //call TopCastScreen()
        }
        composable<Destinations.ReviewsScreen> {
            //call ReviewsScreen()
        }
        composable<Destinations.SeasonsScreen> {
            SeasonsScreen()
        }
        composable<Destinations.LibraryScreen> {
            FakeLibraryScreen()
        }
        composable<Destinations.MoreScreen> {
            FakeMoreScreen()
        }
        composable<Destinations.AuthenticationScreen> {
            AuthenticationScreen()
        }
        composable<Destinations.ForgotPassword> {
            val url = it.toRoute<Destinations.ForgotPassword>().url
            ForgotPassword(url = url)
        }

        composable<Destinations.WebViewScreen> {
            val url = it.toRoute<Destinations.WebViewScreen>().url
            WebViewScreen(url = url)
        }

    }
}