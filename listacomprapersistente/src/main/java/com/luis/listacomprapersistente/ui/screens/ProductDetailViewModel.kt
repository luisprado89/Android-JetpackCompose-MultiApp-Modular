package com.luis.listacomprapersistente.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luis.listacomprapersistente.data.ProductRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

data class ProductDetailUiState(
    val productDetails: ProductDetails = ProductDetails()
)

// ViewModel que maneja la lógica de la pantalla de detalles del producto
class ProductDetailViewModel(
    savedStateHandle: SavedStateHandle, // Maneja el estado guardado
    private val productRepository: ProductRepository // Repositorio para acceder a los productos
) : ViewModel() {
    // Propiedad mutable que representa el estado de la UI del detalle del producto
    var productDetailUiState by mutableStateOf(ProductDetailUiState()) // Inicializa el estado con un objeto vacío
        private set // Hace que el setter sea privado para que solo se pueda modificar dentro de la clase

    // Bloque de inicialización del ViewModel
    init {
        // Obtiene el nombre del producto desde el estado guardado, o una cadena vacía si no existe
        val productName = savedStateHandle.get<String>("productName") ?: ""
        // Crea un flujo que obtiene los detalles del producto y filtra los valores nulos
        val productDetails = productRepository
            .getProductStream(productName).filterNotNull().map {
                it.toProductDetails() // Convierte el producto a sus detalles, este esta en ProductAddViewModel
            }
        // Lanza una corutina para recolectar los detalles del producto
        viewModelScope.launch {
            productDetails.collect { // Recolecta los detalles del producto
                updateUiState(it) // Actualiza el estado de la UI con los nuevos detalles
            }
        }
    }

    // Función privada que actualiza el estado de la UI con los detalles del producto
    private fun updateUiState(productDetails: ProductDetails) {
        productDetailUiState = ProductDetailUiState(productDetails) // Asigna los nuevos detalles al estado
    }
}