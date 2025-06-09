package com.luis.listacompraexamenbd.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product (
    @PrimaryKey
    val name: String,
    val price: Float,
    val quantity: Int
)