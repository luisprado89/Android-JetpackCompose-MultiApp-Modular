package com.luis.listacomprapersistente

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.luis.listacomprapersistente.ui.navigation.AppNavHost

@Composable
fun ListaCompraApp (navController: NavHostController = rememberNavController()){
    AppNavHost(navController)
}