package com.example.propertymanagement.model

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationContent(
    val title: String,
    val icon: Painter,
    val currentTab: Tab,
)
