package com.pablogv63.quicklock.ui.navigation

import androidx.compose.runtime.Composable
import com.pablogv63.quicklock.ui.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost

@Composable
fun QuickLockNavHost() {
    DestinationsNavHost(navGraph = NavGraphs.root)
}