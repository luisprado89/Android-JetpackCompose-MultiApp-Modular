package com.luis.triviaapproom.data  // Define el paquete donde se encuentra este archivo.

import android.util.Log  // Importa la clase Log para realizar registros en el log de Android.
import androidx.datastore.core.DataStore  // Importa la clase DataStore, que permite guardar datos de preferencias.
import androidx.datastore.core.IOException  // Importa la clase IOException para manejar errores al leer desde DataStore.
import androidx.datastore.preferences.core.Preferences  // Importa la clase Preferences, que contiene las claves y valores almacenados.
import androidx.datastore.preferences.core.edit  // Importa la extensión 'edit' para modificar las preferencias en DataStore.
import androidx.datastore.preferences.core.emptyPreferences  // Importa la función emptyPreferences para manejar errores de lectura.
import androidx.datastore.preferences.core.intPreferencesKey  // Importa la función intPreferencesKey para crear claves de enteros en DataStore.
import kotlinx.coroutines.flow.Flow  // Importa la clase Flow, que es utilizada para trabajar con flujos de datos asíncronos.
import kotlinx.coroutines.flow.catch  // Importa la extensión 'catch' para manejar errores en un flujo.
import kotlinx.coroutines.flow.map  // Importa la extensión 'map' para transformar los elementos de un flujo.

class TriviaPreferencesRepository(
    private val dataStore: DataStore<Preferences>  // Recibe una instancia de DataStore para almacenar y recuperar las preferencias.
) {
    // Definimos las claves y un TAG para registrar en el log.
    private companion object {
        const val RECORD_KEY = "record"  // La clave para almacenar el valor del récord.
        val record_key = intPreferencesKey(RECORD_KEY)  // La clave en sí, utilizando la función intPreferencesKey para claves de enteros.
        const val TAG = "TriviaPreferencesRepository"  // Etiqueta para usar en los logs.
    }

    // 'recordFlow' es un flujo de datos que nos permitirá observar el valor del récord.
    val recordFlow: Flow<Int> = dataStore.data  // 'data' es una propiedad que proporciona el flujo de preferencias del DataStore.
        .catch {  // 'catch' se usa para manejar excepciones que puedan ocurrir durante la lectura del flujo.
            if(it is IOException) {  // Si el error es una excepción de tipo IOException (por ejemplo, problemas de lectura).
                Log.e(TAG, "Error reading preferences", it)  // Registra el error en el log.
                emit(emptyPreferences())  // Si hay un error, emite un flujo vacío.
            } else {
                throw it  // Si el error no es una IOException, lo vuelve a lanzar.
            }
        }
        .map { preferences ->  // 'map' transforma el flujo de preferencias.
            preferences[record_key] ?: 0  // Devuelve el valor de 'record_key', si no existe, devuelve 0 por defecto.
        }

    // Función suspendida para escribir el récord en el DataStore.
    suspend fun writeTriviaPreferences(record: Int) {
        dataStore.edit { preferences ->  // 'edit' permite modificar las preferencias en el DataStore.
            preferences[record_key] = record  // Guarda el valor del récord usando 'record_key'.
        }
    }
}
