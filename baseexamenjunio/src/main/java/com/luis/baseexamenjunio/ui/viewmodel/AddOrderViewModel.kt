package com.luis.baseexamenjunio.ui.viewmodel

import com.luis.baseexamenjunio.data.DataStoreManager
import com.luis.baseexamenjunio.data.Product
import com.luis.baseexamenjunio.data.productsDefault
import kotlinx.coroutines.flow.asStateFlow


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AddOrderViewModel(private val dataStore: DataStoreManager) : ViewModel() {
    private val _products = MutableStateFlow(productsDefault.map { it.copy() })
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _viewMode = MutableStateFlow("list")
    val viewMode: StateFlow<String> = _viewMode

    init {
        viewModelScope.launch {
            dataStore.viewMode.collect { mode ->
                _viewMode.value = mode
            }
        }
    }

    fun incrementQuantity(productName: String) {
        _products.value = _products.value.map {
            if (it.name == productName) it.copy(quantity = it.quantity + 1) else it
        }
    }

    fun decrementQuantity(productName: String) {
        _products.value = _products.value.map {
            if (it.name == productName) it.copy(quantity = (it.quantity - 1).coerceAtLeast(0)) else it
        }
    }

    fun resetQuantities() {
        _products.value = _products.value.map { it.copy(quantity = 0) }
    }

    fun setViewMode(mode: String) {
        viewModelScope.launch {
            dataStore.saveViewMode(mode)
        }
    }
}