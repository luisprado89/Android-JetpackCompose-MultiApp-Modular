package com.luis.introcompose
/**
 * Created by resuadam2 on september 2024.
 * Tutorial de Compose en Android
 * Basado en la lista de tutoriales de Mouredev y en la documentación oficial de Android sobre Compose
 * Main topics:
 * - Column: Componente de diseño (layout), que permite organizar otros componentes de UI de manera
vertical (uno debajo del otro). Es como un contenedor para disponer elementos en una columna.

Column {
Text("Primer Elemento")
Text("Segundo Elemento")
}

 * - Row: Componente de diseño (layout), pero organiza los elementos en una disposición horizontal
(de izquierda a derecha). Es un contenedor que permite alinear sus elementos en una fila.

Row {
Text("Elemento 1")
Text("Elemento 2")
}
 * - Image: componente de Jetpack Compose que se usa para mostrar una imagen en la UI.

Image(painter = painterResource(id = R.drawable.mi_imagen), contentDescription = "Descripción de la imagen")

 * - setContent: método utilizado  en la función 'onCreate' de una actividad (Activity) o onCreateView
de un fragmento para establecer el contenido de la interfaz de usuario de tu aplicación en Jetpack Compose.
Este método se utiliza para configurar el árbol de composables que se va a renderizar.

setContent {
MyApp()  // Composable que define la UI
}

 * - @Composable: anotación. Se usa para marcar funciones que definen la interfaz de usuario en Compose,
es decir, funciones que pueden ser composables.
 * - @Preview: anotación que permite previsualizar composables en Android Studio sin tener que ejecutar la app.
 * - painterResource: método que carga una imagen desde los recursos del proyecto y la convierte en un Painter,
que luego se puede usar con el componente Image para mostrar la imagen.

val painter = painterResource(id = R.drawable.mi_imagen)
Image(painter = painter, contentDescription = "Imagen desde recursos")

 * - enableEdgeToEdge: método (o función) que se usa para habilitar el diseño de pantalla de borde a
borde, lo que permite que la UI ocupe todo el espacio disponible, incluso bajo las barras de sistema
(como la barra de estado y la de navegación).

enableEdgeToEdge(true)

Resumen:
Componentes: Column, Row, Image (elementos de la UI).-> layout <-
Métodos: setContent, painterResource, enableEdgeToEdge.
Anotaciones: @Composable, @Preview (declaraciones para las funciones).


@Composable
public fun Text(
text: String,
modifier: Modifier = Modifier,
color: Color = Color. Unspecified,
fontSize: TextUnit = TextUnit. Unspecified,
fontStyle: FontStyle? = null,
fontWeight: FontWeight? = null,
fontFamily: FontFamily? = null,
letterSpacing: TextUnit = TextUnit. Unspecified,
textDecoration: TextDecoration? = null,
textAlign: TextAlign? = null,
lineHeight: TextUnit = TextUnit. Unspecified,
overflow: TextOverflow = TextOverflow. Clip,
softWrap: Boolean = true,
maxLines: Int = Int. MAX_VALUE,
minLines: Int = 1,
onTextLayout: ((TextLayoutResult) -> Unit)? = null,
style: TextStyle = LocalTextStyle. current
): Unit
Elemento de alto nivel que muestra texto y proporciona información semántica/ accesibilidad.
El style predeterminado utiliza el LocalTextStyle proporcionado por MaterialTheme / componentes. Si está configurando su propio estilo, es posible que desee considerar primero recuperar LocalTextStyle y usar TextStyle. copy para mantener los atributos definidos del tema, modificando solo los atributos específicos que desea anular.
Para facilitar su uso, aquí también están presentes los parámetros de uso común de TextStyle . El orden de precedencia es el siguiente:
Si un parámetro se establece explícitamente aquí (es decir, no es null o TextUnit. Unspecified ), este parámetro siempre se utilizará.
Si no se establece un parámetro ( null o TextUnit. Unspecified ), en su lugar se utilizará el valor correspondiente del style .
Además, para color , si color no está configurado y style no tiene color, se utilizará LocalContentColor .
Parámetros:
text : el texto que se mostrará
modifier : el Modifier que se aplicará a este nodo de diseño
color : Color que se aplicará al texto. Si Color. Unspecified y style no tienen ningún color establecido, será LocalContentColor .
fontSize : el tamaño de los glifos que se utilizarán al pintar el texto. Consulte TextStyle. fontSize .
fontStyle : la variante tipográfica que se utilizará al dibujar las letras (por ejemplo, cursiva). Consulte TextStyle. fontStyle .
fontWeight : el grosor del tipo de letra que se utilizará al pintar el texto (por ejemplo, FontWeight. Bold ).
fontFamily : la familia de fuentes que se utilizará al representar el texto. Consulte TextStyle. fontFamily .
letterSpacing : la cantidad de espacio que se agregará entre cada letra. Consulte TextStyle. letterSpacing .
textDecoration : las decoraciones que se pintarán en el texto (por ejemplo, un subrayado). Consulte TextStyle. textDecoration .
textAlign : la alineación del texto dentro de las líneas del párrafo. Consulte TextStyle. textAlign .
lineHeight : altura de línea para el Paragraph en la unidad TextUnit , por ejemplo, SP o EM. Consulte TextStyle. lineHeight .
overflow : cómo se debe manejar el desbordamiento visual.
softWrap : si el texto debe dividirse en saltos de línea suaves. Si es falso, los glifos en el texto se colocarán como si hubiera un espacio horizontal ilimitado. Si softWrap es falso, overflow y TextAlign pueden tener efectos inesperados.
maxLines : un número máximo opcional de líneas para que el texto se extienda, ajustándose si es necesario. Si el texto excede el número de líneas dado, se truncará según overflow y softWrap . Se requiere que 1 <= minLines <= maxLines .
minLines : la altura mínima en términos de número mínimo de líneas visibles. Se requiere que 1 <= minLines <= maxLines .
onTextLayout : devolución de llamada que se ejecuta cuando se calcula un nuevo diseño de texto. Un objeto TextLayoutResult que proporciona la devolución de llamada contiene información de párrafo, tamaño del texto, líneas de base y otros detalles. La devolución de llamada se puede utilizar para agregar decoración o funcionalidad adicional al texto. Por ejemplo, para dibujar una selección alrededor del texto.
style : configuración de estilo para el texto, como color, fuente, altura de línea, etc.

 */
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.luis.introcompose.ui.theme.AndroidJetpackComposeMultiAppModularTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Habilitar el modo de pantalla completa
        setContent { // setContent para establecer el contenido de la actividad
            AndroidJetpackComposeMultiAppModularTheme {// TutorialComposeTheme para establecer el tema de la actividad
                    // Greeting para mostrar un saludo en la pantalla
                    ScreenContent("Android")
                }
            }
        }
    }

// @Composable para definir una función composable
// Si mostramos dos Text juntos, se superpondrán
@Composable
fun GreetingBad(name: String) {
    Text(text = "Hello $name!") // Text para mostrar un texto en la pantalla
    Text(text = "Goodbye $name!")
}

// @Composable para definir una función composable
@Composable
fun GreetingGood(name: String) {
    Column {
        Text(text = "Hello $name!") // Text para mostrar un texto en la pantalla
        Text(text = "Goodbye $name!")
    }
}

// @Composable para definir una función composable
@Composable
fun Greeting(name: String) {
    Column( // Column para mostrar los elementos de forma vertical
    ) {
        Text(text = "Hello $name!") // Text para mostrar un texto en la pantalla
        Text(text = "Goodbye $name!")
    }
}

// @Composable para definir una función composable
@Composable
fun ScreenContent(name: String) {
    Row ( // Row para mostrar los elementos de forma horizontal
    ){
        Image(
            contentDescription = "Android Logo", // contentDescription para describir la imagen
            // painterResource para cargar una imagen desde los recursos (se debe importar androidx.compose.ui.res.painterResource)
            painter = painterResource(id = R.drawable.ic_launcher_foreground) // ic_launcher_foreground para cargar el icono de la aplicación
        )
        Greeting(name) // Greeting para mostrar un saludo en la pantalla
    }
}


// @Preview para mostrar una vista previa de la actividad
@Preview(showBackground = true) // showBackground para mostrar el fondo de la vista previa
@Composable // @Composable para definir una función composable
fun DefaultPreview() {
    AndroidJetpackComposeMultiAppModularTheme { // TutorialComposeTheme para establecer el tema de la vista previa
        // Greeting para mostrar un saludo en la vista previa
        // GreetingBad("Android")
        // GreetingGood("Android")
        // Greeting("Android")
        ScreenContent("Android")
    }
}
