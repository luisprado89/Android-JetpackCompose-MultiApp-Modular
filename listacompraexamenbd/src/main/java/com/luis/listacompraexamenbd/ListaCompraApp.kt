package com.luis.listacompraexamenbd

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.luis.listacompraexamenbd.ui.navigation.AppNavHost

@Composable
fun ListaCompraApp(navController: NavHostController = rememberNavController()) {
    AppNavHost(navController)
}