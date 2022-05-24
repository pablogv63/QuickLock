package com.pablogv63.quicklock.ui.credentials.detail.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun DetailScreenTopAppBar(
    title: String,
    onArrowBackClick: () -> Unit,
    onEditClick: () -> Unit
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
            IconButton(onClick = onEditClick) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Confirm"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
    )
}