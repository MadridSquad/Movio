//package com.madrid.presentation.navigation.route
//
//import androidx.navigation.NavController
//import androidx.navigation.NavGraphBuilder
//import androidx.navigation.compose.composable
//import androidx.navigation.toRoute
//import com.madrid.presentation.navigation.Destinations
//import com.madrid.presentation.screens.loginScreen.component.ForgotPassword
//import com.madrid.presentation.screens.loginScreen.component.WebViewScreen
//
//fun NavGraphBuilder.signUpRoute(navController: NavController) {
//    composable<Destinations.WebViewScreen> { backStackEntry ->
//        val url = backStackEntry.toRoute<Destinations.WebViewScreen>().url
//        WebViewScreen(
//            url = url,
//            navController = navController
//        )
//    }
//
//}
