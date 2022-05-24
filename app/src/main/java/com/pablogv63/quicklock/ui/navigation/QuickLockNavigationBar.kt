package com.pablogv63.quicklock.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import com.pablogv63.quicklock.ui.destinations.*
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun QuickLockNavigationBar(
    current: String,
    navigator: DestinationsNavigator,
    onSameClick: () -> Unit = {}
) {
    val items = listOf("Credentials", "Generator", "Settings")
    val destinations = listOf(
        { navigator.navigate(CredentialsScreenDestination) },
        { navigator.navigate(GeneratorScreenDestination()) },
        { navigator.navigate(SettingsScreenDestination) }
    )
    var selectedItem = items.indexOfFirst { current.contains(it) }
    val filledIcons = listOf(
        Icons.Filled.Lock, Icons.Filled.Sync,
        Icons.Filled.Settings
    )
    val outlinedIcons = listOf(
        Icons.Outlined.Lock, Icons.Outlined.Sync,
        Icons.Outlined.Settings
    )

    NavigationBar {
        items.forEachIndexed { index, item ->
            val selected = selectedItem == index
            val label: @Composable() ()->Unit = { Text(item) }
            NavigationBarItem(
                icon = {
                    Icon(
                        if (selected) filledIcons[index] else outlinedIcons[index],
                        contentDescription = null
                    )
                },
                label = if (selected) label else null,
                selected = selected,
                onClick =
                if (!selected) {
                    {
                        selectedItem = index
                        destinations[index]()
                    }
                } else onSameClick
            )
        }
    }
}