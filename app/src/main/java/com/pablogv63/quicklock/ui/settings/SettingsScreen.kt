package com.pablogv63.quicklock.ui.settings

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.UploadFile
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.pablogv63.quicklock.R
import com.pablogv63.quicklock.ui.navigation.NavBarDestination
import com.pablogv63.quicklock.ui.navigation.QuickLockNavigationBar
import com.pablogv63.quicklock.ui.tools.Tools.dataStore
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import de.schnettler.datastore.compose.material3.PreferenceScreen
import de.schnettler.datastore.compose.material3.model.Preference
import org.koin.androidx.compose.getViewModel

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Destination
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = getViewModel(),
    navigator: DestinationsNavigator
){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Settings") }
            )
        },
        bottomBar = {
            QuickLockNavigationBar(
                navigator = navigator,
                currentDestination = NavBarDestination.Settings
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SettingsScreenContent(viewModel)
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Composable
fun SettingsScreenContent(
    viewModel: SettingsViewModel
){
    val context = LocalContext.current

    // Save
    val exportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument()
    ) { uri ->
        if (uri != null){
            viewModel.onEvent(SettingsEvent.ExportDatabase(context, uri))
        }
    }
    // Load
    val importLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null){
            viewModel.onEvent(SettingsEvent.ImportDatabase(context, uri))
        }
    }

    PreferenceScreen(
        items = listOf(
            Preference.PreferenceGroup(
                title = stringResource(id = R.string.settingsScreen_tools),
                enabled = true,
                listOf(
                    Preference.PreferenceItem.TextPreference(
                        title = stringResource(id = R.string.settingsScreen_saveDatabase_json_title),
                        onClick = {
                            exportLauncher.launch("QuickLockDatabase.json")
                        },
                        summary = stringResource(id = R.string.settingsScreen_saveDatabase_json_summary),
                        singleLineTitle = true,
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Save,
                                contentDescription = stringResource(id = R.string.settingsScreen_saveDatabase_json_title),
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    ),
                    Preference.PreferenceItem.TextPreference(
                        title = stringResource(id = R.string.settingsScreen_loadDatabase_json_title),
                        onClick = {
                            importLauncher.launch("application/json")
                        },
                        summary = stringResource(id = R.string.settingsScreen_loadDatabase_json_summary),
                        singleLineTitle = true,
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.UploadFile,
                                contentDescription = stringResource(id = R.string.settingsScreen_loadDatabase_json_title),
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    )
                )
            )
        ),
        dataStore = context.dataStore,
        statusBarPadding = true
    )
}
