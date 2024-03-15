package com.example.propertymanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.propertymanagement.ui.PManagerApp
import com.example.propertymanagement.ui.theme.PropertyManagementTheme
import com.example.propertymanagement.ui.views.AvailabilitySelection
import com.example.propertymanagement.ui.views.CreateNewPropertyScreen
import com.example.propertymanagement.ui.views.PropertyScreen
import com.example.propertymanagement.ui.views.UnitsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            PropertyManagementTheme {
                // A surface container using the 'background' color from the theme
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    PManagerApp()
//                    CreateNewPropertyScreen()
                }
            }
        }
    }
}
