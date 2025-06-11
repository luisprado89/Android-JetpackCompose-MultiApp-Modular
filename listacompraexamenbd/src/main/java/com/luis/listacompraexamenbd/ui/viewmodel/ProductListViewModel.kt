package com.luis.listacompraexamenbd.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luis.listacompraexamenbd.data.Product
import com.luis.listacompraexamenbd.data.ProductRepository
import com.luis.listacompraexamenbd.data.products
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.any
import kotlin.collections.random
import kotlin.ranges.random
import kotlin.text.equals
import kotlin.text.isBlank
import kotlin.text.toFloatOrNull
import kotlin.text.trim

// --------------------------------------------------
// UiState SIN showError ni errorMessage
// --------------------------------------------------
data class ProductListUiState(
    val products: List<Product> = emptyList(),
    val nameInput: String = "",
    val priceInput: String = ""
)

class ProductListViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        // Inserción inicial de productos falsos si la BD está vacía
        viewModelScope.launch {
            productRepository.getAllProductsStream().collect { productos ->
                if (productos.isEmpty()) {
                    productRepository.insertProducts(getFakeProducts())
                }
            }
        }
        // Observador de cambios
        viewModelScope.launch {
            productRepository.getAllProductsStream().collect { productos ->
                _uiState.update { it.copy(products = productos) }
            }
        }
    }

    fun onNameChanged(newValue: String) {
        _uiState.update { it.copy(nameInput = newValue) }
    }

    fun onPriceChanged(newValue: String) {
        _uiState.update { it.copy(priceInput = newValue) }
    }

    /**
     * Devuelve `true` si el producto se insertó correctamente,
     * `false` si falló la validación (nombre/precio vacíos, precio <= 0 o duplicado).
     * En la UI podrás usar este Boolean para lanzar el Toast.
     */
//    fun addProduct(): Boolean {
//        val name = _uiState.value.nameInput.trim()
//        val priceText = _uiState.value.priceInput.trim()
//
//        // Validaciones básicas
//        if (name.isBlank() || priceText.isBlank()) {
//            return false
//        }
//        val price = priceText.toFloatOrNull()
//        if (price == null || price <= 0f) {
//            return false
//        }
//        // ¿Ya existe?
//        if (_uiState.value.products.any { it.name.equals(name, true) }) {
//            // opcional: limpiar inputs en caso de duplicado
//            _uiState.update { it.copy(nameInput = "", priceInput = "") }
//            return false
//        }
//
//        // Si todo OK, inserta y limpia los inputs
//        viewModelScope.launch {
//            productRepository.insertProduct(Product(name, price, 1))
//        }
//        _uiState.update { it.copy(nameInput = "", priceInput = "") }
//        return true
//    }

    //No necesitamos hacer comprobaciones ya que lo estamos haciendo en el ListaCompraScreen
    fun addProduct() {
        val name = _uiState.value.nameInput.trim()
        val price = _uiState.value.priceInput.trim().toFloat() // ya has validado en la UI

        viewModelScope.launch {
            productRepository
                .insertProduct(Product(
                    name,//name → el nombre que ha metido el usuario
                    price,//el precio validado
                    1))// 1, es decir, al añadir por primera vez el producto aparecerá con una unidad en la lista
        }
        // limpiar inputs
        _uiState.update { it.copy(nameInput = "", priceInput = "") }
    }




    fun addRandomProduct() {
        viewModelScope.launch {
            var newProduct: Product
            do {
                newProduct = getFakeProduct()
            } while (_uiState.value.products.any { it.name == newProduct.name })
            productRepository.insertProduct(newProduct)
        }
    }

    fun increaseQuantity(product: Product) {
        viewModelScope.launch {
            productRepository.updateProduct(product.copy(quantity = product.quantity + 1))
        }
    }

    fun decreaseQuantity(product: Product) {
        viewModelScope.launch {
            if (product.quantity > 1) {
                productRepository.updateProduct(product.copy(quantity = product.quantity - 1))
            } else {
                productRepository.deleteProduct(product)
            }
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            productRepository.deleteProduct(product)
        }
    }

    // --------------------------------------------------
    // Generación de productos de ejemplo
    // --------------------------------------------------
    fun getFakeProducts(size: Int = 10): List<Product> {
        val usedNames = mutableSetOf<String>()
        val productList = mutableListOf<Product>()
        while (productList.size < size) {
            val p = getFakeProduct()
            if (usedNames.add(p.name)) {
                productList.add(0, p)// Inserta al principio en lugar de al final
                //no se va a poner al principio ya que estamos llamando a getAllProductsStream
                // dentro de loadProducts() y esto ordena el nombre de a a la z
                //@Query("SELECT * FROM products ORDER BY name ASC")
                //    fun getAllProducts(): Flow<List<Product>>
            }
        }
        return productList
    }

    fun getFakeProduct(): Product {
        val name = products.random()
        val price = (1..10).random().toFloat()
        val quantity = (1..5).random()
        return Product(name = name, price = price, quantity = quantity)
    }
}
