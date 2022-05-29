package com.pablogv63.quicklock.ui.credentials.list

import com.pablogv63.quicklock.domain.model.Credential
import com.pablogv63.quicklock.domain.util.CredentialFilter
import com.pablogv63.quicklock.domain.util.CredentialOrder

sealed class CredentialsEvent {
    data class Order(val credentialOrder: CredentialOrder): CredentialsEvent()
    object ToggleOrderMenu: CredentialsEvent()
    object ToggleSearchBar: CredentialsEvent()
    data class SearchTextChanged(val text: String): CredentialsEvent()
    object ClearSearchText: CredentialsEvent()
    data class FilterChanged(val credentialFilter: CredentialFilter): CredentialsEvent()
    data class CredentialAccessed(val credential: Credential): CredentialsEvent()
}