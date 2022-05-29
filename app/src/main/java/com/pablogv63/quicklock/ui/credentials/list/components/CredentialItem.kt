package com.pablogv63.quicklock.ui.credentials.list.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pablogv63.quicklock.R
import com.pablogv63.quicklock.domain.model.CredentialWithCategoryList

data class CredentialItemValues(
    val roundedIcon: RoundedIcon,
    val name: String,
    val username: String,
    val onCardClick: () -> Unit,
    val onEditButtonClick: () -> Unit,
    val onCopyUsernameClick: () -> Unit,
    val onCopyPasswordClick: () -> Unit
) {
    data class RoundedIcon(
        val text: String,
        val color: Color
    )
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Composable
fun CredentialItem(
    credentialWithCategoryList: CredentialWithCategoryList,
    onItemClick: () -> Unit,
    onCopyUsernameClick: () -> Unit,
    onCopyPasswordClick: () -> Unit,
    onEditButtonClick: () -> Unit
){
    val credential = credentialWithCategoryList.credential
    val categoryList = credentialWithCategoryList.categories
    val firstCategory = categoryList.firstOrNull()
    val roundedIconColor = if (firstCategory != null)
        Color(firstCategory.colour)
    else MaterialTheme.colorScheme.primary

    val credentialItemValues = CredentialItemValues(
        roundedIcon = CredentialItemValues.RoundedIcon("C", roundedIconColor),
        name = credential.name,
        username = credential.username,
        onCardClick = onItemClick,
        onEditButtonClick = onEditButtonClick,
        onCopyUsernameClick = onCopyUsernameClick,
        onCopyPasswordClick = onCopyPasswordClick
    )

    CredentialItemContent(credentialItemValues = credentialItemValues)
}

@ExperimentalMaterial3Api
@Composable
fun CredentialItemContent(credentialItemValues: CredentialItemValues){
    OutlinedCard (
        modifier = Modifier
            .fillMaxWidth(),
        onClick = credentialItemValues.onCardClick
    ){
        Column (
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 14.dp)
        ){
            // First row
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Rounded icon
                RoundedIconWithText(
                    text = credentialItemValues.name.first().toString(),
                    backgroundColor = credentialItemValues.roundedIcon.color
                )
                Spacer(modifier = Modifier.width(16.dp))
                // Cred name and main field
                Column {
                    Text(
                        text = credentialItemValues.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = credentialItemValues.username,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                // Edit button
                EditButton(credentialItemValues.onEditButtonClick)
            }
            // Divider
            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            // Second row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CopyButton(
                    text = stringResource(id = R.string.action_copy_username),
                    credentialItemValues.onCopyUsernameClick
                )
                CopyButton(
                    text = stringResource(id = R.string.action_copy_password),
                    credentialItemValues.onCopyPasswordClick
                )
            }
        }
    }
}

@Composable
fun RoundedIconWithText(text: String, backgroundColor: Color) {
    Box(contentAlignment= Alignment.Center,
        modifier = Modifier
            .background(backgroundColor, shape = CircleShape)
            .layout() { measurable, constraints ->
                // Measure the composable
                val placeable = measurable.measure(constraints)

                //get the current max dimension to assign width=height
                val currentHeight = placeable.height
                var heightCircle = currentHeight
                if (placeable.width > heightCircle)
                    heightCircle = placeable.width

                //assign the dimension and the center position
                layout(heightCircle, heightCircle) {
                    // Where the composable gets placed
                    placeable.placeRelative(0, (heightCircle - currentHeight) / 2)
                }
            }) {

        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier
                .padding(4.dp)
                .defaultMinSize(30.dp) //Use a min size for short text.
        )
    }
}

@Composable
fun EditButton(
    onItemClick: () -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomEnd
    ) {
        IconButton(onClick = onItemClick) {
            Icon(imageVector = Icons.Outlined.Edit, contentDescription = stringResource(id = R.string.editScreen_topAppBar_title))
        }
    }
}

@Composable
fun CopyButton(
    text: String,
    onItemClick: () -> Unit
){
    OutlinedButton(onClick = onItemClick) {
        Icon(
            imageVector = Icons.Outlined.ContentCopy,
            contentDescription = text,
            modifier = Modifier
                .width(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text)
    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewCredentialItem() {
    val credentialItemValues = CredentialItemValues(
        roundedIcon = CredentialItemValues.RoundedIcon(
            text = "C",
            color = Color(80,166,66)),
        name = "Credential with a large name",
        username = "cred1@mail.com",
        onCardClick = {},
        onCopyUsernameClick = {},
        onCopyPasswordClick = {},
        onEditButtonClick = {}
    )
    CredentialItemContent(credentialItemValues)
}

@Preview
@Composable
fun PreviewRoundedIconWithText(){
    RoundedIconWithText(text = "C", backgroundColor = Color(80,166,66))
}