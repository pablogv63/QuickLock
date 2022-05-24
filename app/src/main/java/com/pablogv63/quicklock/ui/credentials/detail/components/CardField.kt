package com.pablogv63.quicklock.ui.credentials.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pablogv63.quicklock.ui.tools.AppPaddingValues

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardField(
    label: String,
    value: String,
    secondRowContent: @Composable()(() -> Unit)
){
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.outlinedCardColors()
    ) {
        Row(
            modifier = Modifier
                .padding(AppPaddingValues.Medium)
        ) {
            Column {
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(AppPaddingValues.Small))
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                secondRowContent()
            }
        }
    }
}