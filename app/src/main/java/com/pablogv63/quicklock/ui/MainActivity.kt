package com.pablogv63.quicklock.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.pablogv63.quicklock.ui.navigation.QuickLockNavHost
import com.pablogv63.quicklock.ui.theme.QuickLockTheme
import org.koin.androidx.compose.getViewModel

@ExperimentalMaterial3Api
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuickLockTheme {
                // Change status bar color
                this.window.statusBarColor = MaterialTheme.colorScheme.inverseSurface.toArgb()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    mainViewModel = getViewModel()
                    QuickLockNavHost()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {

    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Home", "Settings", "Favorites")
    val icons = listOf(
        Icons.Filled.Home, Icons.Filled.Settings,
        Icons.Filled.Favorite)

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SmallTopAppBar(title = { Text("ThemeDemo") }, scrollBehavior = null)

        Button(onClick = { }) {
            Text("MD3 Button")
        }

        Text("A Theme Demo")

        FloatingActionButton(onClick = { }) {
            Text("FAB")
        }

        NavigationBar {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = { Icon(icons[index], contentDescription = null) },
                    label = { Text(item) },
                    selected = selectedItem == index,
                    onClick = { selectedItem = index }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    QuickLockTheme(true) {
        Surface {
            MainScreen()
        }
    }
}