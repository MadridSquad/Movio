package com.madrid.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.madrid.presentation.screens.detailsScreen.castDetails.ActorDetails
import com.madrid.presentation.screens.detailsScreen.castDetails.TopCastDetailsScreen
import com.madrid.presentation.screens.detailsScreen.detailsMovieScreen.MovieDetailsScreen
import com.madrid.presentation.screens.detailsScreen.reviewsScreen.ReviewsScreen
import com.madrid.presentation.screens.detailsScreen.seriesDetails.EpisodesScreen
import com.madrid.presentation.screens.detailsScreen.seriesDetails.SeasonsScreen
import com.madrid.presentation.screens.detailsScreen.seriesDetails.SeriesDetailsScreen
import com.madrid.presentation.screens.detailsScreen.similarMedia.SeeAllSimilarMediaScreen
import com.madrid.presentation.screens.homeScreen.HomeScreen
import com.madrid.presentation.screens.homeScreen.SeeAllTVShowsScreen
import com.madrid.presentation.screens.homeScreen.component.FakeHomeScreen
import com.madrid.presentation.screens.libraryScreen.LibraryScreen
import com.madrid.presentation.screens.loginScreen.AuthenticationScreen
import com.madrid.presentation.screens.loginScreen.component.ForgotPassword
import com.madrid.presentation.screens.loginScreen.component.WebViewScreen
import com.madrid.presentation.screens.moreScreen.MoreScreen
import com.madrid.presentation.screens.searchScreen.SearchScreen
import com.madrid.presentation.screens.searchScreen.SeeAllForYou.SeeAllForYouScreen

@Composable
fun MovioNavHost(
    navController: NavHostController,
    isLoggedIn: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn.not()) Destinations.AuthenticationScreen else Destinations.HomeScreen,
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
            HomeScreen()
        }
        composable<Destinations.EpisodesScreen> {
            EpisodesScreen()
        }

        composable<Destinations.MovieDetailsScreen> {
            MovieDetailsScreen()
        }
        composable<Destinations.SeriesDetailsScreen> {
            SeriesDetailsScreen()
        }
        composable<Destinations.ReviewsScreen> {
            ReviewsScreen()
        }
        composable<Destinations.SeasonsScreen> {
            SeasonsScreen()
        }
        composable<Destinations.LibraryScreen> {
            LibraryScreen()
        }
        composable<Destinations.MoreScreen> {
            MoreScreen()
        }
        composable<Destinations.ActorDetails> {
            ActorDetails()
        }
        composable<Destinations.TopCast> {
            TopCastDetailsScreen()
        }
        composable<Destinations.SimilarMediaScreen> {
            SeeAllSimilarMediaScreen()
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
            composable<Destinations.SeeAllTvShowsScreen> { backStackEntry ->
                val destination = backStackEntry.toRoute<Destinations.SeeAllTvShowsScreen>()
                SeeAllTVShowsScreen(type = destination.type)
            }

        }
    }
}