package com.luis.triviaapproom.data  // Define el paquete en el que se encuentra este archivo.

import android.content.Context  // Importa la clase Context de Android para obtener acceso a los recursos del sistema.
import androidx.room.Database  // Importa la anotación Database de Room para declarar la base de datos.
import androidx.room.Room  // Importa la clase Room que facilita la creación de bases de datos en Android.
import androidx.room.RoomDatabase  // Importa la clase base que se debe extender para crear la base de datos usando Room.


// Define la base de datos usando la anotación @Database, especificando las entidades y la versión de la base de datos.
@Database(entities = [Game::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Método abstracto que debe ser implementado para obtener acceso al DAO (Data Access Object) de la entidad 'Game'.
    abstract fun gameDao(): GameDao
    // Un objeto compañero (companion object) es usado para almacenar variables estáticas dentro de la clase.
    companion object {
        // Nombre de la base de datos.
        const val DATABASE_NAME = "trivia_app_database"
        // Volatile asegura que los cambios en esta variable sean visibles inmediatamente en otros hilos.
        @Volatile
        private var INSTANCE: AppDatabase? = null  // Variable para almacenar la instancia de la base de datos. la primera ves el null ya que no existe DB y debe crearlo
        // Método para obtener la instancia de la base de datos.
        // Si la instancia no existe, la crea de manera sincronizada.
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {  // Si INSTANCE es null, sincroniza el bloque para crear la instancia.
                val instance = Room.databaseBuilder(
                    context.applicationContext,  // Usa el contexto de la aplicación para evitar fugas de memoria.
                    AppDatabase::class.java,  // Especifica la clase de la base de datos.
                    DATABASE_NAME  // Nombre de la base de datos.
                ).build()  // Crea la base de datos.

                // Una vez creada la instancia, se asigna a la variable INSTANCE para reutilizarla en futuras llamadas.
                INSTANCE = instance
                instance  // Devuelve la instancia de la base de datos.
            }
        }
    }
}
