package com.example.marthaspantrymore.ui.presentation.navigation

sealed class AppScreens(val route: String) {
    data object StoreScreen: AppScreens("store_screen")
    data object InventoryScreen: AppScreens("inventory_screen")
    data object profileScreen: AppScreens("profile_screen")
    data object notificationScreen: AppScreens("notification_screen")
}