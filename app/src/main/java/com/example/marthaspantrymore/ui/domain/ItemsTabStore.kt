package com.example.marthaspantrymore.ui.domain

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Sell
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.marthaspantrymore.ui.presentation.screens.BuyScreen
import com.example.marthaspantrymore.ui.presentation.screens.SellScreen

sealed class ItemsTabStore(
    var title: String,
    var iconSelected: ImageVector,
    var iconUnselected: ImageVector,
    var screen: @Composable () -> Unit
){
    object sellTab: ItemsTabStore(
        "Ventas",
        iconSelected = Icons.Filled.Sell,
        iconUnselected = Icons.Outlined.Sell,
        screen = { SellScreen() }
    )

    object buyTab: ItemsTabStore(
        "Compras",
        iconSelected = Icons.Filled.ShoppingCart,
        iconUnselected = Icons.Outlined.ShoppingCart,
        screen = { BuyScreen() }
    )
}
