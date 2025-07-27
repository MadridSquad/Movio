package com.madrid.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.component.CustomBottomBar
import com.madrid.presentation.component.navBarDestinations

val LocalNavController = compositionLocalOf<NavHostController> { error("No Nav Controller Found") }


@Composable
fun MovioNavGraph(navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route

    val currentDestination = navBarDestinations.find { destinationItem ->
        destinationItem.destination.toString() == currentRoute?.substringAfterLast(".")    }

    Column(
        Modifier
            .fillMaxSize()
            .background(
                color = Theme.color.surfaces.surface
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MovioNavHost(navController)
        }

        AnimatedVisibility(
            visible = currentDestination != null,
            enter = slideInVertically(
                animationSpec = tween(0),
                initialOffsetY = { it}
            ),
            exit = slideOutVertically(
                animationSpec = tween(0),
                targetOffsetY = {it}
            )
        ) {
            CustomBottomBar(
                currentDestination = currentDestination?.destination ?: Destinations.HomeScreen,
                navItems = navBarDestinations,
                onNavDestinationClicked = { destination ->
                    navController.navigate(destination)
                },
                modifier = Modifier.navigationBarsPadding()
            )
        }
    }
}