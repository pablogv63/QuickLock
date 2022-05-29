package com.pablogv63.quicklock.ui.credentials.list

import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.pablogv63.quicklock.R
import com.pablogv63.quicklock.ui.credentials.list.components.*
import com.pablogv63.quicklock.ui.destinations.AddScreenDestination
import com.pablogv63.quicklock.ui.destinations.DetailScreenDestination
import com.pablogv63.quicklock.ui.destinations.EditScreenDestination
import com.pablogv63.quicklock.ui.navigation.NavBarDestination
import com.pablogv63.quicklock.ui.navigation.QuickLockNavigationBar
import com.pablogv63.quicklock.ui.tools.AppPaddingValues
import com.pablogv63.quicklock.ui.tools.Tools.copyTextToClipboard
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@RootNavGraph(start = true)
@Destination
@ExperimentalMaterial3Api
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun CredentialsScreen(
    viewModel: CredentialsViewModel = getViewModel(),
    navigator: DestinationsNavigator
) {
    val state = viewModel.state.value
    val context = LocalContext.current
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val copiedUsernameToastText = stringResource(id = R.string.credentialsScreen_toast_copiedUsername)
    val copiedPasswordToastText = stringResource(id = R.string.credentialsScreen_toast_copiedPassword)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navigator.navigate(AddScreenDestination) }) {
                Icon(Icons.Filled.Add, contentDescription = stringResource(id = R.string.credentialsScreen_addFAB_title))
            }
        },
        topBar = {
            Column (
                modifier = Modifier.animateContentSize()
                    ){
                if (state.isSearchBarVisible) {
                    Column {
                        CredentialSearchBar(
                            searchText = state.searchBarText,
                            onSearchTextChanged = {
                                viewModel.onEvent(
                                    CredentialsEvent.SearchTextChanged(
                                        it
                                    )
                                )
                            },
                            onNavigateBack = { viewModel.onEvent(CredentialsEvent.ToggleSearchBar) },
                            onClearClick = { viewModel.onEvent(CredentialsEvent.ClearSearchText) }
                        )
                            // Filter section
                            FilterSection(viewModel = viewModel)
                    }
                } else {
                    CenterAlignedTopAppBar(
                        title = { Text(stringResource(id = R.string.app_name)) },
                        navigationIcon = {
                            IconButton(onClick = { viewModel.onEvent(CredentialsEvent.ToggleOrderMenu) }) {
                                if (state.isOrderMenuVisible) {
                                    Icon(
                                        imageVector = Icons.Filled.FilterListOff,
                                        contentDescription = stringResource(id = R.string.credentialsScreen_filter_title)
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Filled.FilterList,
                                        contentDescription = stringResource(id = R.string.credentialsScreen_filter_title)
                                    )
                                }
                            }
                        },
                        actions = {
                            IconButton(onClick = { viewModel.onEvent(CredentialsEvent.ToggleSearchBar) }) {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = stringResource(id = R.string.search_title)
                                )
                            }
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
                    )
                }
                if (state.isOrderMenuVisible) {
                    OrderSection(
                        credentialOrder = state.credentialOrder
                    ) {
                        viewModel.onEvent(CredentialsEvent.Order(it))
                    }
                }
            }
        },
        bottomBar = {
            QuickLockNavigationBar(
                navigator = navigator,
                currentDestination = NavBarDestination.Credentials,
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
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
        ) {

            LazyColumn(
                state = listState,
            ) {
                items(
                    if (state.isSearchBarVisible) {
                        state.matchedCredentialsWithCategories
                    } else state.credentialsWithCategories
                ) { credentialWithCategories ->
                    CredentialItem(
                        credentialWithCategoryList = credentialWithCategories,
                        onItemClick = {
                            viewModel.onEvent(CredentialsEvent.CredentialAccessed(credentialWithCategories.credential))
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
                                copiedUsernameToastText,
                                Toast.LENGTH_SHORT
                            ).show()
                            viewModel.onEvent(CredentialsEvent.CredentialAccessed(credentialWithCategories.credential))
                        },
                        onCopyPasswordClick = {
                            context.copyTextToClipboard(
                                "Password",
                                credentialWithCategories.credential.password
                            )
                            Toast.makeText(
                                context,
                                copiedPasswordToastText,
                                Toast.LENGTH_SHORT
                            ).show()
                            viewModel.onEvent(CredentialsEvent.CredentialAccessed(credentialWithCategories.credential))
                        },
                        onEditButtonClick = {
                            viewModel.onEvent(CredentialsEvent.CredentialAccessed(credentialWithCategories.credential))
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