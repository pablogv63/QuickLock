package com.pablogv63.quicklock.ui.generator.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pablogv63.quicklock.ui.tools.AppPaddingValues

@ExperimentalMaterial3Api
@Composable
fun ToggleField(
    title: String,
    checked: Boolean,
    onTogglePressed: (Boolean) -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.outlinedCardColors()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge
            )
            val icon: (@Composable () -> Unit)? = if (checked) {
                {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            } else {
                null
            }
            Switch(
                checked = checked,
                onCheckedChange = onTogglePressed,
                thumbContent = icon
            )
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewToggleField(){
    ToggleField(title = "Uppercase (A-Z)", checked = true) { }
}