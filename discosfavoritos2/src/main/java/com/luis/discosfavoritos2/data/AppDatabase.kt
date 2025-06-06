package com.luis.discosfavoritos2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Disco::class], version =1)
abstract class AppDatabase : RoomDatabase()
{
    abstract fun discoDao(): DiscoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(appContext: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    appContext,
                    AppDatabase::class.java,
                    "disco_database"
                ).build().also{
                    INSTANCE = it
                }
                instance
            }
        }
    }
}