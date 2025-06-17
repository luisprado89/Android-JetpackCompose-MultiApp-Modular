package com.luis.baseexamenjunio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.luis.baseexamenjunio.data.DataStoreManager
import com.luis.baseexamenjunio.ui.screens.AddOrderScreen
import com.luis.baseexamenjunio.ui.screens.OrderManagementScreen
import com.luis.baseexamenjunio.ui.theme.AndroidJetpackComposeMultiAppModularTheme
import com.luis.baseexamenjunio.ui.viewmodel.AddOrderViewModel
import com.luis.baseexamenjunio.ui.viewmodel.OrderManagementViewModel
import com.luis.baseexamenjunio.ui.viewmodel.OrderViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidJetpackComposeMultiAppModularTheme {
                val dataStore = DataStoreManager(this)
                val orderViewModel: OrderViewModel = viewModel()
                val navController = rememberNavController()

                NavHost(navController, startDestination = "add") {
                    composable("add") {
                        AddOrderScreen(
                            viewModel = viewModel(factory = AddOrderViewModelFactory(dataStore)),
                            orderViewModel = orderViewModel,
                            onNavigateToOrders = { navController.navigate("orders") }
                        )
                    }
                    composable("orders") {
                        OrderManagementScreen(
                            viewModel = viewModel(factory = OrderManagementViewModelFactory(orderViewModel, dataStore)),
                            onNavigateToAddOrder = { navController.navigate("add") }
                        )
                    }
                }
            }
        }
    }
}

// Factories para ViewModels
class AddOrderViewModelFactory(private val dataStore: DataStoreManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddOrderViewModel(dataStore) as T
    }
}

class OrderManagementViewModelFactory(
    private val orderViewModel: OrderViewModel,
    private val dataStore: DataStoreManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OrderManagementViewModel(orderViewModel, dataStore) as T
    }
}