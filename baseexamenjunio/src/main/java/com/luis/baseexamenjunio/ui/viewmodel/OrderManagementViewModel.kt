package com.luis.baseexamenjunio.ui.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luis.baseexamenjunio.data.DataStoreManager
import com.luis.baseexamenjunio.data.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlin.collections.sortedByDescending

class OrderManagementViewModel(
    internal val orderViewModel: OrderViewModel,
    private val dataStore: DataStoreManager
) : ViewModel() {
    private val _sortCriteria = MutableStateFlow("price")
    val sortCriteria: StateFlow<String> = _sortCriteria

    val sortedOrders: StateFlow<List<Order>> = combine(
        orderViewModel.orders,
        _sortCriteria
    ) { orders, criteria ->
        when (criteria) {
            "price" -> orders.sortedByDescending { it.totalPrice() }
            "quantity" -> orders.sortedByDescending { it.totalQuantity() }
            else -> orders
        }
    } as StateFlow<List<Order>>

    init {
        viewModelScope.launch {
            dataStore.sortCriteria.collect { criteria ->
                _sortCriteria.value = criteria
            }
        }
    }

    fun setSortCriteria(criteria: String) {
        viewModelScope.launch {
            dataStore.saveSortCriteria(criteria)
        }
    }
}