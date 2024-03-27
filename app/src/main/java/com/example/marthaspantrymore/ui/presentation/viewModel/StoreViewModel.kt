package com.example.marthaspantrymore.ui.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marthaspantrymore.ui.domain.model.ProductData
import com.example.marthaspantrymore.ui.domain.repository.ProductRepository

class StoreViewModel(
    private val repository: ProductRepository
) : ViewModel() {
    private val _products = MutableLiveData<ProductData>()
    val products: LiveData<ProductData> = _products

    suspend fun getProductsByName(name: String, type: String) : List<ProductData> {
        return repository.getProductByName(
            name,
            type,
            onSucces = { products ->
                //
            },
            onFailure = { exception ->

            }
        )
    }

    suspend fun updateStockproduct(type: String) {

    }
}