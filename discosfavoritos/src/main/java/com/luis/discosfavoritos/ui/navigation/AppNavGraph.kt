package com.luis.discosfavoritos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.luis.discosfavoritos.ui.add.AddDestination
import com.luis.discosfavoritos.ui.add.AddScreen
import com.luis.discosfavoritos.ui.detail.DetailDestination
import com.luis.discosfavoritos.ui.detail.DetailScreen
import com.luis.discosfavoritos.ui.home.HomeDestination
import com.luis.discosfavoritos.ui.home.HomeScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
    ) {
        composable(HomeDestination.route) {
            HomeScreen(
                onNavigateToAdd = {
                    navController.navigate(AddDestination.route)
                },
                onNavigateToDetail = {
                    navController.navigate("${DetailDestination.route}/$it")
                },
                modifier = modifier,
            )
        }
        composable(AddDestination.route) {
            AddScreen(
                onNavigateBack = { navController.popBackStack() },
                modifier = modifier,
            )
        }
        composable(
            route = DetailDestination.routeWithArgs,
            arguments = listOf(navArgument(DetailDestination.detailIdArg) {
                type = NavType.IntType
            })
        ) {
            DetailScreen(
                onNavigateBack = { navController.popBackStack() },
                modifier = modifier,
            )
        }
    }
}