package com.pablogv63.quicklock.ui.credentials.form.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.pablogv63.quicklock.R
import com.pablogv63.quicklock.domain.model.Category
import com.pablogv63.quicklock.ui.tools.AppPaddingValues

@ExperimentalMaterial3Api
@Composable
fun CategoryChipList(
    categories: List<Category>,
    selectedCategories: List<Category>,
    onChipClick: (Category) -> Unit,
    onRemoveCategoryClick: (Category) -> Unit,
    onNewCategory: (String, Color) -> Unit,
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppPaddingValues.Small),
        colors = CardDefaults.outlinedCardColors()
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.field_categories_label),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(AppPaddingValues.Small))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
            ) {
                val openDialog = remember { mutableStateOf(false) }
                AssistChip(
                    onClick = { openDialog.value = true },
                    label = {
                        Text(
                            text = stringResource(id = R.string.dialog_open)
                        )
                    },
                    trailingIcon = {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = stringResource(id = R.string.dialog_newCategory_title),
                            Modifier.size(InputChipDefaults.IconSize)
                        )
                    }
                )
                NewCategoryDialog(
                    openDialog = openDialog,
                    onConfirm = onNewCategory
                )
                selectedCategories.forEach { category ->
                    Spacer(modifier = Modifier.width(AppPaddingValues.Small))
                    InputChip(
                        onClick = { onRemoveCategoryClick(category) },
                        label = {
                            Text(text = category.name, color = Color(category.colour))
                        },
                        trailingIcon = {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = category.name,
                                Modifier.size(InputChipDefaults.IconSize)
                            )
                        },
                    )
                }
                categories.filter { !selectedCategories.contains(it) }.forEach { category ->
                    Spacer(modifier = Modifier.width(AppPaddingValues.Small))
                        AssistChip(
                            onClick = { onChipClick(category) },
                            label = {
                                Text(text = category.name, color = Color(category.colour))
                            }
                        )
                }
            }
        }
    }
}

