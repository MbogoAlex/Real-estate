package com.example.propertymanagement.ui.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CreatePropertyPreviewScreen(
    modifier: Modifier = Modifier
) {
    val generalPropertyDetails = GeneralPropertyDetails(
        type = "Arbnb",
        rooms = "2",
    )
}