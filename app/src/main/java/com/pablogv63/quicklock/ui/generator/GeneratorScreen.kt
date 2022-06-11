package com.pablogv63.quicklock.ui.generator

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.pablogv63.quicklock.R
import com.pablogv63.quicklock.ui.generator.components.GeneratedPasswordField
import com.pablogv63.quicklock.ui.generator.components.LengthField
import com.pablogv63.quicklock.ui.generator.components.ToggleField
import com.pablogv63.quicklock.ui.navigation.NavBarDestination
import com.pablogv63.quicklock.ui.navigation.QuickLockNavigationBar
import com.pablogv63.quicklock.ui.tools.AppPaddingValues
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import org.koin.androidx.compose.getViewModel

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Destination
@Composable
fun GeneratorScreen(
    viewModel: GeneratorViewModel = getViewModel(),
    navigator: DestinationsNavigator,
    fromCredentialView: Boolean = false,
    resultNavigator: ResultBackNavigator<String>
) {
    val state = viewModel.generatorState
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(id = R.string.generatorScreen_title)) },
                navigationIcon = {
                    if (fromCredentialView) IconButton(onClick = { navigator.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.topAppBar_back)
                        )
                    }
                },
                actions = {
                    if (fromCredentialView) IconButton(onClick = {
                        resultNavigator.navigateBack(state.generatedPassword)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = stringResource(id = R.string.topAppBar_confirm)
                        )
                    }
                }
            )
        },
        bottomBar = {
            if (!fromCredentialView) {
                QuickLockNavigationBar(
                    navigator = navigator,
                    currentDestination = NavBarDestination.Generator
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
            LengthField(
                title = stringResource(id = R.string.generatorScreen_length),
                length = viewModel.generatorState.length,
                onValueChange = {
                    viewModel.onEvent(GeneratorEvent.LengthChanged(it))
                }
            )
            Divider(color = MaterialTheme.colorScheme.surfaceVariant)
            ToggleField(
                title = stringResource(id = R.string.generatorScreen_uppercase),
                checked = state.uppercase,
                onTogglePressed = {
                    viewModel.onEvent(GeneratorEvent.UppercaseToggleChanged(it))
                }
            )
            Divider(color = MaterialTheme.colorScheme.surfaceVariant)
            ToggleField(
                title = stringResource(id = R.string.generatorScreen_lowercase),
                checked = state.lowercase,
                onTogglePressed = {
                    viewModel.onEvent(GeneratorEvent.LowercaseToggleChanged(it))
                }
            )
            Divider(color = MaterialTheme.colorScheme.surfaceVariant)
            ToggleField(
                title = stringResource(id = R.string.generatorScreen_numbers),
                checked = state.numbers,
                onTogglePressed = {
                    viewModel.onEvent(GeneratorEvent.NumbersToggleChanged(it))
                }
            )
            Divider(color = MaterialTheme.colorScheme.surfaceVariant)
            ToggleField(
                title = stringResource(id = R.string.generatorScreen_specialChars),
                checked = state.special,
                onTogglePressed = {
                    viewModel.onEvent(GeneratorEvent.SpecialCharsToggleChanged(it))
                }
            )
        }
    }
}