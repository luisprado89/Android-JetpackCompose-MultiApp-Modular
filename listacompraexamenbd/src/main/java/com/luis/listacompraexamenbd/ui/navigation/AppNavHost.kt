package com.luis.listacompraexamenbd.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.luis.listacompraexamenbd.ui.screens.*

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                onNavigateToList = {
                    navController.navigate(ListaCompraDestination.route)
                }
            )
        }
        composable(route = ListaCompraDestination.route) {
            ListaCompraScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToDetail = {
                    navController.navigate("${DetailDestination.route}/$it")
                }
            )
        }

        composable(
            route = DetailDestination.routeWithArgs,
            arguments = listOf(navArgument(DetailDestination.productNameArg) {
                type = NavType.StringType
            })
        ) {
            DetailScreen(
                onNavigateBack = { navController.popBackStack() },
            )
        }

    }
}
