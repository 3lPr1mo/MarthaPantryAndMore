package com.example.marthaspantrymore.ui.domain

import com.example.marthaspantrymore.ui.domain.model.ProductData

data class MovementData(
    val product: ProductData,
    val type: String, //Se vendió o se compró
    val cost: Int //Cuanto se ganó en caso de vender o cuanto se gastó en caso de comprar
)
