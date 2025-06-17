# Tutorial básico de Jetpack Compose

Cada módulo del proyecto contiene una aplicación de ejemplo sobre un tema específico de Jetpack Compose. Se puede ejecutar cada módulo de forma independiente, ya que cada uno de ellos contiene su propia `MainActivity.kt`, configurada como lanzador de la aplicación.

## Estructura de módulos

1. **IntroCompose**: Introducción a Jetpack Compose.
2. **Modificadores**: Modificadores en Jetpack Compose.
3. **Listas**: Listas y scroll en Jetpack Compose.
4. **State**: Estado en Jetpack Compose.
5. **Navigation**: Navegación en Jetpack Compose.
6. **Splash**: Pantalla de bienvenida (Splash Screen).

---

## 1. IntroCompose: Introducción a Jetpack Compose

En este módulo se exploran los conceptos básicos:

- **Column**: Componente de diseño que organiza sus hijos en una columna vertical, apilándolos de arriba hacia abajo. Útil para mostrar múltiples elementos verticalmente (textos, imágenes, botones).

- **Row**: Similar a `Column`, pero organiza los elementos en una fila horizontal, alineándolos de izquierda a derecha. Ideal para elementos en línea.

- **Image**: Composable para mostrar imágenes. Se puede cargar desde recursos locales (`drawable`) usando `painterResource` o desde una URL.

- **setContent**: Función de `ComponentActivity` que define el contenido visual de la actividad en Compose, por ejemplo `setContent { MiComposable() }`.

- **@Composable**: Anotación que marca una función como composable, generando UI en Compose.

- **@Preview**: Permite previsualizar un composable en Android Studio sin ejecutar la app en un dispositivo.

- **painterResource**: Carga imágenes desde `res/drawable` devolviendo un `Painter` para `Image`.

- **enableEdgeToEdge**: Habilita que la UI ocupe toda la pantalla, ignorando barras de sistema (estado, navegación) para pantalla completa.

## 2. Modificadores: Modificadores en Jetpack Compose

Este módulo trata sobre cómo modificar comportamiento y apariencia:

- **Modifiers**: Objetos que modifican tamaño, color, padding, alineación, etc., aplicados con `.modifier`.

- **CustomText**: Composable personalizado para mostrar texto con estilos específicos (color, tamaño, peso).

- **Material Design**: Conjunto de principios de diseño de Google, usados en componentes como `Button`, `TextField`, `Card`.

- **Themes (Temas)**: Definición global de colores, tipografía y formas mediante `MaterialTheme`.

- **ColorScheme**: Esquema de colores (primario, secundario, fondo, texto) para consistencia.

- **Typography**: Conjunto de estilos de texto (familias, tamaños, grosores, interlineado).

- **Padding**: Modificador para espacio alrededor de un composable: `padding()`.

- **Background**: Modificador para establecer un fondo (color, degradado o imagen).

- **Clip**: Recorta el contenido a una forma (círculo, rectángulo redondeado).

- **Size**: Establece dimensiones con `size()`, `fillMaxWidth()`, `fillMaxSize()`.

- **Spacer**: Composable que aporta espacio vacío entre elementos.

- **UI_MODE_NIGHT_YES**: Constante para detectar modo oscuro y adaptar la UI.

## 3. Listas: Listas y scroll en Jetpack Compose

- **Scroll**: Capacidad de desplazar contenido con `Column + ScrollState`, `LazyColumn`, etc.

- **Listas**: Estructuras visuales para mostrar colecciones de elementos.

- **LazyColumn**: Lista eficiente que compone sólo elementos visibles, mejorando rendimiento.

- **rememberScrollState**: Guarda estado de desplazamiento para conservar posición entre recomposiciones.

## 4. State: Estado en Jetpack Compose

- **State (Estado)**: Información mutable que afecta la UI y provoca recomposiciones.

- **remember**: Guarda valores entre recomposiciones para evitar recálculos innecesarios.

- **mutableStateOf**: Crea estado mutable que desencadena recomposición al cambiar.

## 5. Navigation: Navegación en Jetpack Compose

- **Navigation**: Biblioteca para gestionar flujos de pantallas, rutas y parámetros.

- **Scaffold**: Contenedor con estructuras UI comunes (topBar, bottomBar, drawer).

- **topBar**: Parámetro de `Scaffold` para `TopAppBar` con título y acciones.

- **modifier**: Permite agregar padding, tamaño, alineación, etc. a composables.

- **fillMaxSize**: Ocupa todo el espacio disponible del contenedor.

- **verticalArrangement** / **horizontalAlignment**: Controlan distribución de elementos en `Column` o `Row`.

- **clickable**: Hace interactuable un composable respondiendo toques.

- **popBackStack**: Elimina la pantalla actual de la pila de navegación.

- **arguments**: Parámetros pasados entre pantallas de navegación.

- **arrowBack**: Icono o acción para volver a la pantalla anterior.

- **navHostController**: Controlador que gestiona la navegación y la pila.

- **navHost**: Composable donde se definen rutas y destinos de navegación.

## 6. Splash: Pantalla de Bienvenida

- **Splash Screen**: Pantalla inicial que aparece al iniciar la app y redirige después.

- **Navigation**: Tras la splash, dirige a la pantalla principal de la app.

- **LaunchedEffect**: Ejecuta efectos secundarios al componer, útil para delays o animaciones.

- **Composable functions**: Funciones que generan la UI en Compose.

- **Coroutines**: Tareas asíncronas que no bloquean el hilo principal.

- **Delay**: Pausa la ejecución de una coroutine para mantener la splash por un tiempo.
