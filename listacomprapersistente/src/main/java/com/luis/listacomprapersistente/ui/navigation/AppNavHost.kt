package com.luis.listacomprapersistente.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import com.luis.listacomprapersistente.ui.screens.ListDestination
import com.luis.listacomprapersistente.ui.screens.ListScreen
import com.luis.listacomprapersistente.ui.screens.ProductAddDestination
import com.luis.listacomprapersistente.ui.screens.ProductAddScreen
import com.luis.listacomprapersistente.ui.screens.ProductDetailDestination
import com.luis.listacomprapersistente.ui.screens.ProductDetailScreen
import com.luis.listacomprapersistente.ui.screens.ProductUpdateDestination
import com.luis.listacomprapersistente.ui.screens.ProductUpdateScreen

/*
La Interfaz NavigationDestination

---------------------------------------------------------------------------------
package com.luisdpc.listacompraroom.ui.navigation
// Define una interfaz llamada NavigationDestination.
interface NavigationDestination {
    // Propiedad `route` que debe devolver una cadena de texto representando la ruta de navegación.
    val route: String
}
---------------------------------------------------------------------------------

Define una interfaz común para todos los destinos de navegación en la aplicación.

La Implementación de la interfaz NavigationDestination con ListDestination que esta dentro del composable fun ListScreen()

---------------------------------------------------------------------------------
object ListDestination : NavigationDestination {
    override val route = "list"
}
---------------------------------------------------------------------------------

ListDestination: NavigationDestination es un object (una implementación singleton) que implementa la interfaz NavigationDestination.
route: Aquí, route es igual a "list", lo que significa que la ruta para llegar a la pantalla que representa ListDestination es "list".

Cada composable tiene un route que corresponde a la URL o la ruta de la pantalla y un bloque de código que representa el contenido de esa pantalla.
*/
@Composable
fun AppNavHost(
    navController: NavHostController// Controlador de navegación que gestiona la navegación entre pantallas.
) {
    NavHost(// Define la estructura de navegación usando NavHost.
        navController = navController,// El controlador de navegación.
        startDestination = ListDestination.route,// Pantalla de inicio, que es ListScreen.
    ) {
        composable(route = ListDestination.route) {// Pantalla principal de la lista de productos.
            ListScreen(
                navigateToAddProduct = {
                    navController.navigate(ProductAddDestination.route)
                },
                navigateToProductDetails = {
                    navController.navigate("${ProductDetailDestination.route}/$it")
                },
                navigateToProductUpdate = {
                    navController.navigate("${ProductUpdateDestination.route}/$it")
                }
            )
        }
        composable(route = ProductAddDestination.route) {// Pantalla para añadir un nuevo producto.
            ProductAddScreen(
                navigateBack = { navController.popBackStack() },// Permite regresar a la pantalla anterior.
            )
        }// Pantalla de detalles de un producto específico. roteWithArgs le indicamos que va con argumentos
        composable( route = ProductDetailDestination.routeWithArgs,//
            arguments = listOf(navArgument(ProductDetailDestination.productNameArg) {
                type = NavType.StringType// El argumento es un nombre de producto que es un String.
            })
        ) {
            ProductDetailScreen(
                navigateBack = { navController.popBackStack() },// Permite regresar a la pantalla anterior.
                navigateToEditProduct = {
                    navController.navigate("${ProductUpdateDestination.route}/$it")// Navega a la pantalla de actualización del producto.
                }
            )
        }// Pantalla de actualización de un producto específico.
        composable( route = ProductUpdateDestination.routeWithArgs,
            arguments = listOf(navArgument(ProductUpdateDestination.productNameArg) {
                type = NavType.StringType // El argumento es un nombre de producto que es un String.
            })
        ) {
            ProductUpdateScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}