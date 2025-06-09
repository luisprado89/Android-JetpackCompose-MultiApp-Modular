package com.luis.discosfavoritos2.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DiscoDao {
    @Query("SELECT * FROM disco")
    fun getAll(): Flow<List<Disco>>

    @Query("SELECT * FROM disco WHERE id = :id")
    fun getDisco(id: Int): Flow<Disco>

    @Insert
    suspend fun insert(disco: Disco)

    @Update
    suspend fun update(disco: Disco)

    @Delete
    suspend fun delete(disco: Disco)


    // 🔍 Función añadida para verificar duplicados
    @Query("""
        SELECT * FROM disco 
        WHERE titulo = :titulo 
          AND autor = :autor 
          AND numCanciones = :numCanciones 
          AND publicacion = :publicacion 
          AND valoracion = :valoracion
        LIMIT 1
    """)
    suspend fun buscarDiscoDuplicado(
        titulo: String,
        autor: String,
        numCanciones: Int,
        publicacion: Int,
        valoracion: Int
    ): Disco?
}