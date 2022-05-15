package com.pablogv63.composeroomtemplate.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pablogv63.composeroomtemplate.data.repository.ExamplePersonRepositoryImpl
import com.pablogv63.composeroomtemplate.domain.model.ExamplePerson
import com.pablogv63.composeroomtemplate.domain.repository.ExamplePersonRepository
import com.pablogv63.composeroomtemplate.domain.use_case.ExamplePersonUseCases
import com.pablogv63.composeroomtemplate.domain.use_case.GetExamplePeople
import com.pablogv63.composeroomtemplate.ui.theme.ComposeRoomTemplateTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeRoomTemplateTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = getViewModel<MainViewModel>()
                    BDList(viewModel = viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BDList(
    viewModel: MainViewModel
){
    // Obtain the data
    val state = viewModel.state.value

    Scaffold { innerPadding ->
        LazyColumn(contentPadding = innerPadding){
            items(state.examplePeople) { examplePerson ->
                val birthDate = examplePerson.birthdate
                Card {
                    Row {
                        Text(text = examplePerson.name)
                        Text(text = " ")
                        Text(text = "${birthDate.dayOfMonth} ${birthDate.month}")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeRoomTemplateTheme {
        Greeting("Android")
    }
}