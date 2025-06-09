package com.luis.discosfavoritos2.data

import kotlinx.coroutines.flow.Flow

interface DiscoRepository {
    fun getAll(): Flow<List<Disco>>
    fun getDisco(id: Int): Flow<Disco>
    suspend fun insert(disco: Disco)
    suspend fun update(disco: Disco)
    suspend fun delete(disco: Disco)

    // 🔍 Nueva función para verificar si ya existe un disco igual
    suspend fun discoYaExiste(disco: Disco): Boolean
}

class DiscoRepositoryImpl(private val discoDao: DiscoDao) : DiscoRepository {
    override fun getAll(): Flow<List<Disco>> = discoDao.getAll()
    override fun getDisco(id: Int): Flow<Disco> = discoDao.getDisco(id)
    override suspend fun insert(disco: Disco) = discoDao.insert(disco)
    override suspend fun update(disco: Disco) = discoDao.update(disco)
    override suspend fun delete(disco: Disco) = discoDao.delete(disco)

    //Este es para comprobar si el disco existe
    override suspend fun discoYaExiste(disco: Disco): Boolean {
        return discoDao.buscarDiscoDuplicado(
            titulo = disco.titulo,
            autor = disco.autor,
            numCanciones = disco.numCanciones,
            publicacion = disco.publicacion,
            valoracion = disco.valoracion
        ) != null
    }
}