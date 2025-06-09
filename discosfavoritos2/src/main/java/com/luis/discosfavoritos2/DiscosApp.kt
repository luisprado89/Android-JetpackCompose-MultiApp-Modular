package com.luis.discosfavoritos2

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.luis.discosfavoritos2.ui.navigation.AppNavHost

/*
* Top level composable that represents screens for the application.
* Composable de nivel superior que representa las pantallas de la aplicación.
 */

@Composable
fun ListaDiscosApp(
    navController: NavHostController = rememberNavController()
    ) {
    AppNavHost(navController)
    //En caso de dar errorse cambia por  AppNavHost(navController = navController)
}
