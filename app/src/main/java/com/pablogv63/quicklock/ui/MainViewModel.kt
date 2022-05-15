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
        getExamplePeople()
    }

    private fun getExamplePeople(){
        getExamplePeopleJob?.cancel()
        getExamplePeopleJob = credentialUseCases.getCredentials()
            .onEach { examplePeople ->
                _state.value = state.value.copy(
                    examplePeople = examplePeople
                )
            }
            .launchIn(viewModelScope)
    }

}