package com.pablogv63.quicklock.ui.generator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pablogv63.quicklock.ui.navigation.QuickLockNavigationBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun GeneratorScreen(
    navigator: DestinationsNavigator,
    fromCredentialView: Boolean = false
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Password generator") },
                navigationIcon = {
                    if (fromCredentialView) {
                        IconButton(onClick = { navigator.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                },
                actions = {
                    if (fromCredentialView) {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = "Search"
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            QuickLockNavigationBar(
                current = "GeneratorScreen",
                navigator = navigator
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

        }
    }
}