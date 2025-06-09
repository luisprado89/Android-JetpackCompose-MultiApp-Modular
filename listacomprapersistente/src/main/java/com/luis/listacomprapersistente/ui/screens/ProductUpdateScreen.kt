// Paquete donde se encuentra la clase
package com.luis.listacomprapersistente.ui.screens

// Importaciones necesarias para la interfaz de usuario y la navegación
import androidx.compose.foundation.layout.Arrangement // Importa la clase para definir la disposición de los elementos
import androidx.compose.foundation.layout.Column // Importa la clase para crear una columna de elementos
import androidx.compose.foundation.layout.Row // Importa la clase para crear una fila de elementos
import androidx.compose.foundation.layout.Spacer // Importa la clase para crear un espacio entre elementos
import androidx.compose.foundation.layout.fillMaxSize // Importa la función para llenar el tamaño máximo disponible
import androidx.compose.foundation.layout.fillMaxWidth // Importa la función para llenar el ancho máximo disponible
import androidx.compose.foundation.layout.padding // Importa la función para agregar relleno a los elementos
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width // Importa la función para establecer el ancho de un elemento
import androidx.compose.foundation.text.KeyboardOptions // Importa la clase para opciones de teclado
import androidx.compose.material.icons.Icons // Importa el objeto que contiene los íconos
import androidx.compose.material.icons.automirrored.filled.ArrowBack // Importa el ícono de flecha hacia atrás
import androidx.compose.material3.Button // Importa el componente de botón de Material 3
import androidx.compose.material3.ExperimentalMaterial3Api // Importa la anotación para usar API experimentales de Material 3
import androidx.compose.material3.Icon // Importa el componente de ícono de Material 3
import androidx.compose.material3.IconButton // Importa el componente de botón de ícono de Material 3
import androidx.compose.material3.OutlinedTextField // Importa el campo de texto con borde de Material 3
import androidx.compose.material3.Scaffold // Importa el componente Scaffold que proporciona una estructura básica
import androidx.compose.material3.Text // Importa el componente de texto de Material 3
import androidx.compose.material3.TopAppBar // Importa el componente de barra de aplicación superior de Material 3
import androidx.compose.runtime.Composable // Importa la anotación Composable para funciones que crean UI
import androidx.compose.ui.Alignment // Importa la clase para alinear elementos
import androidx.compose.ui.Modifier // Importa la clase para modificar la apariencia de los elementos
import androidx.compose.ui.text.input.KeyboardType // Importa la clase para tipos de teclado
import androidx.compose.ui.unit.dp // Importa la unidad de medida dp (density-independent pixels)
import androidx.lifecycle.viewmodel.compose.viewModel // Importa la función para obtener un ViewModel en un Composable
import androidx.compose.runtime.rememberCoroutineScope // Importa la función para recordar el scope de corutinas
import androidx.compose.ui.focus.onFocusChanged // Importa la función para manejar cambios de enfoque
import androidx.compose.ui.platform.LocalFocusManager // Importa la clase para manejar el enfoque local
import androidx.compose.ui.unit.dp
import com.luis.listacomprapersistente.ui.AppViewModelProvider
import com.luis.listacomprapersistente.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch // Importa la función para lanzar corutinas
import kotlin.text.isBlank

// Objeto que representa la pantalla de actualización del producto
object ProductUpdateDestination : NavigationDestination {
    override val route = "productUpdate" // Define la ruta para la pantalla de actualización del producto
    const val productNameArg = "productName" // Define el argumento para el nombre del producto
    val routeWithArgs = "$route/{$productNameArg}" // Define la ruta con argumentos
}

// Función Composable que representa la pantalla de actualización del producto
@OptIn(ExperimentalMaterial3Api::class) // Indica que se está utilizando una API experimental
@Composable
fun ProductUpdateScreen(
    navigateBack: () -> Unit, // Función para navegar hacia atrás
    modifier: Modifier = Modifier, // Modificador opcional para personalizar la apariencia
    viewModel: ProductUpdateViewModel = viewModel(factory = AppViewModelProvider.Factory) // Obtiene el ViewModel para la pantalla
) {
    val coroutineScope = rememberCoroutineScope() // Recuerda el scope de corutinas
    // Crea un Scaffold que proporciona una estructura básica para la pantalla
    Scaffold(
        topBar = {
            // Crea una barra de aplicación superior
            TopAppBar(
                title = {
                    Text("Detalles del producto") // Título de la barra de aplicación
                },
                navigationIcon = {
                    // Botón de navegación hacia atrás
                    IconButton(onClick = navigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        ) // Ícono de flecha hacia atrás
                    }
                }
            )
        }
    ) {
        // Crea una columna para organizar los elementos verticalmente
        Column(
            modifier = modifier
                .padding(it) // Aplica relleno basado en el padding del Scaffold
                .fillMaxSize() // Llena el tamaño máximo disponible
                .padding(16.dp), // Aplica un relleno adicional
            horizontalAlignment = Alignment.Start, // Alinea los elementos a la izquierda
            verticalArrangement = Arrangement.Top // Organiza los elementos en la parte superior
        ) {
            // Llama a la función para mostrar el formulario de actualización del producto
            UpdateProductForm(
                productDetailUiState = viewModel.productUpdateUiState, // Estado de la UI del detalle del producto
                onProductValueChanged = viewModel::updateUiState, // Actualiza el estado del producto
                onEdit = {
                    coroutineScope.launch { // Lanza una corutina para actualizar el producto
                        viewModel.updateProduct() // Actualiza el producto en el repositorio
                        navigateBack() // Navega hacia atrás después de la actualización
                    }
                },
                onCancel = navigateBack // Navega hacia atrás al cancelar
            )
        }
    }
}

// Función Composable que representa el formulario de actualización del producto
@Composable
fun UpdateProductForm(
    productDetailUiState: ProductUpdateUiState, // Estado de la UI de la actualización del producto
    onProductValueChanged: (ProductDetails) -> Unit, // Función para manejar cambios en los valores del producto
    onEdit: () -> Unit = {}, // Función para manejar la acción de editar
    onCancel: () -> Unit = {} // Función para manejar la acción de cancelar
) {
    //val focusManager = LocalFocusManager.current  Obtiene el manejador de enfoque local
    // Muestra el nombre del producto
    Text("Producto: ${productDetailUiState.productDetails.productName}")
    // Crea una fila para la cantidad del producto
    Row(
        modifier = Modifier.fillMaxWidth(), // Llena el ancho máximo disponible
        horizontalArrangement = Arrangement.SpaceBetween, // Espacia los elementos horizontalmente
        verticalAlignment = Alignment.CenterVertically // Alinea los elementos verticalmente al centro
    ) {//Row
        Spacer(
            modifier = Modifier.padding(
                horizontal = 32.dp,
                vertical = 0.dp
            )
        ) // Espacio entre elementos
        Text("Cantidad") // Etiqueta para la cantidad
        Spacer(
            modifier = Modifier.padding(
                horizontal = 32.dp,
                vertical = 0.dp
            )
        ) // Espacio entre elementos
        // Campo de texto para la cantidad del producto
        OutlinedTextField(
            value = productDetailUiState.productDetails.productQuantity, // Valor actual del campo
            onValueChange = {
                // Actualiza el estado del producto cuando cambia el valor
                onProductValueChanged(productDetailUiState.productDetails.copy(productQuantity = it))
            },
            modifier = Modifier
                .width(86.dp) // Establece el ancho del campo
                .onFocusChanged {
                    // Maneja el cambio de enfoque
                    if (it.hasFocus) {
                        // Si el campo tiene enfoque, limpia el valor
                        onProductValueChanged(
                            productDetailUiState.productDetails.copy(
                                productQuantity = ""
                            )
                        )
                    } else if (productDetailUiState.productDetails.productQuantity.isBlank()) {
                        // Si el campo pierde enfoque y está vacío, establece un valor por defecto
                        onProductValueChanged(
                            productDetailUiState.productDetails.copy(
                                productQuantity = "1"
                            )
                        )
                    }
                },
            maxLines = 1, // Permite una sola línea de texto
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // Establece el tipo de teclado a numérico
        )
    }//Row
    Spacer(Modifier.size(5.dp))
    // Crea una fila para el precio del producto
    Row(
        modifier = Modifier.fillMaxWidth(), // Llena el ancho máximo disponible
        horizontalArrangement = Arrangement.SpaceBetween, // Espacia los elementos horizontalmente
        verticalAlignment = Alignment.CenterVertically // Alinea los elementos verticalmente al centro
    ) {
        Spacer(modifier = Modifier.padding(32.dp, 0.dp)) // Espacio entre elementos
        Text("Precio") // Etiqueta para el precio
        Spacer(modifier = Modifier.padding(32.dp, 0.dp)) // Espacio entre elementos
        // Campo de texto para el precio del producto
        OutlinedTextField(
            value = productDetailUiState.productDetails.productPrice, // Valor actual del campo
            onValueChange = {
                // Actualiza el estado del producto cuando cambia el valor
                onProductValueChanged(productDetailUiState.productDetails.copy(productPrice = it))
            },
            modifier = Modifier
                .width(86.dp) // Establece el ancho del campo
                .onFocusChanged {
                    // Maneja el cambio de enfoque
                    if (it.hasFocus) {
                        // Si el campo tiene enfoque, limpia el valor
                        onProductValueChanged(productDetailUiState.productDetails.copy(productPrice = ""))
                    } else if (productDetailUiState.productDetails.productPrice.isBlank()) {
                        // Si el campo pierde enfoque y está vacío, establece un valor por defecto
                        onProductValueChanged(productDetailUiState.productDetails.copy(productPrice = "1.0"))
                    }
                },
            maxLines = 1, // Permite una sola línea de texto
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal), // Establece el tipo de teclado a decimal
        )
    }
    Spacer(modifier = Modifier.padding(0.dp, 16.dp)) // Espacio entre elementos
    // Crea una fila para los botones de acción
    Row(//Los botones
        modifier = Modifier.fillMaxWidth(), // Llena el ancho máximo disponible
        horizontalArrangement = Arrangement.Center, // Centra los elementos horizontalmente
        verticalAlignment = Alignment.CenterVertically // Alinea los elementos verticalmente al centro
    ) {
        // Botón para cancelar
        Button(
            onClick = onCancel, // Acción al hacer clic
        ) {
            Text("Cancelar") // Texto del botón
        }
        Spacer(modifier = Modifier.padding(32.dp, 0.dp)) // Espacio entre botones
        // Botón para editar
        Button(
            onClick = onEdit, // Acción al hacer clic
            enabled = productDetailUiState.isSaveButtonEnabled // Habilita o deshabilita el botón según el estado
        ) {
            Text("Editar") // Texto del botón
        }
    }
}