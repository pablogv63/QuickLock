package com.pablogv63.quicklock.ui.credentials.form.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pablogv63.quicklock.R
import com.pablogv63.quicklock.ui.tools.AppPaddingValues

@Composable
fun NewCategoryDialog(
    openDialog: MutableState<Boolean>,
    onConfirm: (String, Color) -> Unit
){
    var color = Color.Red
    var name by remember {
        mutableStateOf("")
    }
    if (openDialog.value){
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = { Text(text = stringResource(id = R.string.dialog_newCategory_title)) },
            text = {
                Column {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text(text = stringResource(id = R.string.field_name_label)) }
                    )
                    Spacer(modifier = Modifier.height(AppPaddingValues.Small))
                    Text(
                        text = stringResource(id = R.string.field_color_label),
                        style = MaterialTheme.typography.labelLarge
                    )
                    ColorPickerDialog(
                        onColorChanged = { color = it.toColor() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )

                }
            },
            confirmButton = {
                TextButton(onClick = {
                    openDialog.value = false
                    onConfirm(name, color)
                }) {
                    Text(text = stringResource(id = R.string.dialog_confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { openDialog.value = false }) {
                    Text(text = stringResource(id = R.string.dialog_dismiss))
                }
            },
        )
    }
}