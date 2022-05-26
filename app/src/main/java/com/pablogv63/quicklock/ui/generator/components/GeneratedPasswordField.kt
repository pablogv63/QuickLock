package com.pablogv63.quicklock.ui.generator.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.pablogv63.quicklock.ui.tools.AppPaddingValues
import com.pablogv63.quicklock.ui.tools.Tools.copyTextToClipboard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneratedPasswordField(
    onRegenerateClick: () -> Unit,
    generatedPassword: String
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.outlinedCardColors()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = AppPaddingValues.Small),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onRegenerateClick) {
                Icon(
                    imageVector = Icons.Filled.Sync,
                    contentDescription = "Regenerate",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                text = generatedPassword,
                style = MaterialTheme.typography.titleMedium,
            )
            IconButton(onClick = {
                context.copyTextToClipboard(
                    "Generated password",
                    generatedPassword
                )
            }) {
                Icon(
                    imageVector = Icons.Filled.ContentCopy,
                    contentDescription = "Copy",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewGeneratedPasswordField(){
    GeneratedPasswordField(
        onRegenerateClick = { /*TODO*/ },
        generatedPassword = "aLAwXXE4AwQcS9dE6AnQGLdE6AnQGL"
    )
}