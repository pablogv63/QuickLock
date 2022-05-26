package com.pablogv63.quicklock.ui.generator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pablogv63.quicklock.ui.generator.components.GeneratedPasswordField
import com.pablogv63.quicklock.ui.generator.components.LengthField
import com.pablogv63.quicklock.ui.generator.components.ToggleField
import com.pablogv63.quicklock.ui.navigation.QuickLockNavigationBar
import com.pablogv63.quicklock.ui.tools.AppPaddingValues
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun GeneratorScreen(
    viewModel: GeneratorViewModel = getViewModel(),
    navigator: DestinationsNavigator,
    fromCredentialView: Boolean = false
) {
    val state = viewModel.generatorState
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Password generator") },
                navigationIcon = {
                    if (fromCredentialView) IconButton(onClick = { navigator.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        bottomBar = {
            if (!fromCredentialView) {
                QuickLockNavigationBar(
                    current = "GeneratorScreen",
                    navigator = navigator
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding(),
                    start = AppPaddingValues.Small,
                    end = AppPaddingValues.Small
                )
        ) {
            GeneratedPasswordField(
                onRegenerateClick = { viewModel.onEvent(GeneratorEvent.RefreshPassword) },
                generatedPassword = state.generatedPassword
            )
            LengthField(title = "Length", length = viewModel.generatorState.length, onValueChange = {
                viewModel.onEvent(GeneratorEvent.LengthChanged(it))
            })
            Divider(color = MaterialTheme.colorScheme.surfaceVariant)
            ToggleField(title = "Uppercase (A-Z)", checked = state.uppercase, onTogglePressed = {
                viewModel.onEvent(GeneratorEvent.UppercaseToggleChanged(it))
            })
            Divider(color = MaterialTheme.colorScheme.surfaceVariant)
            ToggleField(title = "Lowercase (a-z)", checked = state.lowercase, onTogglePressed = {
                viewModel.onEvent(GeneratorEvent.LowercaseToggleChanged(it))
            })
            Divider(color = MaterialTheme.colorScheme.surfaceVariant)
            ToggleField(title = "Numbers", checked = state.numbers, onTogglePressed = {
                viewModel.onEvent(GeneratorEvent.NumbersToggleChanged(it))
            })
            Divider(color = MaterialTheme.colorScheme.surfaceVariant)
            ToggleField(
                title = "Special characters (!@#$%^&*€)",
                checked = state.special,
                onTogglePressed = {
                    viewModel.onEvent(GeneratorEvent.SpecialCharsToggleChanged(it))
                }
            )
        }
    }
}