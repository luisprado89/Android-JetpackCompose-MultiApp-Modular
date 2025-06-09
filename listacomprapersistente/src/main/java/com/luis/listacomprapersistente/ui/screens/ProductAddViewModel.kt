package com.luis.listacomprapersistente.ui.screens

import android.database.sqlite.SQLiteConstraintException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.luis.listacomprapersistente.data.Product
import com.luis.listacomprapersistente.data.ProductRepository
import kotlin.toString

/**
 * UI representation of the product details
 * Representación de la UI con los detalles del producto
 */
//Representa los detalles del producto como los introduce el usuario
data class ProductDetails(//Como son Texfield que tienen que trabajar con texto (String)
    val productName: String = "",// Nombre del producto
    val productPrice: String = "",// Precio del producto en formato String
    val productQuantity: String = "",// Cantidad del producto en formato String
)


/**
 * UI state for the ProductAddScreen
 */
//Representa el estado de la UI en la pantalla de agregar producto
data class ProductAddUiState(
    val productDetails: ProductDetails = ProductDetails(),// Contiene los detalles del producto
    val isSaveButtonEnabled: Boolean = false,// Indica si el botón de 'Añadir' está habilitado
)

/**
 * Función de extensión para convertir un [ProductDetails] en un [Product]
 * @return un objeto [Product] con los valores convertidos desde [ProductDetails]
 * Si la conversión no es posible, se utilizan los valores predeterminados
 */
fun ProductDetails.toProduct(): Product {
    return Product(
        name = productName,
        price = productPrice.toDoubleOrNull() ?: 1.0,// Convierte el precio a Double, si no es válido usa 1.0
        quantity = productQuantity.toIntOrNull() ?: 1// Convierte la cantidad a Int, si no es válida usa 1
    )
}

/**
 * Función de extensión para convertir un [Product]  en un [ProductDetails]
 * @return un [ProductDetails] con los valores del [Product]
 */
fun Product.toProductDetails(): ProductDetails {//Se usa en ProductDetails
    return ProductDetails(
        productName = name,// Asigna el nombre del producto
        productPrice = price.toString(),// Convierte el precio a String
        productQuantity = quantity.toString()// Convierte la cantidad a String
    )
}
//ViewModel para manejar la lógica de la pantalla de agregar producto
class ProductAddViewModel (
    private val productRepository: ProductRepository
) : ViewModel() {

    // Estado mutable que representa el estado de la UI
    var productAddUiState by mutableStateOf(ProductAddUiState())
        private set // Solo puede ser modificado dentro del ViewModel

    /**
     * Valida que el formulario no tenga campos vacíos
     */
    private fun validateInput(productDetails: ProductDetails = productAddUiState.productDetails) : Boolean{
        return with (productDetails){ // Devuelve true si todos los campos tienen datos
            productName.isNotBlank() && productPrice.isNotBlank() && productQuantity.isNotBlank()
        }
    }
    /**
     * Actualiza el estado de la UI con los detalles del producto
     */
    fun updateUiState(productDetails: ProductDetails) {
        productAddUiState = productAddUiState.copy(
            productDetails = productDetails, // Actualiza los detalles del producto
            isSaveButtonEnabled = validateInput(productDetails) // Valida si los datos son correctos
        )
    }

    /**
     * Guarda un producto en la base de datos
     * Captura la SQLException que se produce si el producto ya existe y devuelve false en ese caso
     */

    suspend fun saveProduct(): Boolean {
        if (validateInput(productAddUiState.productDetails)) {// Verifica si la entrada es válida
            try { // Inserta el producto en la BD, convertido en formato String, Double, Int con el toProduct()
                productRepository.insertProduct(productAddUiState.productDetails.toProduct())
                return true // Retorna true si se guardó correctamente
            } catch (e: SQLiteConstraintException) { // Captura la excepción si el producto ya existe
                updateUiState(productAddUiState.productDetails.copy(productName = "")) // Limpia el nombre del producto
                return false // Retorna false porque no se pudo guardar ya que el producto ya existe
            }
        } else return false // Retorna false si la validación falló
    }
}