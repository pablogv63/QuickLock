package com.pablogv63.quicklock.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pablogv63.quicklock.R
import com.pablogv63.quicklock.ui.NavGraphs
import com.pablogv63.quicklock.ui.appCurrentDestinationAsState
import com.pablogv63.quicklock.ui.destinations.*
import com.pablogv63.quicklock.ui.startAppDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.navigateTo
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

@ExperimentalMaterial3Api
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun QuickLockNavigationBar(
    navigator: DestinationsNavigator,
    currentDestination: NavBarDestination? = null,
    onSameClick: () -> Unit = {}
) {
    NavigationBar {
        NavBarDestination.values().forEach { destination ->
            val selected = currentDestination == destination
            NavigationBarItem(
                selected = selected,
                onClick = {
                    onSameClick()
                    // This line makes the direction to navigate the only one in the current graph
                    // This means that back button will close the app
                    navigator.popBackStack()
                    navigator.navigate(destination.direction) {
                        launchSingleTop = true
                    }
                },
                icon = {
                    val imageVector = if (selected) destination.filledIcon else destination.outlinedIcon
                    Icon(imageVector = imageVector, contentDescription = stringResource(
                        id = destination.label
                    ))
                },
                label = { Text(text = stringResource(id = destination.label)) }
            )
        }
    }
}

@ExperimentalMaterial3Api
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
enum class NavBarDestination(
    val direction: Direction,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector,
    @StringRes val label: Int
){
    Credentials(
        CredentialsScreenDestination,
        Icons.Filled.Lock,
        Icons.Outlined.Lock,
        R.string.navigationBar_credentials
    ),
    Generator(
        GeneratorScreenDestination(),
        Icons.Filled.Sync,
        Icons.Outlined.Sync,
        R.string.navigationBar_generator
    ),
    Settings(
        SettingsScreenDestination,
        Icons.Filled.Settings,
        Icons.Outlined.Settings,
        R.string.navigationBar_settings
    )
}