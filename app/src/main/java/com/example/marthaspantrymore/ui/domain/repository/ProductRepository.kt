package com.example.marthaspantrymore.ui.domain.repository

import com.example.marthaspantrymore.ui.domain.model.ProductData

interface ProductRepository {
    suspend fun getProductByName(name: String, type: String, onSucces: (List<ProductData>) -> Unit, onFailure: (Exception) -> Unit) : List<ProductData>
    suspend fun updateStockProduct(productId: String, type: String, quantity: Int, onSucces: () -> Unit, onFailure: (Exception) -> Unit)
    suspend fun getIdProduct(type: String, onSucces: () -> Unit, onFailure: (Exception) -> Unit)
}