package com.luis.discosfavoritos2.data

import kotlinx.coroutines.flow.Flow

interface DiscoRepository {
    fun getAll(): Flow<List<Disco>>
    fun getDisco(id: Int): Flow<Disco>
    suspend fun insert(disco: Disco)
    suspend fun update(disco: Disco)
    suspend fun delete(disco: Disco)
}

class DiscoRepositoryImpl(private val discoDao: DiscoDao) : DiscoRepository {
    override fun getAll(): Flow<List<Disco>> = discoDao.getAll()
    override fun getDisco(id: Int): Flow<Disco> = discoDao.getDisco(id)
    override suspend fun insert(disco: Disco) = discoDao.insert(disco)
    override suspend fun update(disco: Disco) = discoDao.update(disco)
    override suspend fun delete(disco: Disco) = discoDao.delete(disco)
}