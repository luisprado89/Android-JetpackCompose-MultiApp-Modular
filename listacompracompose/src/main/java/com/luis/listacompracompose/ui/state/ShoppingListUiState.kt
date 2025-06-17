package com.luis.listacompracompose.ui.state
import com.luis.listacompracompose.model.Product

data class ShoppingListUiState (
    val list: List<Product> = emptyList(),
    val newProduct: String = "",
    val isSomethingChecked: Boolean = false
)

