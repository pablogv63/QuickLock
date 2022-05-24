package com.pablogv63.quicklock.ui.credentials.edit

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import com.pablogv63.quicklock.ui.credentials.edit.components.EditScreenTopAppBar
import com.pablogv63.quicklock.ui.credentials.form.FormEvent
import com.pablogv63.quicklock.ui.credentials.form.FormState
import com.pablogv63.quicklock.ui.credentials.form.components.CategoryDropdownMenu
import com.pablogv63.quicklock.ui.credentials.form.components.ExpirationDatePickerDialog
import com.pablogv63.quicklock.ui.credentials.form.components.Field
import com.pablogv63.quicklock.ui.destinations.CredentialsScreenDestination
import com.pablogv63.quicklock.ui.navigation.QuickLockNavigationBar
import com.pablogv63.quicklock.ui.tools.AppPaddingValues
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.viewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun EditScreen(
    navigator: DestinationsNavigator,
    credentialId: Int
){
    val viewModel: EditViewModel by viewModel {
        parametersOf(credentialId)
    }
    val formState = viewModel.formState
    val editState = viewModel.editState
    val context = LocalContext.current

    //Gets called when validation is a success
    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when(event){
                EditViewModel.ValidationEvent.Success -> {
                    Toast.makeText(
                        context,
                        "${formState.name} edited successfully",
                        Toast.LENGTH_LONG
                    ).show()
                    // Navigate back
                    navigator.navigateUp()
                }
            }
        }
    }

    Scaffold(
        topBar = { EditScreenTopAppBar(
            title = "Edit ${formState.name}",
            onArrowBackClick = { navigator.navigateUp() },
            onCheckClick = { viewModel.onEvent(FormEvent.Submit) }
        ) },
        bottomBar = {
            QuickLockNavigationBar(
                current = "CredentialsScreen",
                navigator = navigator,
                onSameClick = {
                    navigator.navigate(CredentialsScreenDestination)
                }
            )
        }
    ) { innerPadding ->
        EditScreenContent(
            formState = formState,
            editState = editState,
            viewModel = viewModel,
            innerPadding = innerPadding,
            context = context
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreenContent(
    formState: FormState,
    editState: EditState,
    viewModel: EditViewModel,
    innerPadding: PaddingValues,
    context: Context
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding(),
                start = AppPaddingValues.Medium,
                end = AppPaddingValues.Medium
            ),
    ) {
        // Name
        Field(
            value = formState.name,
            onValueChange = {
                viewModel.onEvent(FormEvent.NameChanged(it))
            },
            errorValue = formState.nameError,
            label = "Name",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(AppPaddingValues.Medium))
        // Username
        Field(
            value = formState.username,
            onValueChange = {
                viewModel.onEvent(FormEvent.UsernameChanged(it))
            },
            errorValue = formState.usernameError,
            label = "Username",
            keyboardType = KeyboardType.Email
        )
        Spacer(modifier = Modifier.height(AppPaddingValues.Medium))
        // Password
        var showAsPassword by remember { mutableStateOf(true) }
        Field(
            value = formState.password,
            onValueChange = {
                viewModel.onEvent(FormEvent.PasswordChanged(it))
            },
            errorValue = formState.passwordError,
            label = "Password",
            keyboardType = KeyboardType.Password,
            trailingIcon = {
                Row {
                    IconButton(onClick = { showAsPassword = !showAsPassword }) {
                        Icon(
                            imageVector =
                            if (showAsPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = "See password"
                        )
                    }
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(imageVector = Icons.Filled.Sync, contentDescription = "Generate")
                    }
                }
            },
            showAsPassword = showAsPassword
        )
        Spacer(modifier = Modifier.height(AppPaddingValues.Medium))
        // Repeat Password
        var showRepeatedAsPassword by remember { mutableStateOf(true) }
        AnimatedVisibility(visible = formState.passwordTextChanged) {
            Column {
                Field(
                    value = formState.repeatedPassword,
                    onValueChange = {
                        viewModel.onEvent(FormEvent.RepeatedPasswordChanged(formState.password, it))
                    },
                    errorValue = formState.repeatedPasswordError,
                    label = "Repeat password",
                    keyboardType = KeyboardType.Password,
                    trailingIcon = {
                        IconButton(onClick = {
                            showRepeatedAsPassword = !showRepeatedAsPassword
                        }) {
                            Icon(
                                imageVector =
                                if (showRepeatedAsPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = "See password"
                            )
                        }
                    },
                    showAsPassword = showAsPassword
                )
                Spacer(modifier = Modifier.height(AppPaddingValues.Medium))
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
            label = "Expiration date",
            keyboardType = KeyboardType.Text,
            trailingIcon = {
                IconButton(onClick = { expirationDatePickerDialog.show() }) {
                    Icon(imageVector = Icons.Filled.Today, contentDescription = "Pick date")
                }
            },
            readOnly = true
        )
        Spacer(modifier = Modifier.height(AppPaddingValues.Medium))
        // Category
        CategoryDropdownMenu(
            categoryName = formState.category,
            onValueChange = { viewModel.onEvent(FormEvent.CategoryChanged(it)) },
            categories = formState.categories,
            onDropdownMenuClick = { viewModel.onEvent(FormEvent.CategoryChanged(it)) },
            fromEdit = true
        )
        // Delete button
        Box(modifier = Modifier.fillMaxSize()) {
            FilledIconButton(
                onClick = { viewModel.onEvent(EditEvent.Delete(editState.credentialId)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete"
                    )
                    Spacer(modifier = Modifier.width(AppPaddingValues.Small))
                    Text(text = "Delete")
                }
            }
        }
    }
}