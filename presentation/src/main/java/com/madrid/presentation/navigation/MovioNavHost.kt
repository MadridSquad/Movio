package com.madrid.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.madrid.presentation.screens.detailsScreen.castDetails.ActorDetails
import com.madrid.presentation.screens.detailsScreen.castDetails.TopCastDetailsScreen
import com.madrid.presentation.screens.detailsScreen.movieDetail.MovieDetailsScreen
import com.madrid.presentation.screens.detailsScreen.reviewsScreen.ReviewsScreen
import com.madrid.presentation.screens.detailsScreen.seriesDetails.EpisodesScreen
import com.madrid.presentation.screens.detailsScreen.seriesDetails.SeasonsScreen
import com.madrid.presentation.screens.detailsScreen.seriesDetails.SeriesDetailsScreen
import com.madrid.presentation.screens.detailsScreen.similarMedia.SeeAllSimilarMediaScreen
import com.madrid.presentation.screens.homeScreen.HomeScreen
import com.madrid.presentation.screens.homeScreen.SeeAllMoviesScreen
import com.madrid.presentation.screens.homeScreen.SeeAllTVShowsScreen
import com.madrid.presentation.screens.libraryScreen.LibraryScreen
import com.madrid.presentation.screens.libraryScreen.ViewAllScreen
import com.madrid.presentation.screens.libraryScreen.WatchlistViewAllScreen
import com.madrid.presentation.screens.libraryScreen.watchList.WatchListDetailsScreen
import com.madrid.presentation.screens.loginScreen.LoginScreen
import com.madrid.presentation.screens.loginScreen.component.WebViewScreen
import com.madrid.presentation.screens.moreScreen.MoreScreen
import com.madrid.presentation.screens.moreScreen.MyRatingScreen
import com.madrid.presentation.screens.onboarding.OnBoardingScreen
import com.madrid.presentation.screens.searchScreen.SearchScreen
import com.madrid.presentation.screens.searchScreen.seeAllForYou.SeeAllForYouScreen
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.ViewAllViewModel
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.factory.ViewAllFactory
import com.madrid.presentation.viewModel.seeAll.movies.factory.SeeAllMoviesFactory
import com.madrid.presentation.viewModel.seeAll.movies.SeeAllMoviesViewModel
import com.madrid.presentation.viewModel.seeAll.tvShows.factory.SeeAllTVShowsFactory
import com.madrid.presentation.viewModel.seeAll.tvShows.SeeAllTVShowsViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface StrategyFactoryEntryPoint {
    fun moviesFactory(): SeeAllMoviesFactory
    fun tvShowsFactory(): SeeAllTVShowsFactory
    fun viewAllFactory(): ViewAllFactory
}

@Composable
fun MovioNavHost(
    navController: NavHostController,
    isLoggedIn: Boolean,
    isFirstLaunch: Boolean,
    setOnBoardingComplete: (Boolean) -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination =
            if (isFirstLaunch) {
                setOnBoardingComplete(true)
                Destinations.OnBoarding
            } else if (isLoggedIn.not()) Destinations.LoginScreen
            else Destinations.HomeScreen,
        enterTransition = {
            fadeIn()
        },
        exitTransition = {
            fadeOut()
        }
    ) {
        composable<Destinations.SeeAllForYouScreen> {
            SeeAllForYouScreen()
        }
        composable<Destinations.SplashScreen> {
            //call SplashScreen()
        }
        composable<Destinations.OnBoarding> {
            OnBoardingScreen()
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
        composable<Destinations.WatchListViewAllScreen>{
            WatchlistViewAllScreen()
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
        composable<Destinations.LoginScreen> {
            LoginScreen()
        }
        composable<Destinations.ForgotPassword> {
            val url = it.toRoute<Destinations.ForgotPassword>().url
            WebViewScreen(url = url)
        }
        composable<Destinations.WebViewScreen> {
            val url = it.toRoute<Destinations.WebViewScreen>().url
            WebViewScreen(url = url)
        }
        composable<Destinations.SeeAllMoviesScreen> { backStackEntry ->
            val destination = backStackEntry.toRoute<Destinations.SeeAllMoviesScreen>()
            val context = androidx.compose.ui.platform.LocalContext.current
            val entryPoint =
                EntryPointAccessors.fromApplication(context, StrategyFactoryEntryPoint::class.java)
            val strategy = entryPoint.moviesFactory().create(destination.type)
            SeeAllMoviesScreen(
                viewModel = hiltViewModel<SeeAllMoviesViewModel, SeeAllMoviesViewModel.Factory>(
                    key = destination.type.toString()
                ) { factory -> factory.create(strategy) }
            )
        }
        composable<Destinations.SeeAllTvShowsScreen> { backStackEntry ->
            val destination = backStackEntry.toRoute<Destinations.SeeAllTvShowsScreen>()
            val context = androidx.compose.ui.platform.LocalContext.current
            val entryPoint =
                EntryPointAccessors.fromApplication(context, StrategyFactoryEntryPoint::class.java)
            val strategy = entryPoint.tvShowsFactory().create(destination.type)
            SeeAllTVShowsScreen(
                viewModel = hiltViewModel<SeeAllTVShowsViewModel, SeeAllTVShowsViewModel.Factory>(
                    key = destination.type.toString()
                ) { factory -> factory.create(strategy) })
        }
        composable<Destinations.ViewAllScreen> { backStackEntry ->
            val destination = backStackEntry.toRoute<Destinations.ViewAllScreen>()
            val context = androidx.compose.ui.platform.LocalContext.current
            val entryPoint =
                EntryPointAccessors.fromApplication(context, StrategyFactoryEntryPoint::class.java)
            val strategy = entryPoint.viewAllFactory().create(destination.type)
            ViewAllScreen(
                viewModel = hiltViewModel<ViewAllViewModel, ViewAllViewModel.Factory>(
                    key = destination.type.toString()
                ) { factory -> factory.create(strategy) })
        }
        composable<Destinations.MyRatingScreen> {
            MyRatingScreen()
        }
        composable<Destinations.WatchListDetailsScreen> {
            WatchListDetailsScreen()
        }

    }
}