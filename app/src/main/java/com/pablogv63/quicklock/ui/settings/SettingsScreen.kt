package com.pablogv63.quicklock.ui.settings

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.pablogv63.quicklock.ui.navigation.QuickLockNavigationBar
import com.pablogv63.quicklock.ui.tools.Tools.dataStore
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import de.schnettler.datastore.compose.material3.PreferenceScreen
import de.schnettler.datastore.compose.material3.model.Preference
import de.schnettler.datastore.manager.PreferenceRequest
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
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
                current = "SettingsScreen",
                navigator = navigator
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SettingsScreenContent(
    viewModel: SettingsViewModel
){
    val context = LocalContext.current

    // Save
    val exportLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.CreateDocument()) { uri ->
        if (uri != null){
            viewModel.onEvent(SettingsEvent.ExportDatabase(context, uri))
        }
    }
    // Load
    val importLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        if (uri != null){
            viewModel.onEvent(SettingsEvent.ImportDatabase(context, uri))
        }
    }

    PreferenceScreen(
        items = listOf(
            Preference.PreferenceGroup(
                title = "Tools",
                enabled = true,
                listOf(
                    Preference.PreferenceItem.TextPreference(
                        title = "Save database",
                        summary = "Save database to a JSON file",
                        singleLineTitle = true,
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Save,
                                contentDescription = "Save",
                                modifier = Modifier.padding(8.dp)
                            )
                        },
                        onClick = {
                            exportLauncher.launch("QuickLockDatabase.json")
                        }
                    ),
                    Preference.PreferenceItem.TextPreference(
                        title = "Load database",
                        summary = "Load database from a JSON file",
                        singleLineTitle = true,
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.UploadFile,
                                contentDescription = "Save",
                                modifier = Modifier.padding(8.dp)
                            )
                        }, onClick = {
                            importLauncher.launch("application/json")
                        }
                    )
                )
            )
        ),
        dataStore = context.dataStore,
        statusBarPadding = true
    )
}

/**
 * Represents the Preference to choose if backup
 * TODO: Decide if backup is a choice
 */
fun backupPreference(): Preference.PreferenceItem<Boolean>{
    val backupRequest = PreferenceRequest<Boolean>(
        key = booleanPreferencesKey("backup"),
        defaultValue = true
    )

    return Preference.PreferenceItem.SwitchPreference(
        request = backupRequest,
        title = "Backup Preference",
        summary = "Choosing if backup",
        singleLineTitle = true,
        icon = {
            Icon(
                imageVector = Icons.Outlined.Save,
                contentDescription = null,
                modifier = Modifier.padding(8.dp)
            )
        }
    )
}
