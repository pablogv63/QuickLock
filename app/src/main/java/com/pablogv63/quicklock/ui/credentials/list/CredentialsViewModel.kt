package com.pablogv63.quicklock.ui.credentials.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablogv63.quicklock.domain.use_case.CredentialUseCases
import com.pablogv63.quicklock.domain.util.CredentialFilter
import com.pablogv63.quicklock.domain.util.CredentialOrder
import com.pablogv63.quicklock.domain.util.OrderType
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate

class CredentialsViewModel(
    private val credentialUseCases: CredentialUseCases
): ViewModel() {

    private val _state = mutableStateOf(CredentialsState())
    val state: State<CredentialsState> = _state

    private var getCredentialsJob: Job? = null

    init {
        getCredentials(state.value.credentialOrder)
    }

    private fun getCredentials(credentialOrder: CredentialOrder){
        getCredentialsJob?.cancel()
        getCredentialsJob = credentialUseCases.getCredentialsWithCategories(credentialOrder)
            .onEach { credentials ->
                _state.value = state.value.copy(
                    credentialsWithCategories = credentials,
                    credentialOrder = credentialOrder,
                    matchedCredentialsWithCategories = credentials
                )
            }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: CredentialsEvent) {
        when(event){
            is CredentialsEvent.Order -> {
                if (state.value.credentialOrder::class == event.credentialOrder::class &&
                        state.value.credentialOrder.orderType == event.credentialOrder.orderType
                ) {
                    return
                }
                getCredentials(event.credentialOrder)
            }
            is CredentialsEvent.ToggleOrderMenu -> {
                _state.value = state.value.copy(
                    isOrderMenuVisible = !_state.value.isOrderMenuVisible
                )
            }
            is CredentialsEvent.ToggleSearchBar -> {
                _state.value = state.value.copy(
                    isSearchBarVisible = !_state.value.isSearchBarVisible,
                    isOrderMenuVisible = false
                )
            }
            is CredentialsEvent.SearchTextChanged -> {
                _state.value = state.value.copy(
                    searchBarText = event.text
                )
                if (event.text.isEmpty()){
                    _state.value = state.value.copy(
                        matchedCredentialsWithCategories = state.value.credentialsWithCategories
                    )
                    return
                }
                filterList()
            }
            CredentialsEvent.ClearSearchText -> {
                _state.value = state.value.copy(
                    searchBarText = "",
                    matchedCredentialsWithCategories = listOf()
                )
            }
            is CredentialsEvent.FilterChanged -> {
                when(event.credentialFilter){
                    is CredentialFilter.Name -> {
                        _state.value = state.value.copy(filterByName = !_state.value.filterByName)
                    }
                    CredentialFilter.Username -> {
                        _state.value = state.value.copy(filterByUsername = !_state.value.filterByUsername)
                    }
                    CredentialFilter.Category -> {
                        _state.value = state.value.copy(filterByCategory = !_state.value.filterByCategory)
                    }
                }
                filterList()
            }
            is CredentialsEvent.CredentialAccessed -> {
                viewModelScope.launch {
                    val credential = event.credential.copy(
                        lastAccess = LocalDate.now()
                    )
                    credentialUseCases.addCredential(credential = credential)
                }
            }
        }
    }

    private fun filterList(){
        val text = _state.value.searchBarText
        val byName = _state.value.filterByName
        val byUsername = _state.value.filterByUsername
        val byCategory = _state.value.filterByCategory

        // Don't filter if search text empty
        if (_state.value.searchBarText.isEmpty()) return

        val filteredList = _state.value.credentialsWithCategories.filter { x ->
            var match = false
            if (byName){
                match = match || x.credential.name.contains(text, true)
            }
            if (byUsername){
                match = match || x.credential.username.contains(text, true)
            }
            if (byCategory){
                match = match || x.categories.any { it.name.contains(text, true) }
            }
            match
        }

        _state.value = state.value.copy(
            matchedCredentialsWithCategories = filteredList
        )
    }
}