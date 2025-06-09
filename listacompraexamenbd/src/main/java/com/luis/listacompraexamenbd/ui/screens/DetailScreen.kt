package com.luis.listacompraexamenbd.ui.screens



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.luis.listacompraexamenbd.ui.AppViewModelProvider
import com.luis.listacompraexamenbd.ui.navigation.NavigationDestination
import com.luis.listacompraexamenbd.ui.viewmodel.ProductDetailViewModel

object DetailDestination : NavigationDestination {
    const val productNameArg = "productName"
    override val route = "detalle"
    val routeWithArgs = "$route/{$productNameArg}"
}



@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DetailScreen(
//    onNavigateBack: () -> Unit,
//    modifier: Modifier = Modifier,
//    viewModel: ProductDetailViewModel = viewModel(factory = AppViewModelProvider.Factory) // Usa el VM de detalle, NO el global
//) {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text(stringResource(R.string.detail_title)) },
//                navigationIcon = {
//                    IconButton(onClick = onNavigateBack) {
//                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
//                    }
//                }
//            )
//        }
//    ) { innerPadding ->
//        Column(
//            modifier = modifier
//                .padding(innerPadding)
//                .fillMaxSize()
//                .padding(16.dp),
//            horizontalAlignment = Alignment.Start,
//            verticalArrangement = Arrangement.Top
//        ) {
//            DetailsProductForm(
//                productDetailUiState = viewModel.productDetailUiState,
//                onCancel = onNavigateBack
//            )
//        }
//    }
//}
//
//// Formulario para mostrar los detalles del producto
//@Composable
//fun DetailsProductForm(
//    productDetailUiState: ProductDetailUiState,
//    onCancel: () -> Unit = {}
//) {
//    val details = productDetailUiState.productDetails
//    if (details != null) {
//        Text("Producto: ${details.productName}")
//        Spacer(modifier = Modifier.padding(vertical = 16.dp))
//        Text("Precio: ${details.productPrice}")
//    } else {
//        Text(
//            "Producto no encontrado.",
//            color = MaterialTheme.colorScheme.error
//        )
//    }
//    Spacer(modifier = Modifier.padding(vertical = 16.dp))
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.Center,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Button(onClick = onCancel) {
//            Text("Volver")
//        }
//    }
//}

@Composable
fun DetailScreen(
    onNavigateBack: () -> Unit,
    viewModel: ProductDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val details = viewModel.productDetailUiState.productDetails

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (details != null) {
            Text(
                text = "¡Has comprado ${details.productName} por ${details.productPrice} €!",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        } else {
            Text(
                text = "Producto no encontrado.",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        }
        Spacer(Modifier.height(32.dp))
        Button(onClick = onNavigateBack) {
            Text("Volver a la lista")
        }
    }
}