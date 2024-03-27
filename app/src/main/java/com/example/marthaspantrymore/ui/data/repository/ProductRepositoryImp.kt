package com.example.marthaspantrymore.ui.data.repository

import com.example.marthaspantrymore.ui.domain.model.ProductData
import com.example.marthaspantrymore.ui.domain.repository.ProductRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class ProductRepositoryImp : ProductRepository {
    private val db = Firebase.firestore
    override suspend fun getProductByName(
        name: String,
        type: String,
        onSucces: (List<ProductData>) -> Unit,
        onFailure: (Exception) -> Unit
    ) : List<ProductData> {
        var auxId: String = ""
        val productsList = mutableListOf<ProductData>()
        //Obtener ID
        db.collection("products")
            .document(type)
            .collection("lista")
            .get()
            .addOnSuccessListener { documents ->
                for(document in documents){
                    auxId = document.id
                }
            }
        //Obtener datos de productos

        db.collection("productos")
            .document(type)
            .collection("lista")
            .whereEqualTo("name", name)
            .get()
            .addOnSuccessListener { documents ->

                for (document in documents) {
                    val product = document.toObject(ProductData::class.java)
                    product.id = auxId
                    productsList.add(product)
                }
                onSucces(productsList)
            }
            .addOnFailureListener{
                onFailure(it)
            }

        return productsList
    }

    override suspend fun updateStockProduct(
        productId: String,
        type: String,
        quantity: Int,
        onSucces: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("products")
            .document(type)
            .collection("lsita")
            .document(productId)
            .update("stock", quantity)
            .addOnSuccessListener {
                onSucces()
            }
            .addOnFailureListener{
                onFailure(it)
            }
    }

    override suspend fun getIdProduct(
        type: String,
        onSucces: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        TODO("Not yet implemented")
    }

}