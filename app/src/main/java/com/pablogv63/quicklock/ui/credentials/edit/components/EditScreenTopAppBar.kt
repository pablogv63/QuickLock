package com.pablogv63.quicklock.ui.credentials.edit.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun EditScreenTopAppBar(
    title: String,
    onArrowBackClick: () -> Unit,
    onCheckClick: () -> Unit
){
    CenterAlignedTopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = onArrowBackClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(onClick = onCheckClick) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Confirm"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
    )
}