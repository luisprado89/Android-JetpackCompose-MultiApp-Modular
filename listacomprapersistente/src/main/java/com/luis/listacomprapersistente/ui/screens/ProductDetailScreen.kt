package com.luis.listacomprapersistente.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.luis.listacomprapersistente.ui.AppViewModelProvider
import com.luis.listacomprapersistente.ui.components.ListaTopAppBar
import com.luis.listacomprapersistente.ui.navigation.NavigationDestination

// Objeto que representa la pantalla de detalles del producto
object ProductDetailDestination : NavigationDestination {
    override val route = "productDetails" // Define la ruta para la pantalla de detalles del producto
    override val title = "Details Product"
    const val productNameArg = "productName" // Define el argumento para el nombre del producto
    val routeWithArgs = "$route/{$productNameArg}" // Define la ruta con argumentos
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    navigateBack: () -> Unit, // Función para navegar hacia atrás
    navigateToEditProduct: (String) -> Unit, // Función para navegar a la pantalla de edición del producto
    modifier: Modifier = Modifier, // Modificador opcional para personalizar la apariencia
    viewModel: ProductDetailViewModel = viewModel(factory = AppViewModelProvider.Factory) // Obtiene el ViewModel para la pantalla
) {
    Scaffold(
        topBar = {
            ListaTopAppBar(
                title = ProductDetailDestination.title,
                canNavigateBack = true,
                navigateBack = navigateBack
            )
        },
/*
    Scaffold (// Crea un Scaffold que proporciona una estructura básica para la pantalla
        topBar = {// Crea una barra de aplicación superior
            TopAppBar(
                title = {
                    Text("Detalles del producto") // Título de la barra de aplicación
                },
                navigationIcon = { // Botón de navegación hacia atrás
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver") // Ícono de flecha hacia atrás
                    }
                }
            )
        }
*/

    ) {
        // Crea una columna para organizar los elementos verticalmente
        Column (
            modifier = modifier.padding(it).fillMaxSize().padding(16.dp), // Aplica relleno y tamaño máximo
            horizontalAlignment = Alignment.Start, // Alinea los elementos a la izquierda
            verticalArrangement = Arrangement.Top // Organiza los elementos en la parte superior
        ) {
            // Llama a la función para mostrar el formulario de detalles del producto
            DetailsProductForm(
                productDetailUiState = viewModel.productDetailUiState, // Estado de la UI del detalle del producto
                onGoToEdit = { navigateToEditProduct(
                    viewModel.productDetailUiState.productDetails.productName) }, // Navega a la edición del producto
                onCancel = navigateBack // Navega hacia atrás al cancelar
            )
        }
    }
}

// Función Composable que representa el formulario de detalles del producto
@Composable
fun DetailsProductForm(
    productDetailUiState: ProductDetailUiState, // Estado de la UI del detalle del producto
    onGoToEdit: () -> Unit = {}, // Función para navegar a la edición del producto
    onCancel: () -> Unit = {} // Función para cancelar
) {// Muestra el nombre del producto
    Text("Producto: ${productDetailUiState.productDetails.productName}")
    Spacer(modifier = Modifier.padding(horizontal = 0.dp, vertical = 16.dp)) // Espacio entre elementos
    // Muestra la cantidad del producto
    Text("Cantidad: ${productDetailUiState.productDetails.productQuantity}")
    Spacer(modifier = Modifier.padding(horizontal = 0.dp, vertical = 16.dp)) // Espacio entre elementos
    // Muestra el precio del producto
    Text("Precio: ${productDetailUiState.productDetails.productPrice}")
    Spacer(modifier = Modifier.padding(horizontal = 0.dp, vertical = 16.dp)) // Espacio entre elementos
    Row (// Crea una fila para organizar los botones horizontalmente
        modifier = Modifier.fillMaxWidth(), // Llena el ancho máximo disponible
        horizontalArrangement = Arrangement.Center, // Centra los elementos horizontalmente
        verticalAlignment = Alignment.CenterVertically // Alinea los elementos verticalmente al centro
    ) {
        Button( // Botón para cancelar
            onClick = onCancel, // Acción al hacer clic
        ) {
            Text("Cancelar") // Texto del botón
        }
        Spacer(modifier = Modifier.padding(32.dp, 0.dp)) // Espacio entre botones
        Button(// Botón para editar
            onClick = onGoToEdit, // Acción al hacer clic
        ) {
            Text("Editar") // Texto del botón
        }
    }
}
