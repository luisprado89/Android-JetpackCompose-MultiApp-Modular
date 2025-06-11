package com.luis.listacomprapersistente.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.luis.listacomprapersistente.ui.AppViewModelProvider
import com.luis.listacomprapersistente.ui.components.ListaTopAppBar
import com.luis.listacomprapersistente.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

// Define un objeto que representa la pantalla de añadir productos para navegar a esta
object ProductAddDestination : NavigationDestination {
    override val route = "productAdd" // Define la ruta de navegación para esta pantalla
    override val title = "Añadir Procuto"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductAddScreen (
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: ProductAddViewModel = viewModel (factory = AppViewModelProvider.Factory)
    ) {
    val context = LocalContext.current//  mostrar Toast, Obtiene el contexto actual de la aplicación, permite acceder a recursos y funciones del sistema,
    val coroutineScope = rememberCoroutineScope()// Crea un alcance de corutina para manejar operaciones asíncronas
    Scaffold(
        topBar = {
            ListaTopAppBar(
                title = ProductAddDestination.title,
                canNavigateBack = true,
                navigateBack = navigateBack
            )
        }
/*
    Scaffold (// Crea una estructura de diseño con una barra superior
        topBar = {
            TopAppBar(
                title = {
                    Text("Añadir producto")// Título de la barra superior
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {// Botón para navegar hacia atrás
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")// Ícono de flecha hacia atrás
                    }
                }
            )
        }
*/

    ){//Scaffold
        Column (// Crea una columna para organizar los elementos verticalmente
            modifier = modifier.padding(it).fillMaxSize().padding(16.dp),// Aplica modificadores de diseño
            horizontalAlignment = Alignment.Start,// Alinea los elementos a la izquierda
            verticalArrangement = Arrangement.Top // Organiza los elementos en la parte superior
        ){
            AddProductForm(// Composable para el formulario de añadir producto
                productAddUiState = viewModel.productAddUiState,// Estado del UI del producto
                /**
                 * El operador '::' se utiliza para referenciar una función.
                 * En este caso, se está referenciando la función updateUiState del ViewModel.
                 * Esto se hace para que la función updateUiState se ejecute cada vez que se
                 * modifique el valor de un campo del formulario.
                 */
                onProductValueChanged = viewModel::updateUiState,// Referencia a la función para actualizar el estado del producto
                onAdd = { // Maneja la acción de añadir un producto
                    /**
                     * Hay que tener en cuenta que si el usuario rota la pantalla rápido,
                     * la operación podría cancelarse y el producto no se guardaría en bbdd.
                     * Esto ocurre cuándo un cambio de configuración (como la rotación de la pantalla)
                     * ocurre mientras una operación asíncrona está en curso.
                     * La actividad se destruye y se vuelve a crear, pero la operación asíncrona
                     * del rememberCoroutineScope se cancela, ya que forma parte de la recomposición.
                     */
                    coroutineScope.launch {// Lanza una corutina para manejar la operación asíncrona
                        if (viewModel.saveProduct()) navigateBack()// Si se guarda el producto, navega hacia atrás
                        else {
                            // Lanzamos un Toast indicando que ya existe el elemento
                            Toast.makeText(
                                context,
                                "El producto ya existe",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                navigateBack = navigateBack,// Pasa la función para navegar hacia atrás
            )
        }//Column
    }//Scaffold
}//ProductAddScreen

@Composable
fun AddProductForm( // Composable que representa el formulario de añadir producto
    productAddUiState: ProductAddUiState, // Estado del formulario con la información del producto
    onProductValueChanged: (ProductDetails) -> Unit, // Función para actualizar los valores del formulario
    onAdd: () -> Unit, // Función para añadir el producto
    navigateBack: () -> Unit // Función para navegar hacia atrás
) {
    //val focusManager = LocalFocusManager.current // Obtiene el gestor de enfoque para controlar el teclado
    OutlinedTextField(// Campo de entrada para el nombre del producto
        value = productAddUiState.productDetails.productName, // Valor del campo
        modifier = Modifier.fillMaxWidth(), // Hace que el campo ocupe todo el ancho disponible
        onValueChange = {// Actualiza el nombre del producto
            onProductValueChanged(productAddUiState.productDetails.copy(productName = it))
        },
        singleLine = true,// Restringe el campo a una sola línea
        label = { Text("Producto") }
    )
    Spacer(Modifier.size(8.dp))// Espacio entre los elementos
    Row (// Fila para organizar los elementos horizontalmente
        modifier = Modifier.fillMaxWidth(), // Hace que la fila ocupe todo el ancho disponible
        horizontalArrangement = Arrangement.SpaceBetween, // Espacia los elementos uniformemente
        verticalAlignment = Alignment.CenterVertically // Alinea los elementos verticalmente en el centro
    ) {
        Spacer(modifier = Modifier.padding(horizontal = 32.dp, vertical = 0.dp))
        Text("Cantidad") // Texto para indicar el campo de cantidad
        Spacer(modifier = Modifier.padding(horizontal = 32.dp, vertical = 0.dp))
        OutlinedTextField(// Campo de entrada para la cantidad del producto
            value = productAddUiState.productDetails.productQuantity,
            onValueChange = {
                onProductValueChanged(productAddUiState.productDetails.copy(productQuantity = it))
            },
            modifier = Modifier.width(86.dp)
                .onFocusChanged {
                    if (it.hasFocus)  {
                        onProductValueChanged(productAddUiState.productDetails.copy(productQuantity = ""))
                    } else if (productAddUiState.productDetails.productQuantity.isBlank()) {
                        onProductValueChanged(productAddUiState.productDetails.copy(productQuantity = "1"))
                    }
                },
            maxLines = 1, // Restringe a una línea
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) // Configura el teclado numérico
        )
    }
    Spacer(Modifier.size(5.dp))
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {//Row
        Spacer(modifier = Modifier.padding(horizontal = 32.dp, vertical = 0.dp))
        Text("Precio")
        Spacer(modifier = Modifier.padding(horizontal = 32.dp, vertical =  0.dp))
        OutlinedTextField(
            value = productAddUiState.productDetails.productPrice,
            onValueChange = {
                onProductValueChanged(productAddUiState.productDetails.copy(productPrice = it))
            },
            modifier = Modifier.width(86.dp)
                .onFocusChanged {//detecta cuándo un campo de entrada el usuario empieza a escribir o deja de escribir en él.
                    if (it.hasFocus)  {//devuelve true si el campo ha ganado el foco (el usuario hizo clic o empezó a escribir en él
                        onProductValueChanged(productAddUiState.productDetails.copy(productPrice = ""))
                    } else if (productAddUiState.productDetails.productPrice.isBlank()) {
                        onProductValueChanged(productAddUiState.productDetails.copy(productPrice = "1,0"))
                    }
                },
            maxLines = 1,// Restringe el campo a una sola línea
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        )
    }//Row
    Spacer(modifier = Modifier.padding(horizontal = 0.dp, vertical = 16.dp))
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {//Row
        Button(onClick = navigateBack) { Text("Cancelar") }
        Spacer(modifier = Modifier.padding(horizontal = 32.dp, vertical = 0.dp))
        Button(
            onClick = onAdd,
            enabled = productAddUiState.isSaveButtonEnabled//esta deshabilitado hasta que todos los campos tengan datos
        ) { Text("Añadir") }
    }//Row
}
