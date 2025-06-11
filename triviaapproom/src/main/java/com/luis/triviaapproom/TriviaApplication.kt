package com.luis.triviaapproom

import android.app.Application  // Importa la clase Application de Android, que es la clase base de la aplicación.
import android.content.Context  // Importa la clase Context, que se usa para acceder a recursos y sistemas de servicios.
import androidx.datastore.core.DataStore  // Importa la clase DataStore, que permite almacenar datos de manera asíncrona.
import androidx.datastore.preferences.core.Preferences  // Importa la clase Preferences, que contiene las claves y valores almacenados.
import androidx.datastore.preferences.preferencesDataStore  // Importa la extensión preferencesDataStore para crear y gestionar el DataStore.

import com.luis.triviaapproom.data.AppContainer
import com.luis.triviaapproom.data.DefaultAppcontainer
import com.luis.triviaapproom.data.TriviaPreferencesRepository

// (justo debajo del package)
private const val DATASTORE_NAME = "trivia_prefs"

// esto crea la propiedad `dataStore` en cualquier Context, usando el delegate de DataStore
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = DATASTORE_NAME
)


/**
 * Application class for the Trivia app
 *
 * Initializes the AppContainer
 *
 * Esta clase es la encargada de inicializar el contenedor de dependencias
 *
 * @see com.luisdpc.triviaapp_master.data.AppContainer
 *
 * OJO: No olvides declarar esta clase en el AndroidManifest.xml para ello agrega la siguiente linea:
 *
 * <application
 *    android:name=".TriviaApplication"
 *    ...
 *    >
 *    ...
 *    </application>
 *
 *    Esta linea debe ir dentro del tag <application> en el archivo AndroidManifest.xml
 */

class TriviaApplication : Application() {  // Clase principal de la aplicación que extiende de Application.
    lateinit var container: AppContainer  // Declara el contenedor de dependencias.
    lateinit var triviaPreferencesRepository: TriviaPreferencesRepository  // Declara el repositorio de preferencias.

    override fun onCreate() {  // Sobreescribe el método onCreate() de Application. Este método se llama cuando la aplicación se inicia.
        super.onCreate()  // Llama al método onCreate() de la clase base (Application).
        triviaPreferencesRepository = TriviaPreferencesRepository(this.dataStore)  // Inicializa el repositorio de preferencias con el DataStore.
        container = DefaultAppcontainer(this)  // Inicializa el contenedor de dependencias usando DefaultAppcontainer.
    }
}