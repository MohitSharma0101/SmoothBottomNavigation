package com.mohitsharma.smoothbottomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector


sealed class NavigationItem(
    var route: String, var icon:
    ImageVector, var title: String
) {
    object Home : NavigationItem("home", Icons.Rounded.Home, "Home")
    object Search : NavigationItem("search", Icons.Rounded.Search, "Search")
    object Setting : NavigationItem("setting", Icons.Rounded.Settings, "Setting")
}