package com.example.propertymanagement.model

import androidx.compose.ui.graphics.painter.Painter

data class NavigationContent(
    val title: String,
    val icon: Painter,
    val currentTab: BottomTab,
)
