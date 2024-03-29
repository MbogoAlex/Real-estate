package com.example.propertymanagement.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.propertymanagement.ui.nav.PManagerNavHost

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PManagerApp(
    navController: NavHostController = rememberNavController()
) {
    PManagerNavHost(navController = navController)
}