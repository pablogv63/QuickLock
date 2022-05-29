package com.pablogv63.quicklock.ui.credentials.list.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pablogv63.quicklock.R
import com.pablogv63.quicklock.domain.util.CredentialOrder
import com.pablogv63.quicklock.domain.util.OrderType
import com.pablogv63.quicklock.ui.tools.AppPaddingValues

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    credentialOrder: CredentialOrder = CredentialOrder.Name(OrderType.Descending),
    onOrderChange: (CredentialOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(id = R.string.field_name_label),
                selected = credentialOrder is CredentialOrder.Name,
                onSelect = { onOrderChange(CredentialOrder.Name(credentialOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(id = R.string.field_username_label),
                selected = credentialOrder is CredentialOrder.Username,
                onSelect = { onOrderChange(CredentialOrder.Username(credentialOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(id = R.string.field_expirationDate_label),
                selected = credentialOrder is CredentialOrder.ExpirationDate,
                onSelect = { onOrderChange(CredentialOrder.ExpirationDate(credentialOrder.orderType)) }
            )
        }
        Row (
            modifier = Modifier.fillMaxWidth()
                ){
            DefaultRadioButton(
                text = stringResource(id = R.string.field_lastModified_label),
                selected = credentialOrder is CredentialOrder.LastModified,
                onSelect = { onOrderChange(CredentialOrder.LastModified(credentialOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(id = R.string.field_lastAccessed_label),
                selected = credentialOrder is CredentialOrder.LastAccessed,
                onSelect = { onOrderChange(CredentialOrder.LastAccessed(credentialOrder.orderType)) }
            )
        }
        Divider(color = MaterialTheme.colorScheme.surfaceVariant)
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(id = R.string.order_ascending),
                selected = credentialOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(credentialOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(id = R.string.order_descending),
                selected = credentialOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(credentialOrder.copy(OrderType.Descending))
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultRadioButton(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.primary,
                unselectedColor = MaterialTheme.colorScheme.onBackground
            )
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, style = MaterialTheme.typography.bodySmall)
    }
}

@Preview
@Composable
fun PreviewOrderSection(){
    Surface {
        OrderSection() {}
    }
}