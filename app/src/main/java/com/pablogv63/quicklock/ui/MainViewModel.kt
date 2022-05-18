package com.pablogv63.quicklock.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablogv63.quicklock.domain.model.Credential
import com.pablogv63.quicklock.domain.use_case.CredentialUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate

class MainViewModel(
    private val credentialUseCases: CredentialUseCases
): ViewModel() {

    private val _state = mutableStateOf(MainState())
    val state: State<MainState> = _state

    private var getExamplePeopleJob: Job? = null

    init {
        addCreds() //TODO: Remove this line and assoc function
    }

    private fun addCreds(){
        val cred1 = Credential(
            credentialId = 1,
            name = "Cred1 and some other stuff",
            username = "cred1@mail.com",
            password = "********",
            lastAccess = LocalDate.of(2022,5,15),
            lastModified = LocalDate.of(2022,5,10)
        )
        val cred2 = Credential(
            credentialId = 2,
            name = "Cred2",
            username = "cred2@mail.com",
            password = "********",
            lastAccess = LocalDate.of(2022,5,15),
            lastModified = LocalDate.of(2022,5,10)
        )
        val cred3 = Credential(
            credentialId = 3,
            name = "Cred3",
            username = "cred3@mail.com",
            password = "********",
            lastAccess = LocalDate.of(2022,5,15),
            lastModified = LocalDate.of(2022,5,10)
        )
        viewModelScope.launch(Dispatchers.IO) {
            credentialUseCases.addCredential(cred1)
            credentialUseCases.addCredential(cred2)
            credentialUseCases.addCredential(cred3)
        }
    }

    private fun getExamplePeople(){
        getExamplePeopleJob?.cancel()
        getExamplePeopleJob = credentialUseCases.getCredentialsWithCategories()
            .onEach { examplePeople ->
                _state.value = state.value.copy(
                    examplePeople = emptyList()
                )
            }
            .launchIn(viewModelScope)
    }

}