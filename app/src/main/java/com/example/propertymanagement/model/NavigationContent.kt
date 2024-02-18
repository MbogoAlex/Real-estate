package com.example.propertymanagement.model

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationContent(
    val title: String,
    val icon: ImageVector,
    val currentTab: Tab,
)
