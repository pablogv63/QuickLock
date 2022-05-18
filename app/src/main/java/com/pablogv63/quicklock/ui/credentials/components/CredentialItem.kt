package com.pablogv63.quicklock.ui.credentials.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.CopyAll
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FileCopy
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pablogv63.quicklock.data.data_source.credential_category_pair.CredentialWithCategoryList

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
    else Color(80,166,66)

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CredentialItemContent(credentialItemValues: CredentialItemValues){
    // Card with stuff
    OutlinedCard (
        modifier = Modifier
            .fillMaxWidth(),
        onClick = credentialItemValues.onCardClick
    ){
        Column (
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 16.dp)
        ){
            // First row
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Rounded icon
                RoundedIconWithText(text = "C", backgroundColor = credentialItemValues.roundedIcon.color)
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
                horizontalArrangement = Arrangement.Center
            ) {
                CopyButton(text = "Copy username", credentialItemValues.onCopyUsernameClick)
                Spacer(modifier = Modifier.width(8.dp))
                CopyButton(text = "Copy password", credentialItemValues.onCopyPasswordClick)
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
            Icon(imageVector = Icons.Outlined.Edit, contentDescription = "Edit")
        }
    }
}

@Composable
fun CopyButton(
    text: String,
    onItemClick: () -> Unit
){
    OutlinedButton(onClick = onItemClick) {
        Icon(imageVector = Icons.Outlined.ContentCopy, contentDescription = "Copy")
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text)
    }
}

@Preview
@Composable
fun PreviewCredentialItem() {
    val credentialItemValues = CredentialItemValues(
        roundedIcon = CredentialItemValues.RoundedIcon("C", Color(80,166,66)),
        name = "Credential with a large name",
        username = "cred1@mail.com",
        onCardClick = {},
        onCopyUsernameClick = {},
        onCopyPasswordClick = {},
        onEditButtonClick = {}
    )
    CredentialItemContent(credentialItemValues = credentialItemValues)
}

@Preview
@Composable
fun PreviewRoundedIconWithText(){
    RoundedIconWithText(text = "C", backgroundColor = Color(80,166,66))
}