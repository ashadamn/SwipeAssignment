package com.example.swipeassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.swipeassignment.presentation.navigation.AppNavigation
import com.example.swipeassignment.presentation.navigation.MainNavHost
import com.example.swipeassignment.presentation.theme.SwipeAssignmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val startDestination = AppNavigation.Home.route
            SwipeAssignmentTheme {
                MainNavHost(startDestination = startDestination)
            }
        }
    }
}