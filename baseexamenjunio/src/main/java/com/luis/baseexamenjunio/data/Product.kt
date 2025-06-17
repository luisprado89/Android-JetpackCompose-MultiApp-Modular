package com.luis.baseexamenjunio.data

data class Product(
    val name: String,
    val price: Double,
    var quantity: Int,
) {
    fun totalPrice(): Double {
        return price * quantity
    }
}