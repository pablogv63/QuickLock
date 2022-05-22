package com.pablogv63.quicklock.ui.credentials.add

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Today
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.pablogv63.quicklock.ui.credentials.add.components.CategoryDropdownMenu
import com.pablogv63.quicklock.ui.credentials.add.components.ExpirationDatePickerDialog
import com.pablogv63.quicklock.ui.credentials.add.components.Field
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel

@Destination
@Composable
fun AddScreen(
    viewModel: AddViewModel = getViewModel(),
    navigator: DestinationsNavigator
) {
    val state = viewModel.state
    val context = LocalContext.current

    //Gets called when validation is a success
    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when(event){
                AddViewModel.ValidationEvent.Success -> {
                    Toast.makeText(
                        context,
                        "Added successfully",
                        Toast.LENGTH_LONG
                    ).show()
                    // Navigate back
                    navigator.navigateUp()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Name
        Field(
            value = state.name,
            onValueChange = {
                viewModel.onEvent(AddFormEvent.NameChanged(it))
            },
            errorValue = state.nameError,
            label = "Name",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Username
        Field(
            value = state.username,
            onValueChange = {
                viewModel.onEvent(AddFormEvent.UsernameChanged(it))
            },
            errorValue = state.usernameError,
            label = "Username",
            keyboardType = KeyboardType.Email
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Password
        var showAsPassword by remember { mutableStateOf(true) }
        Field(
            value = state.password,
            onValueChange = {
                viewModel.onEvent(AddFormEvent.PasswordChanged(it))
            },
            errorValue = state.passwordError,
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
        Spacer(modifier = Modifier.height(16.dp))
        // Repeat Password
        var showRepeatedAsPassword by remember { mutableStateOf(true) }
        AnimatedVisibility(visible = state.password.isNotBlank()) {
            Column {
                Field(
                    value = state.repeatedPassword,
                    onValueChange = {
                        viewModel.onEvent(AddFormEvent.RepeatedPasswordChanged(state.password, it))
                    },
                    errorValue = state.repeatedPasswordError,
                    label = "Repeat password",
                    keyboardType = KeyboardType.Password,
                    trailingIcon = {
                        IconButton(onClick = { showRepeatedAsPassword = !showRepeatedAsPassword }) {
                            Icon(
                                imageVector =
                                if (showRepeatedAsPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = "See password"
                            )
                        }
                    },
                    showAsPassword = showAsPassword
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        // Expiration date
        val expirationDatePickerDialog = ExpirationDatePickerDialog(
            context = context
        ) { expirationDate ->
            viewModel.onEvent(
                AddFormEvent.ExpirationDateChanged(
                    expirationDate = expirationDate
                )
            )
        }
        Field(
            value = state.expirationDate,
            onValueChange = {
                viewModel.onEvent(AddFormEvent.ExpirationDateChanged(it))
            },
            errorValue = state.expirationDateError,
            label = "Expiration date",
            supportiveText = "*Optional",
            keyboardType = KeyboardType.Text,
            trailingIcon = {
                IconButton(onClick = { expirationDatePickerDialog.show() }) {
                    Icon(imageVector = Icons.Filled.Today, contentDescription = "Pick date")
                }
            },
            readOnly = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Category
        CategoryDropdownMenu(
            categoryName = state.category,
            onValueChange = { viewModel.onEvent(AddFormEvent.CategoryChanged(it)) },
            categories = state.categories,
            onDropdownMenuClick = { viewModel.onEvent(AddFormEvent.CategoryChanged(it)) }
        )
        // Submit button TODO: Make the topAppBar make the submit
        Box(modifier = Modifier.fillMaxSize()) {
            OutlinedButton(
                onClick = { viewModel.onEvent(AddFormEvent.Submit) },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd)
            ) {
                Text(text = "Submit")
            }
        }
    }
}



