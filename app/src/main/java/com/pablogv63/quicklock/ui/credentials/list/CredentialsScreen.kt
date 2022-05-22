package com.pablogv63.quicklock.ui.credentials.list

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.pablogv63.quicklock.ui.credentials.destinations.AddScreenDestination
import com.pablogv63.quicklock.ui.credentials.list.components.CredentialItem
import com.pablogv63.quicklock.ui.tools.Tools
import com.pablogv63.quicklock.ui.tools.Tools.copyTextToClipboard
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel

@RootNavGraph(start = true)
@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CredentialsScreen(
    viewModel: CredentialsViewModel = getViewModel(),
    navigator: DestinationsNavigator
){
    val state = viewModel.state.value
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navigator.navigate(AddScreenDestination) }) {
                Icon(Icons.Filled.Add, contentDescription = "Add credential")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            LazyColumn(contentPadding = innerPadding){
                items(state.credentialsWithCategories){ credentialWithCategories ->
                    CredentialItem(
                        credentialWithCategoryList = credentialWithCategories,
                        onItemClick = { Toast.makeText(context,"Card pressed",Toast.LENGTH_SHORT).show() /*TODO*/ },
                        onCopyUsernameClick = {
                            context.copyTextToClipboard("Username", credentialWithCategories.credential.username)
                            Toast.makeText(
                                context,
                                "Username copied to clipboard",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onCopyPasswordClick = {
                            context.copyTextToClipboard("Password", credentialWithCategories.credential.password)
                            Toast.makeText(
                                context,
                                "Password copied to clipboard",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onEditButtonClick = { Toast.makeText(context,"Edit button pressed",Toast.LENGTH_SHORT).show() /*TODO*/ }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}