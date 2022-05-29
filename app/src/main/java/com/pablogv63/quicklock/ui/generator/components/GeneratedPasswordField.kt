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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.pablogv63.quicklock.R
import com.pablogv63.quicklock.ui.tools.AppPaddingValues
import com.pablogv63.quicklock.ui.tools.Tools.copyTextToClipboard

@ExperimentalMaterial3Api
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
                    contentDescription = stringResource(id = R.string.generatorScreen_regenerate),
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
                    contentDescription = stringResource(id = R.string.action_copy_password),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewGeneratedPasswordField(){
    GeneratedPasswordField(
        onRegenerateClick = { /*TODO*/ },
        generatedPassword = "aLAwXXE4AwQcS9dE6AnQGLdE6AnQGL"
    )
}