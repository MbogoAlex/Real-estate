package com.example.propertymanagement.ui.nav

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.propertymanagement.ui.views.CreateNewPropertyScreen
import com.example.propertymanagement.ui.views.CreateNewPropertyScreenDestination
import com.example.propertymanagement.ui.views.HomeDestination
import com.example.propertymanagement.ui.views.LoginScreen
import com.example.propertymanagement.ui.views.LoginScreenDestination
import com.example.propertymanagement.ui.views.MyUnitScreen
import com.example.propertymanagement.ui.views.PropertyScreen
import com.example.propertymanagement.ui.views.RegistrationScreen
import com.example.propertymanagement.ui.views.RegistrationScreenDestination
import com.example.propertymanagement.ui.views.UnitDetailsScreen
import com.example.propertymanagement.ui.views.UnitDetailsScreenDestination
import com.example.propertymanagement.ui.views.advertisenment.AdvertDetailsScreen
import com.example.propertymanagement.ui.views.advertisenment.UserAdvertDetailsScreenDestination
import com.example.propertymanagement.ui.views.advertisenment.UserAdvertsScreen
import com.example.propertymanagement.ui.views.advertisenment.UserAdvertsScreenDestination
import com.example.propertymanagement.ui.views.propertyUpdate.PropertyUpdateScreen
import com.example.propertymanagement.ui.views.propertyUpdate.PropertyUpdateScreenDestination

@RequiresApi(Build.VERSION_CODES.O)
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
                navigateToAdvertDetails = {
                                          navController.navigate(
                                              "${UserAdvertDetailsScreenDestination.route}/${it}"
                                          )
                },
                onBackButtonPressed = { navController.navigateUp() },
                navigateToRegistrationPage = {
                    navController.navigate(RegistrationScreenDestination.route)
                },
                proceedToLogin = { phoneNumber, password ->
                                 navController.navigate(
                                     "${LoginScreenDestination.route}/${phoneNumber}/${password}"
                                 )
                },
                onLoadHomeScreen = {
                    navController.navigate(HomeDestination.route)
                },
                navigateToCreatePropertyScreen = {
                    navController.navigate(CreateNewPropertyScreenDestination.route)
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
            UnitDetailsScreen(onBackButtonPressed = { navController.navigateUp() })
        }
        composable(
            route = UserAdvertsScreenDestination.route
        ) {
            UserAdvertsScreen(
                navigateToCreatePropertyScreen = {
                    navController.navigate(CreateNewPropertyScreenDestination.route)
                },
                navigateToAdvertDetails = {
                    navController.navigate("${UserAdvertDetailsScreenDestination.route}/${it}")
                }
            )
        }
        composable(
            route = CreateNewPropertyScreenDestination.route
        ) {
            CreateNewPropertyScreen(
                navigateToUserAdvertisedProperties = {
                                                     navController.navigate(UserAdvertsScreenDestination.route)
                },
                onBackButtonClicked = { navController.navigateUp() }
            )
        }
        composable(
            route = UserAdvertDetailsScreenDestination.routeWithArgs,
                    arguments = listOf(
                    navArgument(UserAdvertDetailsScreenDestination.unitId) {
                        type = NavType.StringType
                    }
                    )
        ) {
            AdvertDetailsScreen(
                navigateToEditProperty = {
                    navController.navigate("${PropertyUpdateScreenDestination.route}/${it}")
                }
            )
        }
        composable(
            route = PropertyUpdateScreenDestination.routeWithArgs,
            arguments = listOf(
                navArgument(PropertyUpdateScreenDestination.propertyId) {
                    type = NavType.StringType
                }
            )
        ) {
            PropertyUpdateScreen(
                navigateToUserAdvertisedProperties = {
                    navController.navigate(UserAdvertsScreenDestination.route)
                }
            )
        }
    }
}