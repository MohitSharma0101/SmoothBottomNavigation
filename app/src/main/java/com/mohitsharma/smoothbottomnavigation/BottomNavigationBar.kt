package com.mohitsharma.smoothbottomnavigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@ExperimentalAnimationApi
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Search,
        NavigationItem.Setting
    )
    BottomNavigation(
        backgroundColor = Color(0xFF432FBF),
        contentColor = Color.White,

        ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination
        items.forEach { item ->
            var isSelected by remember { mutableStateOf(false) }
            isSelected = currentRoute?.hierarchy?.any { it.route == item.route } == true

            val transition = updateTransition(isSelected, label = "isSelected")

            var modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            if (isSelected) {
                modifier = Modifier
                    .background(
                        color = Color(0xFF5E4ECB),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            }

            BottomNavigationItem(
                selectedContentColor = Color(0xFF432FBF),
                interactionSource = remember { MutableInteractionSource() },
                icon = {
                       BottomBarIcon(modifier = modifier, item = item, transition = transition)
                },
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun BottomBarIcon(modifier: Modifier , item: NavigationItem , transition: Transition<Boolean>){
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            item.icon,
            contentDescription = item.title,
            tint = Color.White
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        transition.AnimatedVisibility(
            visible = { it },
            enter = expandHorizontally(),
            exit = shrinkHorizontally()
        ) {
            Text(
                text = item.title,
                color = Color.White
            )
        }
    }
}
