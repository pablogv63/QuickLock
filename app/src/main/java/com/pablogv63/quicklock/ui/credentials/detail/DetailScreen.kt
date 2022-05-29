package com.pablogv63.quicklock.ui.credentials.detail

import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.pablogv63.quicklock.R
import com.pablogv63.quicklock.domain.util.DateGuidelines.expiresSoon
import com.pablogv63.quicklock.ui.credentials.detail.components.CardField
import com.pablogv63.quicklock.ui.credentials.detail.components.DetailScreenTopAppBar
import com.pablogv63.quicklock.ui.destinations.EditScreenDestination
import com.pablogv63.quicklock.ui.navigation.QuickLockNavigationBar
import com.pablogv63.quicklock.ui.tools.AppPaddingValues
import com.pablogv63.quicklock.ui.tools.Tools.copyTextToClipboard
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.viewModel
import org.koin.core.parameter.parametersOf

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Destination
@Composable
fun DetailScreen(
    navigator: DestinationsNavigator,
    credentialId: Int
){
    val viewModel: DetailViewModel by viewModel {
        parametersOf(credentialId)
    }
    val detailState = viewModel.detailState

    Scaffold(
        topBar = { DetailScreenTopAppBar(
            title = detailState.name,
            onArrowBackClick = { navigator.navigateUp() },
            onEditClick = { navigator.navigate(EditScreenDestination(credentialId = credentialId)) }
        ) },
        bottomBar = {
            QuickLockNavigationBar(
                navigator = navigator
            )
        }
    ) { innerPadding ->
        DetailScreenContent(
            detailState = detailState,
            innerPadding = innerPadding,
            context = LocalContext.current
        )
    }
}

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun DetailScreenContent(
    detailState: DetailState,
    innerPadding: PaddingValues,
    context: Context
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = innerPadding.calculateTopPadding(),
                start = AppPaddingValues.Small,
                end = AppPaddingValues.Small
            ),
    ) {
        // Name
        CardField(
            label = stringResource(id = R.string.field_name_label),
            value = detailState.name,
            secondRowContent = {  }
        )
        // Username
        CardField(
            label = stringResource(id = R.string.field_username_label),
            value = detailState.username,
            secondRowContent = {
                IconButton(onClick = { context.copyTextToClipboard("Username",detailState.username) }) {
                    Icon(
                        imageVector = Icons.Outlined.ContentCopy,
                        contentDescription = stringResource(id = R.string.action_copy_username)
                    )
                }
            }
        )
        // Password
        var passwordVisible by remember { mutableStateOf(false) }
        CardField(
            label = stringResource(id = R.string.field_password_label),
            value = if (passwordVisible) detailState.password else "********",
            secondRowContent = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        contentDescription = null
                    )
                }
                IconButton(onClick = { context.copyTextToClipboard("Password",detailState.password) }) {
                    Icon(imageVector = Icons.Filled.ContentCopy, contentDescription = stringResource(id = R.string.action_copy_password))
                }
            }
        )
        // Categories
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.outlinedCardColors()
        ) {
            Row(
                modifier = Modifier
                    .padding(AppPaddingValues.Medium)
            ) {
                Column {
                    Text(
                        text = stringResource(id = R.string.field_categories_label),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(AppPaddingValues.Small))
                    Row {
                        if (detailState.categories.isEmpty()){
                            Text(
                                text = stringResource(id = R.string.field_categories_empty),
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                        detailState.categories.map { category ->
                            Text(
                                text = category.name,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color(category.colour)
                            )
                            Spacer(modifier = Modifier.width(AppPaddingValues.Small))
                        }
                    }
                }
            }
        }
        // Expiration date
        val warningColor = Color(184,155,51)
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.outlinedCardColors()
        ) {
            Row(
                modifier = Modifier
                    .padding(AppPaddingValues.Medium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = stringResource(id = R.string.field_expirationDate_label),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(AppPaddingValues.Small))
                    Row {
                        Text(
                            text = detailState.expirationDate.ifBlank { stringResource(id = R.string.field_expirationDate_empty) },
                            style = MaterialTheme.typography.bodyLarge
                        )
                        if (detailState.expirationDate.expiresSoon()){
                            Text(
                                text = " - ",
                                style = MaterialTheme.typography.bodyLarge,
                                color = warningColor
                            )
                            Text(
                                text = stringResource(id = R.string.field_expirationDate_expiresSoon),
                                style = MaterialTheme.typography.bodyLarge,
                                color = warningColor
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    if (detailState.expirationDate.expiresSoon()){
                        Icon(
                            imageVector = Icons.Outlined.WarningAmber,
                            contentDescription = stringResource(id = R.string.field_expirationDate_expiresSoon),
                            tint = warningColor,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
        }
        // Last modified
        CardField(
            label = stringResource(id = R.string.field_lastModified_label),
            value = detailState.lastModified,
            secondRowContent = {  }
        )
        // Last access
        CardField(
            label = stringResource(id = R.string.field_lastAccessed_label),
            value = detailState.lastAccess,
            secondRowContent = {  }
        )
    }
}

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Preview
@Composable
fun PreviewCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.outlinedCardColors()
    ) {
        Row(
            modifier = Modifier
                .padding(AppPaddingValues.Small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Categories",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(AppPaddingValues.Small))
                Row {
                    // Cat 1
                    Text(
                        text = "Studies",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(80,166,66)
                    )
                    Spacer(modifier = Modifier.width(AppPaddingValues.Small))
                    // Cat 2
                    Text(
                        text = "Uni",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(186,27,27)
                    )
                    Spacer(modifier = Modifier.width(AppPaddingValues.Small))
                    // Cat 3
                    Text(
                        text = "Work",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Cyan
                    )
                    Spacer(modifier = Modifier.width(AppPaddingValues.Small))
                }

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                    Icon(
                        imageVector = Icons.Outlined.WarningAmber,
                        contentDescription = "Expiration warning",
                        tint = Color(184,155,51),
                        modifier = Modifier.size(40.dp)
                    )
            }
        }
    }
}