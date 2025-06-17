package com.luis.baseexamenjunio.ui.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luis.baseexamenjunio.data.Order
import com.luis.baseexamenjunio.data.OrderState
import com.luis.baseexamenjunio.data.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()

    private var nextId = 1

    fun addOrder(products: List<Product>) {
        val filteredProducts = products.filter { it.quantity > 0 }
        if (filteredProducts.isEmpty()) return

        val newOrder = Order(
            id = nextId++,
            state = OrderState.IN_PROGRESS,
            products = filteredProducts.map { it.copy() }
        )
        _orders.value = _orders.value + newOrder
    }

    fun updateOrderState(orderId: Int, state: OrderState) {
        _orders.value = _orders.value.map {
            if (it.id == orderId) it.copy(state = state) else it
        }
    }

    fun deleteOrder(orderId: Int) {
        _orders.value = _orders.value.filter { it.id != orderId }
    }
}