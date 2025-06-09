package com.luis.listacompraexamenbd.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luis.listacompraexamenbd.data.ProductRepository
import com.luis.listacompraexamenbd.ui.screens.DetailDestination
import kotlinx.coroutines.launch

data class ProductDetailUiState(
    val productDetails: ProductDetails? = null
)
data class ProductDetails(
    val productName: String,
    val productPrice: String
)

class ProductDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: ProductRepository
) : ViewModel() {
    var productDetailUiState by mutableStateOf(ProductDetailUiState())
        private set

    init {
        val productName = savedStateHandle.get<String>(DetailDestination.productNameArg) ?: ""
        viewModelScope.launch {
            val product = repository.getProductByName(productName)
            if (product != null) {
                productDetailUiState = ProductDetailUiState(
                    productDetails = ProductDetails(
                        productName = product.name,
                        productPrice = product.price.toString()
                    )
                )
            }
        }
    }
}
