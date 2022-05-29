package com.pablogv63.quicklock.ui.credentials.list.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.pablogv63.quicklock.R
import com.pablogv63.quicklock.domain.util.CredentialFilter
import com.pablogv63.quicklock.ui.credentials.list.CredentialsEvent
import com.pablogv63.quicklock.ui.credentials.list.CredentialsViewModel
import com.pablogv63.quicklock.ui.tools.AppPaddingValues


data class Filter(
    val name: String,
    val selected: Boolean,
    val onClick: () -> Unit
)

@ExperimentalMaterial3Api
@Composable
fun FilterSection(
    viewModel: CredentialsViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppPaddingValues.Small)
            .horizontalScroll(rememberScrollState())
    ) {
        val state = viewModel.state.value
        FilterItem(
            filter = Filter(
                name = stringResource(id = R.string.field_name_label),
                selected = state.filterByName,
                onClick = {
                    viewModel.onEvent(CredentialsEvent.FilterChanged(CredentialFilter.Name))
                }
            )
        )
        Spacer(modifier = Modifier.width(AppPaddingValues.Small))
        FilterItem(
            filter = Filter(
                name = stringResource(id = R.string.field_username_label),
                selected = state.filterByUsername,
                onClick = {
                    viewModel.onEvent(CredentialsEvent.FilterChanged(CredentialFilter.Username))
                }
            )
        )
        Spacer(modifier = Modifier.width(AppPaddingValues.Small))
        FilterItem(
            filter = Filter(
                name = stringResource(id = R.string.field_category_label),
                selected = state.filterByCategory,
                onClick = {
                    viewModel.onEvent(CredentialsEvent.FilterChanged(CredentialFilter.Category))
                }
            )
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun FilterItem(
    filter: Filter
) {
    FilterChip(
        selected = filter.selected,
        onClick = filter.onClick,
        label = { Text(text = filter.name) },
        selectedIcon = {
            Icon(
                imageVector = Icons.Filled.Done,
                contentDescription = null,
                modifier = Modifier.size(FilterChipDefaults.IconSize)
            )
        }
    )
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewFilterSection(){
    FilterItem(filter = Filter("Name",true) {})
}