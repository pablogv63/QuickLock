package com.pablogv63.quicklock.ui.credentials

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.pablogv63.quicklock.ui.credentials.components.CredentialItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CredentialsScreen(
    viewModel: CredentialsViewModel
){
    val state = viewModel.state.value
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add credential")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            LazyColumn(contentPadding = innerPadding){
                items(state.credentialsWithCategories){ credentialWithCategories ->
                    CredentialItem(
                        credentialWithCategoryList = credentialWithCategories,
                        onItemClick = { Toast.makeText(context,"Card pressed",Toast.LENGTH_SHORT).show() },
                        onCopyUsernameClick = { Toast.makeText(context,"Copy Username Button pressed",Toast.LENGTH_SHORT).show() },
                        onCopyPasswordClick = { Toast.makeText(context,"Copy Password Button pressed",Toast.LENGTH_SHORT).show() },
                        onEditButtonClick = { Toast.makeText(context,"Edit Button pressed",Toast.LENGTH_SHORT).show() }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}