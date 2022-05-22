package com.pablogv63.quicklock.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablogv63.quicklock.data.data_source.AppDatabase
import com.pablogv63.quicklock.domain.model.Category
import com.pablogv63.quicklock.domain.model.Credential
import com.pablogv63.quicklock.domain.model.CredentialWithCategoryList
import com.pablogv63.quicklock.domain.use_case.CredentialUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate

class MainViewModel(
    private val credentialUseCases: CredentialUseCases,
    private val db: AppDatabase
): ViewModel() {

    private val _state = mutableStateOf(MainState())
    val state: State<MainState> = _state

    private var getExamplePeopleJob: Job? = null

    init {
        //TODO: Debug purposes only
        viewModelScope.launch(Dispatchers.IO){
            db.clearAllTables()
            val creds = sampleCreds()
            val cats = sampleCategories()
            val credentialsWithCategories: MutableList<CredentialWithCategoryList> = mutableListOf()
            credentialsWithCategories.add(
                CredentialWithCategoryList(
                credential = creds[0],
                categories = listOf()
                )
            )
            credentialsWithCategories.add(
                CredentialWithCategoryList(
                    credential = creds[1],
                    categories = listOf(cats[0])
                )
            )
            credentialsWithCategories.add(
                CredentialWithCategoryList(
                    credential = creds[2],
                    categories = listOf(cats[1])
                )
            )
            credentialsWithCategories.map {
                credentialUseCases.addCredentialWithCategories(it)
            }
        }
    }

    private fun sampleCreds(): List<Credential> {
        val cred1 = Credential(
            credentialId = 1,
            name = "Credential with no category",
            username = "cred1@mail.com",
            password = "********",
            expirationDate = null,
            lastAccess = LocalDate.of(2022,5,15),
            lastModified = LocalDate.of(2022,5,10)
        )
        val cred2 = Credential(
            credentialId = 2,
            name = "Credential with 'Studies' category",
            username = "cred2@mail.com",
            password = "********",
            expirationDate = null,
            lastAccess = LocalDate.of(2022,5,15),
            lastModified = LocalDate.of(2022,5,10)
        )
        val cred3 = Credential(
            credentialId = 3,
            name = "Credential with 'Uni' category",
            username = "cred3@mail.com",
            password = "********",
            expirationDate = null,
            lastAccess = LocalDate.of(2022,5,15),
            lastModified = LocalDate.of(2022,5,10)
        )
        return listOf(cred1,cred2,cred3)
    }

    private fun sampleCategories(): List<Category> {
        val cat1 = Category(
            categoryId = 1,
            name = "Studies",
            colour = Color(80,166,66).toArgb()
        )
        val cat2 = Category(
            categoryId = 2,
            name = "Uni",
            colour = Color(186,27,27).toArgb()
        )
        return listOf(cat1, cat2)
    }

    private fun getExamplePeople(){
        getExamplePeopleJob?.cancel()
        getExamplePeopleJob = credentialUseCases.getCredentialsWithCategories()
            .onEach { _ ->
                _state.value = state.value.copy(
                    examplePeople = emptyList()
                )
            }
            .launchIn(viewModelScope)
    }

}