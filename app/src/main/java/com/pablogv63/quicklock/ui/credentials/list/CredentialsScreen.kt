package com.pablogv63.quicklock.ui.credentials.list

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.pablogv63.quicklock.ui.credentials.list.components.CredentialItem
import com.pablogv63.quicklock.ui.destinations.AddScreenDestination
import com.pablogv63.quicklock.ui.destinations.DetailScreenDestination
import com.pablogv63.quicklock.ui.destinations.EditScreenDestination
import com.pablogv63.quicklock.ui.navigation.QuickLockNavigationBar
import com.pablogv63.quicklock.ui.tools.Tools.copyTextToClipboard
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@RootNavGraph(start = true)
@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CredentialsScreen(
    viewModel: CredentialsViewModel = getViewModel(),
    navigator: DestinationsNavigator
) {
    val state = viewModel.state.value
    val context = LocalContext.current
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navigator.navigate(AddScreenDestination) }) {
                Icon(Icons.Filled.Add, contentDescription = "Add credential")
            }
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("QuickLock") },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.FilterList,
                            contentDescription = "Filter"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
            )
        },
        bottomBar = {
            QuickLockNavigationBar(
                current = "CredentialsScreen",
                navigator = navigator,
                onSameClick = {
                    // From: https://www.android--code.com/2021/04/jetpack-compose-lazycolumn-scroll-to.html
                    coroutineScope.launch {
                        if (listState.firstVisibleItemIndex != 0) {
                            listState.animateScrollToItem(0)
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            LazyColumn(
                state = listState,
                contentPadding = innerPadding
            ) {
                items(state.credentialsWithCategories) { credentialWithCategories ->
                    CredentialItem(
                        credentialWithCategoryList = credentialWithCategories,
                        onItemClick = {
                            navigator.navigate(DetailScreenDestination(
                                credentialId = credentialWithCategories.credential.credentialId))
                        },
                        onCopyUsernameClick = {
                            context.copyTextToClipboard(
                                "Username",
                                credentialWithCategories.credential.username
                            )
                            Toast.makeText(
                                context,
                                "Username copied to clipboard",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onCopyPasswordClick = {
                            context.copyTextToClipboard(
                                "Password",
                                credentialWithCategories.credential.password
                            )
                            Toast.makeText(
                                context,
                                "Password copied to clipboard",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onEditButtonClick = {
                            navigator.navigate(
                                EditScreenDestination(credentialId =
                                    credentialWithCategories.credential.credentialId
                                )
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}