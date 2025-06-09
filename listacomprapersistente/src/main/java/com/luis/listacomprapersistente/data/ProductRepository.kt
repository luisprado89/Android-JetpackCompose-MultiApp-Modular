package com.luis.listacomprapersistente.data

import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getAllProductsStream(): Flow<List<Product>>
    fun getProductStream(name: String): Flow<Product?>
    suspend fun insertProduct(product: Product)
    suspend fun updateProduct(product: Product)
    suspend fun deleteProduct(product: Product)
}
class ProductRepositoryImpl(private val productDao: ProductDao): ProductRepository{
    override fun getAllProductsStream(): Flow<List<Product>> {
        return productDao.getAllProducts()
    }
    override fun getProductStream(name: String): Flow<Product?> {
        return productDao.getProductByName(name)
    }
    override suspend fun insertProduct(product: Product) {
        return productDao.insertProduct(product)
    }
    override suspend fun updateProduct(product: Product) {
        return productDao.updateProduct(product)
    }
    override suspend fun deleteProduct(product: Product) {
        return productDao.deleteProduct(product)
    }
}