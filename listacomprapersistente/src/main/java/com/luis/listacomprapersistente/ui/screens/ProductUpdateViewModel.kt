package com.luis.listacomprapersistente.ui.screens// Paquete donde se encuentra la clase

// Importaciones necesarias para la gestión del estado y el ViewModel
import androidx.compose.runtime.getValue // Importa la función para obtener el valor de una propiedad mutable
import androidx.compose.runtime.mutableStateOf // Importa la función para crear un estado mutable
import androidx.compose.runtime.setValue // Importa la función para establecer el valor de una propiedad mutable
import androidx.lifecycle.SavedStateHandle // Importa la clase para manejar el estado guardado
import androidx.lifecycle.ViewModel // Importa la clase base ViewModel
import androidx.lifecycle.viewModelScope // Importa el scope de corutinas para el ViewModel
import com.luis.listacomprapersistente.data.ProductRepository
import kotlinx.coroutines.flow.filterNotNull // Importa la función para filtrar elementos no nulos de un flujo
import kotlinx.coroutines.flow.map // Importa la función para transformar los elementos de un flujo
import kotlinx.coroutines.launch // Importa la función para lanzar corutinas
import kotlin.text.isNotBlank

// Clase de datos que representa el estado de la UI para la actualización del producto
data class ProductUpdateUiState(
    val productDetails: ProductDetails = ProductDetails(), // Detalles del producto, inicializado con un objeto vacío
    val isSaveButtonEnabled: Boolean = false // Indica si el botón de guardar está habilitado
)

// ViewModel que maneja la lógica de la pantalla de actualización del producto
class ProductUpdateViewModel(
    savedStateHandle: SavedStateHandle, // Maneja el estado guardado
    private val productRepository: ProductRepository // Repositorio para acceder a los productos
) : ViewModel() {
    // Propiedad mutable que representa el estado de la UI de la actualización del producto
    var productUpdateUiState by mutableStateOf(ProductUpdateUiState()) // Inicializa el estado con un objeto vacío
        private set // Hace que el setter sea privado para que solo se pueda modificar dentro de la clase

    // Bloque de inicialización del ViewModel
    init {
        // Obtiene el nombre del producto desde el estado guardado, o una cadena vacía si no existe
        val productName = savedStateHandle.get<String>("productName") ?: ""
        // Crea un flujo que obtiene los detalles del producto y filtra los valores nulos
        val productDetails = productRepository.getProductStream(productName).filterNotNull().map {
            it.toProductDetails() // Convierte el producto a sus detalles
        }
        // Lanza una corutina para recolectar los detalles del producto
        viewModelScope.launch {
            productDetails.collect { // Recolecta los detalles del producto
                updateUiState(it) // Actualiza el estado de la UI con los nuevos detalles
            }
        }
    }
    /**
     * Valida que el formulario no tenga campos vacíos
     */
    private fun validateInput(productDetails: ProductDetails = productUpdateUiState.productDetails): Boolean {
        return with(productDetails) {
            // Verifica que el nombre, precio y cantidad del producto no estén vacíos
            productName.isNotBlank() && productPrice.isNotBlank() && productQuantity.isNotBlank()
        }
    }
    // Función que actualiza el estado de la UI con los detalles del producto
    fun updateUiState(productDetails: ProductDetails) {
        productUpdateUiState = productUpdateUiState.copy(
            productDetails = productDetails, // Actualiza los detalles del producto
            isSaveButtonEnabled = validateInput(productDetails) // Habilita o deshabilita el botón de guardar según la validación
        )
    }

    // Función suspendida que actualiza el producto en el repositorio
    suspend fun updateProduct() {
        if (validateInput()) { // Valida la entrada antes de actualizar
            productRepository.updateProduct(productUpdateUiState.productDetails.toProduct()) // Actualiza el producto en el repositorio
        }
    }

}