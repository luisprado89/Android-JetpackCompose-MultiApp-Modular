package com.luis.listacompraexamenbd.data

import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getAllProductsStream(): Flow<List<Product>>
    suspend fun getProductByName(name: String): Product?
    suspend fun insertProduct(product: Product)
    suspend fun insertProducts(products: List<Product>)
    suspend fun updateProduct(product: Product)
    suspend fun deleteProduct(product: Product)
    suspend fun deleteAll()
}
class ProductRepositoryImpl(
    private val productDao: ProductDao
) : ProductRepository {
    override fun getAllProductsStream(): Flow<List<Product>> = productDao.getAllProducts()
    override suspend fun getProductByName(name: String): Product? = productDao.getProductByName(name)
    override suspend fun insertProduct(product: Product) = productDao.insertProduct(product)
    override suspend fun insertProducts(products: List<Product>) = productDao.insertProducts(products)
    override suspend fun updateProduct(product: Product) = productDao.updateProduct(product)
    override suspend fun deleteProduct(product: Product) = productDao.deleteProduct(product)
    override suspend fun deleteAll() = productDao.deleteAll()
}