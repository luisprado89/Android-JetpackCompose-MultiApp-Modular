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


data class ProductListUiState(
    val products: List<Product> = emptyList(),
    val nameInput: String = "",
    val priceInput: String = "",
    val showError: Boolean = false,
    val errorMessage: String = ""
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
        viewModelScope.launch {
            productRepository.getAllProductsStream().collect { products ->
                if (products.isEmpty()) {
                    // Insertar productos aleatorios al iniciar si la base está vacía
                    val fakeProducts = getFakeProducts()
                    productRepository.insertProducts(fakeProducts)
                }
            }
        }
        viewModelScope.launch {
            productRepository.getAllProductsStream().collect { products ->
                _uiState.update { it.copy(products = products) }
            }
        }
    }

    fun onNameChanged(newValue: String) {
        _uiState.update { it.copy(nameInput = newValue) }
    }

    fun onPriceChanged(newValue: String) {
        _uiState.update { it.copy(priceInput = newValue) }
    }

    fun addProduct() {
        val name = _uiState.value.nameInput.trim()
        val priceText = _uiState.value.priceInput.trim()
        if (name.isBlank() || priceText.isBlank()) {
            showError("El nombre y el precio no pueden estar vacíos.")
            return
        }
        val price = priceText.toFloatOrNull()
        if (price == null || price <= 0f) {
            showError("Introduce un precio válido mayor que 0.")
            return
        }
        /*
        it.name.equals(name, true)
        El segundo parámetro (true) indica que la comparación no distingue mayúsculas/minúsculas.
        Así:
        "casa".equals("Casa", true) devuelve true
        "casa".equals("CASA", true) devuelve true
        "casa".equals("casa", true) devuelve true
         */
        if (_uiState.value.products.any { it.name.equals(name, true) }) {
            showError("El producto ya existe en la lista.")
            _uiState.update { it.copy(nameInput = "", priceInput = "") }
            return
        }
        viewModelScope.launch {
            productRepository.insertProduct(Product(name, price, 1))
            _uiState.update { it.copy(nameInput = "", priceInput = "") }
        }
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

    fun clearError() {
        _uiState.update { it.copy(showError = false, errorMessage = "") }
    }

    private fun showError(msg: String) {
        _uiState.update { it.copy(showError = true, errorMessage = msg) }
    }

    fun getFakeProducts(size: Int = 10): List<Product> {
        val usedNames = mutableSetOf<String>()
        val productList = mutableListOf<Product>()
        while (productList.size < size) {
            val p = getFakeProduct()
            if (usedNames.add(p.name)) {
                productList.add(p)
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