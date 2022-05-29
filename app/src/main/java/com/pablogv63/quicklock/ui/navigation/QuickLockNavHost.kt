package com.pablogv63.quicklock.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import com.pablogv63.quicklock.ui.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost

@ExperimentalMaterial3Api
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun QuickLockNavHost() {
    DestinationsNavHost(navGraph = NavGraphs.root)
}