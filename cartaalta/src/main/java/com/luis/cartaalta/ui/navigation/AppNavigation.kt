package com.luis.cartaalta.ui.navigation

import androidx.compose.runtime.Composable // Importa la anotación @Composable para funciones de Jetpack Compose
import androidx.navigation.NavType // Importa NavType para definir tipos de argumentos en la navegación
import androidx.navigation.compose.NavHost // Importa NavHost, el contenedor principal de navegación
import androidx.navigation.compose.composable // Importa composable para definir rutas en la navegación
import androidx.navigation.compose.rememberNavController // Importa rememberNavController para gestionar la navegación
import androidx.navigation.navArgument // Importa navArgument para manejar argumentos en rutas
import com.luis.cartaalta.ui.screens.GameOverScreen
import com.luis.cartaalta.ui.screens.GameScreen
import com.luis.cartaalta.ui.screens.HomeScreen

// Función composable que define la navegación de la aplicación
@Composable
fun AppNavigation() {
    val navController = rememberNavController()// Crea un controlador de navegación para gestionar las rutas

    NavHost(
        navController = navController,// Define el controlador de navegación
        startDestination = AppScreens.HOME.name// Establece la pantalla de inicio como la primera en mostrarse
    )
    {
        composable(AppScreens.HOME.name) {// Definición de la pantalla de inicio
            HomeScreen(
                onPlay = {
                    navController.navigate(AppScreens.GAME.name)// Navega a la pantalla del juego cuando se pulsa "Jugar"
                }
            )
        }
        composable(
            AppScreens.GAME.name // Definición de la pantalla del juego
        ) {
            GameScreen(
                onBackToMenu = { navController.popBackStack() }// Vuelve a la pantalla anterior cuando se pulsa "Atrás"
            ) {
                navController.navigate(AppScreens.GAME_OVER.name + "/${it}")
            }
        }
        composable(// Definición de la pantalla de Game Over con argumento "ganador"
            AppScreens.GAME_OVER.name+ "/{ganador}",// Ruta con un argumento dinámico "ganador"
            arguments = listOf(navArgument("ganador") { type = NavType.StringType }) ) {// Define el argumento como String
            GameOverScreen(//ganador es te tipo String en GameScreenState
                ganador = it.arguments?.getString("ganador") ?: "Jugador 1",// Recupera el argumento "ganador", o usa "Jugador 1" si es nulo
                onRestart = {
                    navController.popBackStack(AppScreens.HOME.name, false)// Vuelve a la pantalla de inicio y limpia el historial de navegación
                }
            )
        }
    }

}