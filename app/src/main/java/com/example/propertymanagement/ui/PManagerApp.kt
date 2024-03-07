package com.example.propertymanagement.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.propertymanagement.ui.nav.PManagerNavHost

@Composable
fun PManagerApp(
    navController: NavHostController = rememberNavController()
) {
    PManagerNavHost(navController = navController)
}