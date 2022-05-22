package com.pablogv63.quicklock.ui.credentials.add.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import com.pablogv63.quicklock.domain.model.Category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdownMenu(
    categoryName: String,
    onValueChange: (String) -> Unit,
    categories: List<Category>,
    onDropdownMenuClick: (String) -> Unit
){
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        Column {
            Field(
                value = categoryName,
                onValueChange = { onValueChange(it) },
                errorValue = null,
                label = "Category",
                supportiveText = "*Optional",
                keyboardType = KeyboardType.Text,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { category ->
                    if (category.name.lowercase().contains(categoryName.lowercase())){
                        DropdownMenuItem(
                            text = { Text(text = category.name, color = Color(category.colour)) },
                            onClick = {
                                onDropdownMenuClick(category.name)
                            }
                        )
                    }
                }
                /*
                DropdownMenuItem(
                    text = { Text(text = "New") },
                    onClick = {
                        // TODO: Prompt to add new category?
                    }
                )
                */
            }
        }
    }
}