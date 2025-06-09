package com.luis.listacompraexamenbd.data

import android.content.Context

class AppContainer(context: Context) {
    private val appDatabase = AppDatabase.getDatabase(context)
    private val productDao = appDatabase.productDao()
    private val productRepository = ProductRepositoryImpl(productDao)

    fun provideProductRepository(): ProductRepository {
        return productRepository
    }
}
