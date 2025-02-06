package com.example.swipeassignment.presentation.navigation

sealed class AppNavigation(val route: String) {

    data object Home : AppNavigation("home_screen")

}