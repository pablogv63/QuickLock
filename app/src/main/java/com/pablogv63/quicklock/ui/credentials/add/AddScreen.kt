package com.pablogv63.quicklock.ui.credentials.add

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Today
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.pablogv63.quicklock.R
import com.pablogv63.quicklock.ui.credentials.add.components.AddScreenTopAppBar
import com.pablogv63.quicklock.ui.credentials.form.FormEvent
import com.pablogv63.quicklock.ui.credentials.form.FormState
import com.pablogv63.quicklock.ui.credentials.form.components.CategoryChipList
import com.pablogv63.quicklock.ui.credentials.form.components.ExpirationDatePickerDialog
import com.pablogv63.quicklock.ui.credentials.form.components.Field
import com.pablogv63.quicklock.ui.destinations.GeneratorScreenDestination
import com.pablogv63.quicklock.ui.navigation.QuickLockNavigationBar
import com.pablogv63.quicklock.ui.tools.AppPaddingValues
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import org.koin.androidx.compose.getViewModel

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Destination
@Composable
fun AddScreen(
    viewModel: AddViewModel = getViewModel(),
    navigator: DestinationsNavigator,
    resultRecipient: ResultRecipient<GeneratorScreenDestination,String>
) {
    // Update password from Generator view
    resultRecipient.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {
                // Cancelled (no value)
                // In this case: do nothing
            }
            is NavResult.Value -> {
                // Update password and repeated password field
                viewModel.onEvent(FormEvent.PasswordChanged(result.value))
                viewModel.onEvent(FormEvent.RepeatedPasswordChanged(
                    password = result.value,
                    repeatedPassword = result.value
                ))
            }
        }
    }


    val formState = viewModel.formState
    val addState = viewModel.addState
    val context = LocalContext.current

    // Text
    val addedToastText = stringResource(id = R.string.addScreen_toast_added)

    //Gets called when validation is a success
    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when(event){
                AddViewModel.ValidationEvent.Success -> {
                    Toast.makeText(
                        context,
                        "${formState.name} $addedToastText",
                        Toast.LENGTH_LONG
                    ).show()
                    // Navigate back
                    navigator.navigateUp()
                }
            }
        }
    }

    Scaffold(
        topBar = { AddScreenTopAppBar(
            title = stringResource(id = R.string.addScreen_topAppBar_title),
            onArrowBackClick = { navigator.navigateUp() },
            onCheckClick = { viewModel.onEvent(FormEvent.Submit) }
        ) },
        bottomBar = {
            QuickLockNavigationBar(
                navigator = navigator
            )
        }
    ) { innerPadding ->
        AddScreenContent(
            formState = formState,
            addState = addState,
            viewModel = viewModel,
            innerPadding = innerPadding,
            context = context,
            navigateToGenerator = {
                navigator.navigate(GeneratorScreenDestination(
                    fromCredentialView = true,
                ))
            }
        )
    }
}

@ExperimentalMaterial3Api
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun AddScreenContent(
    formState: FormState,
    addState: AddState,
    viewModel: AddViewModel,
    innerPadding: PaddingValues,
    context: Context,
    navigateToGenerator: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding(),
                start = AppPaddingValues.Medium,
                end = AppPaddingValues.Medium
            )
    ) {
        // Name
        Field(
            value = formState.name,
            onValueChange = {
                viewModel.onEvent(FormEvent.NameChanged(it))
            },
            errorValue = formState.nameError,
            label = stringResource(id = R.string.field_name_label),
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Username
        Field(
            value = formState.username,
            onValueChange = {
                viewModel.onEvent(FormEvent.UsernameChanged(it))
            },
            errorValue = formState.usernameError,
            label = stringResource(id = R.string.field_username_label),
            keyboardType = KeyboardType.Email
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Password
        var showAsPassword by remember { mutableStateOf(true) }
        Field(
            value = formState.password,
            onValueChange = {
                viewModel.onEvent(FormEvent.PasswordChanged(it))
            },
            errorValue = formState.passwordError,
            label = stringResource(id = R.string.field_password_label),
            keyboardType = KeyboardType.Password,
            trailingIcon = {
                Row {
                    IconButton(onClick = { showAsPassword = !showAsPassword }) {
                        Icon(
                            imageVector =
                            if (showAsPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = stringResource(id = R.string.field_password_see)
                        )
                    }
                    IconButton(onClick = navigateToGenerator) {
                        Icon(imageVector = Icons.Filled.Sync, contentDescription = stringResource(id = R.string.field_password_generate))
                    }
                }
            },
            showAsPassword = showAsPassword
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Repeat Password
        var showRepeatedAsPassword by remember { mutableStateOf(true) }
        AnimatedVisibility(visible = formState.password.isNotBlank()) {
            Column {
                Field(
                    value = formState.repeatedPassword,
                    onValueChange = {
                        viewModel.onEvent(FormEvent.RepeatedPasswordChanged(formState.password, it))
                    },
                    errorValue = formState.repeatedPasswordError,
                    label = stringResource(id = R.string.field_repeatPassword_label),
                    keyboardType = KeyboardType.Password,
                    trailingIcon = {
                        IconButton(onClick = { showRepeatedAsPassword = !showRepeatedAsPassword }) {
                            Icon(
                                imageVector =
                                if (showRepeatedAsPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = stringResource(id = R.string.field_password_see)
                            )
                        }
                    },
                    showAsPassword = showRepeatedAsPassword
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        // Expiration date
        val expirationDatePickerDialog = ExpirationDatePickerDialog(
            context = context
        ) { expirationDate ->
            viewModel.onEvent(
                FormEvent.ExpirationDateChanged(
                    expirationDate = expirationDate
                )
            )
        }
        Field(
            value = formState.expirationDate,
            onValueChange = {
                viewModel.onEvent(FormEvent.ExpirationDateChanged(it))
            },
            errorValue = formState.expirationDateError,
            label = stringResource(id = R.string.field_expirationDate_label),
            supportiveText = stringResource(id = R.string.field_optional),
            keyboardType = KeyboardType.Text,
            trailingIcon = {
                IconButton(onClick = { expirationDatePickerDialog.show() }) {
                    Icon(imageVector = Icons.Filled.Today, contentDescription = stringResource(id = R.string.field_expirationDate_pickDate))
                }
            },
            readOnly = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Category
        CategoryChipList(
            categories = formState.categories,
            selectedCategories = formState.selectedCategories,
            onChipClick = { viewModel.onEvent(FormEvent.CategorySelected(it)) },
            onNewCategory = { name, category -> viewModel.onEvent(FormEvent.NewCategory(name,category)) },
            onRemoveCategoryClick = { viewModel.onEvent(FormEvent.CategoryRemoved(it)) }
        )
    }
}



