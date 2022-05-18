package com.pablogv63.quicklock.ui.credentials

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablogv63.quicklock.domain.use_case.CredentialUseCases
import com.pablogv63.quicklock.domain.util.CredentialOrder
import com.pablogv63.quicklock.domain.util.OrderType
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CredentialsViewModel(
    private val credentialUseCases: CredentialUseCases
): ViewModel() {

    private val _state = mutableStateOf(CredentialsState())
    val state: State<CredentialsState> = _state

    private var getCredentialsJob: Job? = null

    init {
        getCredentials(CredentialOrder.Name(OrderType.Ascending))
    }

    private fun getCredentials(credentialOrder: CredentialOrder){
        getCredentialsJob?.cancel()
        getCredentialsJob = credentialUseCases.getCredentialsWithCategories(credentialOrder)
            .onEach { credentials ->
                _state.value = state.value.copy(
                    credentialsWithCategories = credentials,
                    credentialOrder = credentialOrder
                )
            }
            .launchIn(viewModelScope)
    }
}