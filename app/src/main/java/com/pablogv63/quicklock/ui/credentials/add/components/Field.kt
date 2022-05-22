package com.pablogv63.quicklock.ui.credentials.add.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Field(
    value: String,
    onValueChange: (String) -> Unit,
    errorValue: String?,
    label: String,
    supportiveText: String? = null,
    keyboardType: KeyboardType,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    readOnly: Boolean = false,
    showAsPassword: Boolean = false
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        isError = errorValue != null,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(text = label)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        readOnly = readOnly,
        visualTransformation =
        if(showAsPassword)
            PasswordVisualTransformation()
        else
            VisualTransformation.None
    )
    if (errorValue != null) {
        Text(
            text = errorValue,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 8.dp)
        )
    } else if (supportiveText != null){
        // Supportive text
        Text(
            text = supportiveText,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Preview
@Composable
fun PreviewField(){
    Surface {
        Column {
            Field(
                value = "Name of the credential",
                onValueChange = {},
                errorValue = null,
                label = "Name",
                keyboardType = KeyboardType.Text
            )
        }
    }
}