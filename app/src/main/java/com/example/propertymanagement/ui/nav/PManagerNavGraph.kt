package com.example.propertymanagement.ui.nav

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.propertymanagement.ui.views.HomeDestination
import com.example.propertymanagement.ui.views.LoginScreen
import com.example.propertymanagement.ui.views.LoginScreenDestination
import com.example.propertymanagement.ui.views.MyUnitScreen
import com.example.propertymanagement.ui.views.PropertyScreen
import com.example.propertymanagement.ui.views.RegistrationScreen
import com.example.propertymanagement.ui.views.RegistrationScreenDestination
import com.example.propertymanagement.ui.views.UnitDetails
import com.example.propertymanagement.ui.views.UnitDetailsScreenDestination

@Composable
fun PManagerNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            PropertyScreen(
                navigateToUnit = { navController.navigate(
                    "${UnitDetailsScreenDestination.route}/${it}"
                ) },
                onBackButtonPressed = { navController.navigateUp() },
                navigateToRegistrationPage = {
                    navController.navigate(RegistrationScreenDestination.route)
                }
            )
        }
        composable(route = RegistrationScreenDestination.route) {
            RegistrationScreen(
                onBackButtonPressed = {navController.navigateUp()},
                proceedToLogin = { phoneNumber, password ->
                    Log.i("LOGGING_IN","${LoginScreenDestination.route}/${phoneNumber}/${password}" )
                    navController.navigate(
                        "${LoginScreenDestination.route}/${phoneNumber}/${password}"
                    )
                }
            )
        }
        composable(
            route = LoginScreenDestination.routeWithArgs,
            arguments = listOf(
                navArgument(LoginScreenDestination.phoneNumber) {
                    type = NavType.StringType
                },
                navArgument(LoginScreenDestination.password) {
                    type = NavType.StringType
                }
            )
        ) {
            LoginScreen(
                proceedToHomeScreen = {
                    navController.navigate(HomeDestination.route)
                }
            )
        }
        composable(
            route = UnitDetailsScreenDestination.routeWithArgs,
            arguments = listOf(
                navArgument(UnitDetailsScreenDestination.unitId) {
                    type = NavType.StringType
                }
            )
        ) {
            UnitDetails(onBackButtonPressed = { navController.navigateUp() })
        }
    }
}