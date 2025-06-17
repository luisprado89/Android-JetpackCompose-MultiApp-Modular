package com.luis.baseexamenjunio.data


data class Order(
    val id: Int,
    val state: OrderState,
    val products: List<Product>,
) {
    fun totalPrice(): Double {
        return products.sumOf { it.totalPrice() }
    }

    fun totalQuantity(): Int {
        return products.sumOf { it.quantity }
    }
}

enum class OrderState {
    IN_PROGRESS,
    READY,
    CANCELLED
}