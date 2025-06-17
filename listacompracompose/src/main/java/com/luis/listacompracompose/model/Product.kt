package com.luis.listacompracompose.model

import com.luis.listacompracompose.data.productsExample


data class Product(
    val name: String, var checked: Boolean = false
) {}

fun getFakeShoppingProducts() = productsExample.map { Product(it) }
